package com.suchtool.nicetool.util.reflect;

import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MethodUtil {
    private static final ParameterNameDiscoverer NAME_DISCOVERER =
            new DefaultParameterNameDiscoverer();

    /**
     * 解析方法详情
     *
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
     *
     * @param method 方法
     * @param args   参数
     * @return Map（key为参数名，value为参数值）
     */
    public static Map<String, Object> parseParam(Method method, Object[] args) {
        return parseParam(method, args, null);
    }

    /**
     * 解析参数
     *
     * @param method          方法
     * @param args            参数
     * @param ignoreClassList 忽略解析的类
     * @return Map（key为参数名，value为参数值）
     */
    public static Map<String, Object> parseParam(Method method,
                                                 Object[] args,
                                                 Collection<Class<?>> ignoreClassList) {
        Map<String, Object> map = new HashMap<>();

        String[] parameterNames = NAME_DISCOVERER.getParameterNames(method);
        if (parameterNames != null
                && parameterNames.length > 0) {
            for (int i = 0; i < parameterNames.length; i++) {
                if (requireParse(ignoreClassList, args[i])) {
                    map.put(parameterNames[i], args[i]);
                }
            }
        }

        return map;
    }

    private static boolean requireParse(Collection<Class<?>> ignoreClassList,
                                        Object arg) {
        boolean requireParse = true;
        if (!CollectionUtils.isEmpty(ignoreClassList)) {
            for (Class<?> aClass : ignoreClassList) {
                if (aClass.isInstance(arg)) {
                    requireParse = false;
                    break;
                }
            }
        }
        return requireParse;
    }
}
