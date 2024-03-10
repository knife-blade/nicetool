package com.suchtool.nicetool.util.web.ip;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

@Slf4j
public class ClientIpUtil {
    /**
     * 是否是本机IP
     * @param ip IP地址
     * @return 是否是本机IP
     */
    public static boolean isLocalIP(String ip) {
        try {
            InetAddress addr = InetAddress.getByName(ip);
            return addr.isLoopbackAddress();
        } catch (UnknownHostException e) {
            return false;
        }
    }

    /**
     * 获取IP
     * @return 调用方的IP
     */
    public static String parseRemoteIP() {
        boolean isHttpServletRequest = ClassUtils.isPresent(
                "javax.servlet.http.HttpServletRequest", null);
        if (isHttpServletRequest) {
            HttpServletRequest request = getRequest();
            return parseRemoteIp(request::getRemoteAddr);
        }

        throw new RuntimeException("HttpServletRequest doesn't exist, only support HttpServletRequest so far.");
    }

    private static String parseRemoteIp(Supplier<String> remoteIpSupplier) {
        String ip = remoteIpSupplier.get();

        // 如果是本机请求（前者是IPV4的本机，后者是IPV6的本机）
        // 则获取本机的IP
        if (isLocalIP(ip)) {
            // 根据网卡取本机配置的IP
            InetAddress inet = null;
            try {
                inet = InetAddress.getLocalHost();
            } catch (UnknownHostException e) {
                log.error("无法获得localHost", e);
            }

            if (inet != null) {
                ip = inet.getHostAddress();
            }
        }

        return parseMultistageReverseProxyIp(ip);
    }

    /**
     * 获取客户端IP.
     *
     * <p>
     * 默认检测的Header:
     * <pre>
     * 1、X-Forwarded-For
     * 2、X-Real-IP
     * 3、Proxy-Client-IP
     * 4、WL-Proxy-Client-IP
     * 5、HTTP_CLIENT_IP
     * 6、HTTP_X_FORWARDED_FOR
     * </pre>
     * </p>
     * @return IP地址
     */
    public static String parseClientIP() {
        boolean isHttpServletRequest = ClassUtils.isPresent(
                "javax.servlet.http.HttpServletRequest", null);
        if (isHttpServletRequest) {
            HttpServletRequest request = getRequest();
            return parseClientIPByHeader(null, request::getHeader);
        }

        throw new RuntimeException("HttpServletRequest not exist");
    }

    public static String parseClientIP(Function<String, String> headValueSupplier) {
        return parseClientIPByHeader(null, headValueSupplier);
    }

    /**
     * 获取客户端IP
     *
     * @param otherHeaderNameList 其他自定义头文件，通常在Http服务器（例如Nginx）中配置
     * @return IP地址
     */
    public static String parseClientIP(List<String> otherHeaderNameList) {
        return parseClientIP(otherHeaderNameList, null);
    }

    /**
     * 获取客户端IP
     *
     * @param otherHeaderNameList 其他自定义头文件，通常在Http服务器（例如Nginx）中配置
     * @return IP地址
     */
    public static String parseClientIP(List<String> otherHeaderNameList,
                                       Function<String, String> headValueSupplier) {
        List<String> headerList = Arrays.asList(
                "X-Forwarded-For",
                "X-Real-IP",
                "Proxy-Client-IP",
                "WL-Proxy-Client-IP",
                "HTTP_CLIENT_IP",
                "HTTP_X_FORWARDED_FOR"
        );

        if (!CollectionUtils.isEmpty(otherHeaderNameList)) {
            headerList.addAll(otherHeaderNameList);
        }

        return parseClientIPByHeader(headerList, headValueSupplier);
    }

    /**
     * 获取客户端IP
     * @param headerNameList 其他自定义头，通常在Http服务器（例如Nginx）中配置
     * @param headValueFunction 请求头值
     * @return IP地址
     */
    private static String parseClientIPByHeader(List<String> headerNameList,
                                                Function<String, String> headValueFunction) {
        String ip;
        if (!CollectionUtils.isEmpty(headerNameList)) {
            for (String header : headerNameList) {
                ip = headValueFunction.apply(header);
                if (!isUnknown(ip)) {
                    return parseMultistageReverseProxyIp(ip);
                }
            }
        }

        ip = parseRemoteIP();
        return parseMultistageReverseProxyIp(ip);
    }

    /**
     * 从多级反向代理中获得第一个非unknown IP地址
     *
     * @param ip 获得的IP地址
     * @return 第一个非unknown IP地址
     */
    public static String parseMultistageReverseProxyIp(String ip) {
        // 多级反向代理检测
        if (ip != null && ip.indexOf(",") > 0) {
            final String[] ips = ip.trim().split(",");
            for (String subIp : ips) {
                if (!isUnknown(subIp)) {
                    ip = subIp;
                    break;
                }
            }
        }
        return ip;
    }

    private static HttpServletRequest getRequest() {
        ServletRequestAttributes servletRequestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (servletRequestAttributes == null) {
            throw new RuntimeException("ServletRequestAttributes is null, only support ServletRequest so far.");
        }
        // 返回值不可能为null，无需判断
        return servletRequestAttributes.getRequest();
    }

    /**
     * 检测给定字符串是否为未知，多用于检测HTTP请求相关.
     *
     * @param checkString 被检测的字符串
     * @return 是否未知
     */
    public static boolean isUnknown(String checkString) {
        return StringUtils.hasText(checkString) || "unknown".equalsIgnoreCase(checkString);
    }
}
