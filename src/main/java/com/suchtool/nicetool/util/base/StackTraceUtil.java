package com.suchtool.nicetool.util.base;

import org.springframework.util.StringUtils;

/**
 * 栈追踪工具类
 */
public class StackTraceUtil {
    private static final String TRACE_PREFIX = "    at ";

    /**
     * 获取以指定包名为前缀的栈追踪
     *
     * @param stackTraceElements 栈追踪元素
     * @return 栈追踪
     */
    public static String stackTraceToString(StackTraceElement[] stackTraceElements) {
        return stackTraceToString(stackTraceElements, null, null);
    }

    /**
     * 获取最后n条栈追踪
     *
     * @param stackTraceElements 栈追踪元素
     * @param n                  最后n行
     * @return 栈追踪
     */
    public static String stackTraceToString(StackTraceElement[] stackTraceElements,
                                            Integer n) {
        return stackTraceToString(stackTraceElements, null, n);
    }

    /**
     * 获取以指定包名为前缀的所有栈追踪
     *
     * @param stackTraceElements 栈追踪元素
     * @param packagePrefix      包前缀
     * @return 栈追踪
     */
    public static String stackTraceToString(StackTraceElement[] stackTraceElements,
                                            String packagePrefix) {
        return stackTraceToString(stackTraceElements, packagePrefix, null);
    }

    /**
     * 获取以指定包名为前缀的最后n条栈追踪
     *
     * @param stackTraceElements 栈追踪元素
     * @param packagePrefix      包前缀
     * @return 栈追踪
     */
    public static String stackTraceToString(StackTraceElement[] stackTraceElements,
                                            String packagePrefix,
                                            Integer n) {
        StringBuilder s = new StringBuilder();

        for (int i = 0; i < stackTraceElements.length; i++) {
            StackTraceElement stackTraceElement = stackTraceElements[i];
            // 如果是指定的包，则收集。（未指定也收集）
            if (!StringUtils.hasText(packagePrefix)
                    || stackTraceElement.getClassName().startsWith(packagePrefix)) {
                s.append(TRACE_PREFIX).append(stackTraceElement);
            }

            // 如果指定了行数，则收集满了就退出。（不指定则全部收集）
            if (n != null && i >= n) {
                break;
            }
        }

        return s.toString();
    }

    /**
     * 获取简略栈追踪（方法名加行号）
     *
     * @param stackTraceElements 栈追踪元素
     * @return 栈追踪
     */
    public static String stackTraceToStringBriefly(StackTraceElement[] stackTraceElements) {
        return stackTraceToStringBriefly(stackTraceElements, null, null);
    }

    /**
     * 获取最后n条简略栈追踪（方法名加行号）
     *
     * @param stackTraceElements 栈追踪元素
     * @param n                  最后n行
     * @return 栈追踪
     */
    public static String stackTraceToStringBriefly(StackTraceElement[] stackTraceElements,
                                                   Integer n) {
        return stackTraceToStringBriefly(stackTraceElements, null, n);
    }

    /**
     * 获取以指定包名为前缀的所有简略栈追踪（方法名+包名）
     *
     * @param stackTraceElements 栈追踪元素
     * @param packagePrefix      包前缀
     * @return 栈追踪
     */
    public static String stackTraceToStringBriefly(StackTraceElement[] stackTraceElements,
                                                   String packagePrefix) {
        return stackTraceToStringBriefly(stackTraceElements, packagePrefix, null);
    }

    /**
     * 获取以指定包名为前缀的最后n行简略栈追踪（方法名+包名）
     *
     * @param stackTraceElements 栈追踪元素
     * @param packagePrefix      包前缀
     * @param lineCount          行数
     * @return 栈追踪
     */
    public static String stackTraceToStringBriefly(StackTraceElement[] stackTraceElements,
                                                   String packagePrefix,
                                                   Integer lineCount) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < stackTraceElements.length; i++) {
            StackTraceElement stackTraceElement = stackTraceElements[i];
            // 如果是指定的包，则收集。（未指定也收集）
            if (StringUtils.hasText(packagePrefix)
                    || stackTraceElement.getClassName().startsWith(packagePrefix)) {
                String info = stackTraceElement.getMethodName() + ":" + stackTraceElement.getLineNumber();
                s.append(TRACE_PREFIX).append(info);
            }

            // 如果指定了行数，则收集满了就退出。（不指定则全部收集）
            if (lineCount != null && i >= lineCount) {
                break;
            }
        }

        return s.toString();
    }
}
