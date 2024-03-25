package com.suchtool.nicetool.util.spring;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Map;

public class SpringBootUtil {
    public static String parseMainClassPackage() {
        Map<String, Object> runClassBeanMap = ApplicationContextHolder.getContext()
                .getBeansWithAnnotation(SpringBootApplication.class);
        Object runObject = runClassBeanMap.entrySet().iterator().next().getValue();
        return AopUtil.getTargetClass(runObject).getPackage().getName();
    }
}
