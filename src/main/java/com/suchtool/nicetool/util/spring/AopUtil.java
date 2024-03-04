package com.suchtool.nicetool.util.spring;

import org.springframework.aop.framework.Advised;

public class AopUtil {
    /**
     * 获得目标类
     * @param bean bean类
     * @return 目标类
     */
    public static Class<?> getTargetClass(Object bean) {
        Class<?> targetCls = bean.getClass();

        // 如果类名包含此字符串则说明是代理类，记录其父类（真实目标类）
        if (targetCls.getName().contains("$$EnhancerBySpringCGLIB$$")) {
            targetCls = targetCls.getSuperclass();
        }

        return targetCls;
    }

    /**
     * 获得代理bean
     * @param bean 目标bean
     * @return 代理bean
     */
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
