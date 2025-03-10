package com.suchtool.nicetool.util.base;

import org.springframework.util.StringUtils;

import java.util.Collection;

/**
 * 异常工具类
 */
public class ThrowableUtil {
    private static final String TRACE_PREFIX = "\r\n    at ";

    /**
     * 获取以指定包名为前缀的栈追踪
     *
     * @param t 异常
     * @return 栈追踪
     */
    public static String stackTraceToString(Throwable t) {
        return t.toString()
                + wrapStackTrace(StackTraceUtil.stackTraceToString(t.getStackTrace()));
    }

    /**
     * 获取最后n条栈追踪
     *
     * @param t         异常对象
     * @param lineCount 行数
     * @return 栈追踪
     */
    public static String stackTraceToString(Throwable t, Integer lineCount) {
        return t.toString()
                + wrapStackTrace(StackTraceUtil.stackTraceToString(t.getStackTrace(), lineCount));
    }

    /**
     * 获取以指定包名为前缀的所有栈追踪
     *
     * @param t                       异常
     * @param packagePrefixCollection 包前缀集合
     * @return 栈追踪
     */
    public static String stackTraceToString(Throwable t,
                                            Collection<String> packagePrefixCollection) {
        return t.toString()
                + wrapStackTrace(StackTraceUtil.stackTraceToString(t.getStackTrace(), packagePrefixCollection));
    }

    /**
     * 获取以指定包名为前缀的最后n条栈追踪
     *
     * @param t                       异常
     * @param packagePrefixCollection 包前缀集合
     * @param lineCount               行数
     * @return 栈追踪
     */
    public static String stackTraceToString(Throwable t,
                                            Collection<String> packagePrefixCollection,
                                            Integer lineCount) {
        return t.toString()
                + wrapStackTrace(StackTraceUtil.stackTraceToString(t.getStackTrace(), packagePrefixCollection, lineCount));
    }

    /**
     * 获取最后n条简略栈追踪（方法名加行号）
     *
     * @param t         异常对象
     * @param lineCount 行数
     * @return 栈追踪
     */
    public static String stackTraceToStringBriefly(Throwable t,
                                                   Integer lineCount) {
        return t.toString()
                + wrapStackTrace(StackTraceUtil.stackTraceToStringBriefly(t.getStackTrace(), lineCount));
    }

    /**
     * 获取以指定包名为前缀的所有简略栈追踪（方法名+包名）
     *
     * @param t                       异常
     * @param packagePrefixCollection 包前缀集合
     * @return 栈追踪
     */
    public static String stackTraceToStringBriefly(Throwable t,
                                                   Collection<String> packagePrefixCollection) {
        return t.toString()
                + wrapStackTrace(StackTraceUtil.stackTraceToStringBriefly(t.getStackTrace(), packagePrefixCollection));
    }

    /**
     * 获取以指定包名为前缀的最后n行简略栈追踪（方法名+包名）
     *
     * @param t                       异常
     * @param packagePrefixCollection 包前缀集合
     * @param lineCount               行数
     * @return 栈追踪
     */
    public static String stackTraceToStringBriefly(Throwable t,
                                                   Collection<String> packagePrefixCollection,
                                                   Integer lineCount) {
        return t.toString()
                + wrapStackTrace(StackTraceUtil.stackTraceToStringBriefly(t.getStackTrace(), packagePrefixCollection, lineCount));
    }

    private static String wrapStackTrace(String stackTrace) {
        if (StringUtils.hasText(stackTrace)) {
            return TRACE_PREFIX + stackTrace;
        } else {
            return "";
        }
    }
}
