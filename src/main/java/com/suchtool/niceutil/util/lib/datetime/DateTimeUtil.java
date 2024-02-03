package com.suchtool.niceutil.util.lib.datetime;


import com.suchtool.niceutil.util.lib.datetime.constant.DateTimeFormatConstant;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateTimeUtil {
    /**
     * 格式化为字符串
     *
     * @param localDateTime 时间
     * @param format        格式。常用格式见{@link DateTimeFormatConstant}
     */
    public static String format(LocalDateTime localDateTime,
                                String format) {
        return localDateTime.format(DateTimeFormatter.ofPattern(format));
    }

    /**
     * 格式化为字符串
     *
     * @param localDate 日期
     * @param format    格式。常用格式见{@link DateTimeFormatConstant}
     */
    public static String format(LocalDate localDate,
                                String format) {
        return localDate.format(DateTimeFormatter.ofPattern(format));
    }

    /**
     * 格式化为字符串
     *
     * @param localTime 时间
     * @param format    格式。常用格式见{@link DateTimeFormatConstant}
     */
    public static String format(LocalTime localTime,
                                String format) {
        return localTime.format(DateTimeFormatter.ofPattern(format));
    }

    /**
     * 解析为时间
     * @param localDateTimeString 时间字符串
     * @param format 格式。常用格式见{@link DateTimeFormatConstant}
     * @return 时间
     */
    public static LocalDateTime parseToLocalDateTime(String localDateTimeString,
                                                     String format) {
        return LocalDateTime.parse(localDateTimeString, DateTimeFormatter.ofPattern(format));
    }

    /**
     * 解析为日期
     * @param localDateString 日期字符串
     * @param format 格式。常用格式见{@link DateTimeFormatConstant}
     * @return 日期
     */
    public static LocalDate parseToLocalDate(String localDateString,
                                             String format) {
        return LocalDate.parse(localDateString, DateTimeFormatter.ofPattern(format));
    }

    /**
     * 解析为时间
     * @param localTimeString 时间字符串
     * @param format 格式。常用格式见{@link DateTimeFormatConstant}
     * @return 时间
     */
    public static LocalTime parseToLocalTime(String localTimeString,
                                             String format) {
        return LocalTime.parse(localTimeString, DateTimeFormatter.ofPattern(format));
    }

    public static LocalDateTime toLocalLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static LocalDate toLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalTime toLocalTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
    }

    public static Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date toDate(LocalDate localDate) {
        return Date.from(localDate
                .atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }

    public static Long toTimeStamp(LocalDateTime localDateTime) {
        return localDateTime
                .atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli();
    }

    public static Long toTimeStamp(LocalDate localDate) {
        return localDate
                .atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant().toEpochMilli();
    }

    public static LocalDateTime toLocalDateTime(Long timeStamp) {
        return LocalDateTime.ofInstant(
                Instant.ofEpochMilli(timeStamp),
                ZoneId.systemDefault());
    }

    public static LocalDate toLocalDate(Long timeStamp) {
        return LocalDateTime.ofInstant(
                        Instant.ofEpochMilli(timeStamp),
                        ZoneId.systemDefault()
                )
                .toLocalDate();
    }

    // public static void main(String[] args) {
    //     String format = format(LocalDateTime.now(), DateTimeFormatConstant.DATE_TIME_FORMAT_NORMAL);
    //     System.out.println(format);
    // }
}
