package com.suchtool.nicetool.util.base;

/**
 * 异常工具类
 */
public class ThrowableUtil {
    /**
     * 获取以指定包名为前缀的栈追踪
     *
     * @param t 异常
     * @return 栈追踪
     */
    public static String stackTraceToString(Throwable t) {
        return t.toString() + StackTraceUtil.stackTraceToString(t.getStackTrace());
    }

    /**
     * 获取最后n条栈追踪
     *
     * @param t         异常对象
     * @param lineCount 行数
     * @return 栈追踪
     */
    public static String stackTraceToString(Throwable t, Integer lineCount) {
        return t.toString() + StackTraceUtil.stackTraceToString(t.getStackTrace(), lineCount);
    }

    /**
     * 获取以指定包名为前缀的所有栈追踪
     *
     * @param t             异常
     * @param packagePrefix 包前缀
     * @return 栈追踪
     */
    public static String stackTraceToString(Throwable t,
                                            String packagePrefix) {
        return t.toString() + StackTraceUtil.stackTraceToString(t.getStackTrace(), packagePrefix);
    }

    /**
     * 获取以指定包名为前缀的最后n条栈追踪
     *
     * @param t             异常
     * @param packagePrefix 包前缀
     * @param lineCount     行数
     * @return 栈追踪
     */
    public static String stackTraceToString(Throwable t,
                                            String packagePrefix,
                                            Integer lineCount) {
        return t.toString() + StackTraceUtil.stackTraceToString(t.getStackTrace(), packagePrefix, lineCount);
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
        return t.toString() + StackTraceUtil.stackTraceToStringBriefly(t.getStackTrace(), lineCount);
    }

    /**
     * 获取以指定包名为前缀的所有简略栈追踪（方法名+包名）
     *
     * @param t             异常
     * @param packagePrefix 包前缀
     * @return 栈追踪
     */
    public static String stackTraceToStringBriefly(Throwable t,
                                                   String packagePrefix) {
        return t.toString() + StackTraceUtil.stackTraceToStringBriefly(t.getStackTrace(), packagePrefix);
    }

    /**
     * 获取以指定包名为前缀的最后n行简略栈追踪（方法名+包名）
     *
     * @param t             异常
     * @param packagePrefix 包前缀
     * @param lineCount     行数
     * @return 栈追踪
     */
    public static String stackTraceToStringBriefly(Throwable t,
                                                   String packagePrefix,
                                                   Integer lineCount) {
        return t.toString() + StackTraceUtil.stackTraceToStringBriefly(t.getStackTrace(), packagePrefix, lineCount);
    }
}
