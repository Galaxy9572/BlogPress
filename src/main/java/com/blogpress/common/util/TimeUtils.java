package com.blogpress.common.util;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

/**
 * 时间处理工具
 * @author JY
 */
public class TimeUtils {

    private static final Pattern DATE_PATTERN = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");

    private static final Pattern DATE_TIME_PATTERN = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 判断是否为日期格式
     * @param dateString 日期字符串
     * @return 是返回true，否则false
     */
    public static boolean isDateFormat(String dateString){
        if(StringUtils.isBlank(dateString)){
            return false;
        }
        return DATE_PATTERN.matcher(dateString).matches();
    }

    /**
     * 判断是否为日期时间格式
     * @param dateTimeString 日期时间格式字符串
     * @return 是返回true，否则false
     */
    public static boolean isDateTimeFormat(String dateTimeString){
        if(StringUtils.isBlank(dateTimeString)){
            return false;
        }
        return DATE_TIME_PATTERN.matcher(dateTimeString).matches();
    }

    /**
     * 将yyyy-MM-dd格式的时间字符串转为LocalDate
     * @param date yyyy-MM-dd格式的时间字符串
     * @return LocalDate
     */
    public static LocalDate toLocalDate(String date) {
        return LocalDate.parse(date, DATE_FORMATTER);
    }

    /**
     * 将yyyy-MM-dd HH:mm:ss格式的时间字符串转为LocalDateTime
     * @param dateTime yyyy-MM-dd HH:mm:ss格式的时间字符串
     * @return LocalDate
     */
    public static LocalDateTime toLocalDateTime(String dateTime) {
        return LocalDateTime.parse(dateTime, DATE_TIME_FORMATTER);
    }

    /**
     * 将LocalDate转为yyyy-MM-dd格式字符串
     * @param date LocalDate
     * @return yyyy-MM-dd格式的时间字符串
     */
    public static String toDateString(LocalDate date) {
        return date.format(DATE_FORMATTER);
    }

    /**
     * 将yyyy-MM-dd HH:mm:ss格式字符串转为LocalDateTime
     * @param dateTime LocalDateTime
     * @return yyyy-MM-dd HH:mm:ss格式字符串
     */
    public static String toDateTimeString(LocalDateTime dateTime) {
        return dateTime.format(DATE_TIME_FORMATTER);
    }

    /**
     * 时间戳转为LocalDateTime
     * @param timestamp 时间戳
     * @return LocalDateTime
     */
    public static LocalDateTime fromTimestamp(Long timestamp){
        if(timestamp == null) {
            throw new IllegalArgumentException("timestamp cannot null");
        }
        return LocalDateTime.ofEpochSecond(timestamp, 0, OffsetDateTime.now().getOffset());
    }

}
