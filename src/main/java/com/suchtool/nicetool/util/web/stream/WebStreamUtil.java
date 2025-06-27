package com.suchtool.nicetool.util.web.stream;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * 网络流工具
 */
@Slf4j
public class WebStreamUtil {
    /**
     * 使用流所为响应。指定文件名
     *
     * @param inputStream    输入流
     * @param outputFileName 输出的文件名
     */
    public static void responseAsStream(InputStream inputStream,
                                        String outputFileName) {
        responseAsStream(inputStream, outputFileName, null);
    }


    /**
     * 使用流所为响应。指定文件名和响应头
     *
     * @param inputStream    输入流
     * @param outputFileName 输出的文件名
     * @param responseHeader 响应头（追加响应头）
     */
    public static void responseAsStream(InputStream inputStream,
                                        String outputFileName,
                                        MultiValueMap<String, String> responseHeader) {
        responseAsStream(inputStream, outputFileName, responseHeader, false);
    }

    /**
     * 使用流所为响应。指定文件名和响应头
     *
     * @param inputStream           输入流
     * @param outputFileName        输出的文件名
     * @param responseHeader        响应头
     * @param replaceResponseHeader 是否完全替换响应头。true：完全替换。false：添加
     */
    public static void responseAsStream(InputStream inputStream,
                                        String outputFileName,
                                        MultiValueMap<String, String> responseHeader,
                                        boolean replaceResponseHeader) {
        ServletRequestAttributes servletRequestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Assert.notNull(servletRequestAttributes, "RequestAttributes不能为null");
        HttpServletResponse response = servletRequestAttributes.getResponse();
        Assert.notNull(response, "Response不能为null");

        if (StringUtils.hasText(outputFileName)) {
            String fileName = null;
            try {
                fileName = URLEncoder.encode(outputFileName, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }

            // 通知浏览器以附件的形式下载处理，设置返回头要注意文件名有中文
            response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        }

        if (replaceResponseHeader) {
            response.reset();
        }
        if (!CollectionUtils.isEmpty(responseHeader)) {
            appendResponseHeader(response, responseHeader);
        }

        doResponseAsStream(inputStream, response);
    }

    private static void doResponseAsStream(InputStream inputStream,
                                           HttpServletResponse response) {
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            StreamUtils.copy(inputStream, outputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            // 确保流在使用完毕后被关闭
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                log.error("输入流关闭失败", e);
            }

            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                log.error("输出流关闭失败", e);
            }
        }
    }

    private static void appendResponseHeader(HttpServletResponse response,
                                             MultiValueMap<String, String> responseHeader) {
        for (Map.Entry<String, List<String>> entry : responseHeader.entrySet()) {
            String key = entry.getKey();
            List<String> value = entry.getValue();

            for (String headerValue : value) {
                response.addHeader(key, headerValue);
            }
        }
    }
}
