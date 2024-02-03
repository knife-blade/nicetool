package com.suchtool.niceutil.util.web.http;

import com.suchtool.niceutil.util.web.http.annotation.UrlParamProperty;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.reflect.Field;
import java.util.List;

public class HttpUtil {
    /**
     * 拼接URL（自动添加或者去除首尾的/）
     * @param fragments URL片段
     * @return 拼接好的URL
     */
    public static String joinUrl(List<String> fragments) {
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
        if (!url.startsWith("http")
                && !url.startsWith("https")) {
            url = "http://" + url;
        }

        return url;
    }

    /**
     * 通过对象构造带参数的URL
     * @param baseUrl url前缀
     * @param objectList 对象列表（用此注解指定字段名：{@link UrlParamProperty}）
     * @return 带参数的url
     */
    public static String buildUrlWithParams(String baseUrl, List<Object> objectList) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(baseUrl);

        for (Object obj : objectList) {
            Class<?> cls = obj.getClass();

            fillParam(cls, obj, builder);
        }

        return builder.toUriString();
    }

    private static void fillParam(Class<?> cls,
                           Object obj,
                           UriComponentsBuilder builder) {
        for (Field field : cls.getDeclaredFields()) {
            field.setAccessible(true);
            try {
                String key = null;
                if (field.isAnnotationPresent(UrlParamProperty.class)) {
                    key = field.getAnnotation(UrlParamProperty.class).value();
                } else {
                    key = field.getName();
                }
                Object value = field.get(obj);
                if (value != null) {
                    builder.queryParam(key, value);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            field.setAccessible(false);
        }

        Class<?> superclass = cls.getSuperclass();
        if (!superclass.equals(Object.class)) {
            fillParam(superclass, obj, builder);
        }
    }
}
