package com.suchtool.betterutil.util;

import org.springframework.aop.framework.Advised;

public class AopUtil {
    public static Class<?> getTargetClass(Object bean) {
        Class<?> targetCls = bean.getClass();

        // 如果类名包含此字符串则说明是代理类，记录其父类（真实目标类）
        if (targetCls.getName().contains("$$EnhancerBySpringCGLIB$$")) {
            targetCls = targetCls.getSuperclass();
        }

        return targetCls;
    }

    public static Object getTargetBean(Object bean) {
        Object targetBean = bean;
        if (targetBean instanceof Advised) {
            try {
                targetBean = ((Advised) targetBean).getTargetSource().getTarget();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return targetBean;
    }
}
