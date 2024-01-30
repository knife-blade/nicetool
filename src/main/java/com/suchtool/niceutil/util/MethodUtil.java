package com.suchtool.niceutil.util;

import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class MethodUtil {
    private static final ParameterNameDiscoverer NAME_DISCOVERER =
            new DefaultParameterNameDiscoverer();

    /**
     * 解析方法详情
     * @param method 方法
     * @return 方法信息：类、方法名、参数类型
     */
    public static String parseMethodDetail(Method method) {
        // 方法全名（包含返回值、类、方法名、参数类型等）
        String methodGenericString = method.toGenericString();
        // 去掉返回值信息，只取类、方法名、参数类型等
        String[] strings = methodGenericString.split(" ");
        return strings[strings.length - 1];
    }

    /**
     * 解析参数
     * @param method 方法
     * @param args  参数
     * @return Map（key为参数名，value为参数值）
     */
    public static Map<String, Object> parseParam(Method method, Object[] args) {
        Map<String, Object> map = new HashMap<>();

        String[] parameterNames = NAME_DISCOVERER.getParameterNames(method);
        if (parameterNames != null
                && parameterNames.length > 0) {
            for (int i = 0; i < parameterNames.length; i++) {
                map.put(parameterNames[i], args[i]);
            }
        }

        return map;
    }
}
