package com.suchtool.nicetool.util.base;

import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.StringJoiner;

/**
 * 栈追踪工具类
 */
public class StackTraceUtil {
    private static final String TRACE_PREFIX = "\r\n    at ";

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
     * @param stackTraceElements      栈追踪元素
     * @param packagePrefixCollection 包前缀集合
     * @return 栈追踪
     */
    public static String stackTraceToString(StackTraceElement[] stackTraceElements,
                                            Collection<String> packagePrefixCollection) {
        return stackTraceToString(stackTraceElements, packagePrefixCollection, null);
    }

    /**
     * 获取以指定包名为前缀的最后n条栈追踪
     *
     * @param stackTraceElements      栈追踪元素
     * @param packagePrefixCollection 包前缀集合
     * @return 栈追踪
     */
    public static String stackTraceToString(StackTraceElement[] stackTraceElements,
                                            Collection<String> packagePrefixCollection,
                                            Integer n) {
        StringJoiner joiner = new StringJoiner(TRACE_PREFIX);

        for (int i = 0; i < stackTraceElements.length; i++) {
            StackTraceElement stackTraceElement = stackTraceElements[i];
            if (requireCollect(stackTraceElement, packagePrefixCollection)) {
                joiner.add(stackTraceElement.toString());
            }

            // 如果指定了行数，则收集满了就退出。（不指定则全部收集）
            if (n != null && i >= n) {
                break;
            }
        }

        return joiner.toString();
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
     * @param stackTraceElements      栈追踪元素
     * @param packagePrefixCollection 包前缀集合
     * @return 栈追踪
     */
    public static String stackTraceToStringBriefly(StackTraceElement[] stackTraceElements,
                                                   Collection<String> packagePrefixCollection) {
        return stackTraceToStringBriefly(stackTraceElements, packagePrefixCollection, null);
    }

    /**
     * 获取以指定包名为前缀的最后n行简略栈追踪（方法名+包名）
     *
     * @param stackTraceElements      栈追踪元素
     * @param packagePrefixCollection 包前缀集合
     * @param lineCount               行数
     * @return 栈追踪
     */
    public static String stackTraceToStringBriefly(StackTraceElement[] stackTraceElements,
                                                   Collection<String> packagePrefixCollection,
                                                   Integer lineCount) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < stackTraceElements.length; i++) {
            StackTraceElement stackTraceElement = stackTraceElements[i];
            // 如果是指定的包，则收集。（未指定也收集）
            if (requireCollect(stackTraceElement, packagePrefixCollection)) {
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

    // 如果是指定的包，则收集。（若指定包为空则也收集）
    private static boolean requireCollect(
            StackTraceElement stackTraceElement,
            Collection<String> packagePrefixCollection) {

        if (CollectionUtils.isEmpty(packagePrefixCollection)) {
            return true;
        }

        for (String packagePrefix : packagePrefixCollection) {
            if (stackTraceElement.getClassName().startsWith(packagePrefix)) {
                return true;
            }
        }

        return false;
    }
}
