package com.suchtool.niceutil.util.web.http.url;

import com.suchtool.niceutil.util.web.http.url.annotation.UrlParamProperty;
import org.springframework.util.CollectionUtils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

public class HttpUrlUtil {
    /**
     * 拼接URL（自动添加或者去除首尾的/）
     *
     * @param fragments URL片段
     * @param addScheme 是否自动添加协议头
     * @return 拼接好的URL
     */
    public static String joinUrl(List<String> fragments, boolean addScheme) {
        StringBuilder result = new StringBuilder();

        for (String fragment : fragments) {
            if (fragment == null || fragment.isEmpty()) {
                continue;
            }

            if (result.length() > 0 && result.charAt(result.length() - 1) != '/') {
                result.append('/');
            }

            if (fragment.charAt(0) == '/') {
                fragment = fragment.substring(1);
            }

            result.append(fragment);
        }

        String url = result.toString();
        if (addScheme) {
            if (!url.startsWith("http")
                    && !url.startsWith("https")) {
                url = "http://" + url;
            }
        }

        return url;
    }

    /**
     * 拼接URL（自动添加或者去除首尾的/。如果没有协议头，会自动添加）
     *
     * @param fragments URL片段
     * @return 拼接好的URL
     */
    public static String joinUrl(List<String> fragments) {
        return joinUrl(fragments, true);
    }

    /**
     * 转为参数字符串
     * @param paramMap 参数Map
     * @return 参数字符串
     */
    public static String toParamString(Map<Object, Object> paramMap) {
        StringJoiner stringJoiner = new StringJoiner("&");

        for (Map.Entry<?, ?> entry : paramMap.entrySet()) {
            String key = entry.getKey().toString();
            String value = entry.getValue() == null
                    ? ""
                    : entry.getValue().toString();

            String valueString = null;

            try {
                key = URLEncoder.encode(key, StandardCharsets.UTF_8.toString());

                valueString = value == null
                        ? ""
                        : URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
            stringJoiner.add(key + "=" + valueString);
        }

        return stringJoiner.toString();
    }

    /**
     * 转为参数字符串
     * @param paramList 参数列表
     * @return 参数字符串
     */
    public static String toParamString(List<Object> paramList) {
        Map<Object, Object> map = new HashMap<>();
        for (Object obj : paramList) {
            Class<?> cls = obj.getClass();
            fillParamMap(map, cls, obj);
        }
        return toParamString(map);
    }

    /**
     * 通过对象构造带参数的URL
     *
     * @param baseUrl  url前缀
     * @param paramMap 参数Map
     * @return 带参数的url
     */
    public static String buildUrl(String baseUrl, Map<Object, Object> paramMap) {
        StringBuilder stringBuilder = new StringBuilder(baseUrl);

        if (CollectionUtils.isEmpty(paramMap)) {
            return stringBuilder.toString();
        }

        stringBuilder.append("?");
        stringBuilder.append(toParamString(paramMap));

        return stringBuilder.toString();
    }

    /**
     * 通过对象构造带参数的URL
     *
     * @param baseUrl   url前缀
     * @param paramList 对象列表（用此注解指定字段名：{@link UrlParamProperty}）
     * @return 带参数的url
     */
    public static String buildUrl(String baseUrl, List<Object> paramList) {
        StringBuilder stringBuilder = new StringBuilder(baseUrl);

        if (CollectionUtils.isEmpty(paramList)) {
            return stringBuilder.toString();
        }

        stringBuilder.append("?");
        String paramString = toParamString(paramList);

        stringBuilder.append(paramString);

        return stringBuilder.toString();
    }

    private static void fillParamMap(Map<Object, Object> paramMap,
                                     Class<?> cls,
                                     Object obj) {
        for (Field field : cls.getDeclaredFields()) {
            try {
                field.setAccessible(true);
                String key = null;
                if (field.isAnnotationPresent(UrlParamProperty.class)) {
                    key = field.getAnnotation(UrlParamProperty.class).value();
                } else {
                    key = field.getName();
                }
                Object value = field.get(obj);
                paramMap.put(key, value);
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                field.setAccessible(false);
            }
        }

        Class<?> superclass = cls.getSuperclass();
        if (!superclass.equals(Object.class)) {
            fillParamMap(paramMap, superclass, obj);
        }
    }
}
