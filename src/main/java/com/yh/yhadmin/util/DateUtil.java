package com.yh.yhadmin.util;

import org.apache.commons.lang.StringUtils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class DateUtil {
    /**
     * 获取某一段日期的开始时间和结束时间。
     *
     * @param currentDay 日期字符串
     * @param separator  日期字符串的分隔符
     * @return 包含开始日期毫秒数和结束日期毫秒的map，开始key为<code>beg</code>，结束key<code>end</code>
     * @throws Exception
     */
    public static final Map<String, Long> getCurrentDayBeginAndEnd(String currentDay, String separator) throws Exception {
        Long beg = null;
        Long end = null;
        Map<String, Long> timeMap = new HashMap<>();
        if (StringUtils.isNotBlank(currentDay) && StringUtils.isNotBlank(separator)) {
            try {
                String[] dates = currentDay.split(separator);
                beg = parseToMills(dates[0] + " 00:00:00:000");
                end = parseToMills(dates[1] + " 23:59:59:999");
                timeMap.put("beg", beg);
                timeMap.put("end", end);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            return timeMap;
        }
        return null;
    }

    /**
     * 获取某一段日期的开始时间和结束时间。
     *
     * @param currentDay 日期字符串
     * @return 包含开始日期毫秒数和结束日期毫秒的map，开始key为<code>beg</code>，结束key<code>end</code>
     * @throws Exception
     */
    public static final Map<String, Date> getCurrentDayBeginAndEnd(String currentDay) {
        Map<String, Date> timeMap = new HashMap<>();

        try {
            timeMap.put("beg", parseToDateMilis(currentDay + " 00:00:00:000"));
            timeMap.put("end", parseToDateMilis(currentDay + " 23:59:59:999"));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return timeMap;
    }

    /**
     * 获得当天的开始时间
     *
     * @param millis
     * @return
     */
    public static final long startOfDayOfMilli(Long millis) {
        return LocalDateTime
                .of(Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDate(), LocalTime.MIDNIGHT)
                .atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 获得当天的结束时间
     *
     * @param millis
     * @return
     */
    public static final long endOfDayOfMilli(Long millis) {
        return LocalDateTime
                .of(Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDate(), LocalTime.MAX)
                .atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 将字符串日期格式解析为日期毫秒数。
     *
     * @param formatDate 日期字符串
     * @return 日期毫秒数
     * @throws ParseException
     */
    public static final Long parseToMills(String formatDate) throws ParseException {
        Date dt = parseToDate(formatDate);
        return dt.getTime();
    }

    /**
     * 计算开始日期和结束日期之间相差的月份数。
     *
     * @param bigger
     * @param smaller
     * @return
     * @throws ParseException
     */
    public static final int betweenMonths(String bigger, String smaller) throws ParseException {
        return betweenMonths(parseToDate(bigger), parseToDate(smaller));
    }

    public static final int betweenMonths(Date bigger, Date smaller) {
        if (bigger.compareTo(smaller) < 0)
            return 0;
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(bigger);
        c2.setTime(smaller);
        Integer year = c1.get(Calendar.YEAR) - c2.get(Calendar.YEAR);
        int month = c1.get(Calendar.MONTH) - c2.get(Calendar.MONTH);
        int days = c1.get(Calendar.DAY_OF_MONTH) - c2.get(Calendar.DAY_OF_MONTH);
        month += (year * 12);
        return days < 0 ? month - 1 : month;
    }


    /**
     * 计算开始日期和结束日期之间相差的小时数。
     *
     * @param begin 开始日期字符串
     * @param end   结束日期字符串
     * @return 小时数
     * @throws ParseException
     */
    public static final long betweenHour(String begin, String end) throws ParseException {
        long deltaMillis = parseToDateHour(end).getTime() - parseToDateHour(begin).getTime();
        return Duration.ofMillis(deltaMillis).toHours();
    }

    /**
     * 计算开始日期和结束日期之间相差的小时数。
     *
     * @param begin 开始日期毫秒数
     * @param end   结束日期毫秒数
     * @return 小时数
     * @throws ParseException
     */
    public static final long betweenHour(long begin, long end) {
        long deltaMillis = end - begin;
        return Duration.ofMillis(deltaMillis).toHours();
    }

    /**
     * 将完整日期解析为包含年月日时分的日期，不包括秒。
     *
     * @param formatDate 原始完整日期字符串
     * @return 包含年月日时分的日期，秒为0
     * @throws ParseException
     */
    public static final Date parseToDateHour(String formatDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            if (formatDate.contains("年") || formatDate.contains("月") || formatDate.contains("日")) {
                sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
                Date dt = sdf.parse(formatDate);
                return dt;
            }
            if (formatDate.contains("/")) {
                sdf = new SimpleDateFormat("yyyy/MM/dd HH/mm");
                Date dt = sdf.parse(formatDate);
                return dt;
            }
            Date dt = sdf.parse(formatDate);
            return dt;
        } catch (ParseException e) {
           e.printStackTrace();
        }
        return null;
    }

    /**
     * 将字符串日期格式化为日期对象。
     *
     * @param formatDate 字符串日期
     * @return 日期对象
     * @throws ParseException
     */
    public static final Date parseToDateMilis(String formatDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (formatDate.contains("年") || formatDate.contains("月") || formatDate.contains("日")) {
                sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
                Date dt = sdf.parse(formatDate);
                return dt;
            }
            if (formatDate.contains("/")) {
                sdf = new SimpleDateFormat("yyyy/MM/dd HH/mm/ss");
                Date dt = sdf.parse(formatDate);
                return dt;
            }
            Date dt = sdf.parse(formatDate);
            return dt;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将字符串日期格式化为包含年月日的日期，时分秒为0.
     *
     * @param formatDate 字符串日期
     * @return 日期对象
     * @throws ParseException
     */
    public static final Date parseToDate(String formatDate) {

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if (formatDate.contains("年") || formatDate.contains("月") || formatDate.contains("日")) {
                sdf = new SimpleDateFormat("yyyy年MM月dd日");
                Date dt = sdf.parse(formatDate);
                return dt;
            }
            if (formatDate.contains("/")) {
                sdf = new SimpleDateFormat("yyyy/MM/dd");
                Date dt = sdf.parse(formatDate);
                return dt;
            }
            Date dt = sdf.parse(formatDate);
            return dt;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将字符串日期格式化为包含年月日的日期，时分秒为0.
     *
     * @param formatDate 字符串日期
     * @return 日期对象
     * @throws ParseException
     */
    public static final Date parseToHour(String formatDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        if (formatDate.contains("时") || formatDate.contains("分")) {
            sdf = new SimpleDateFormat("HH时mm分");
            Date dt = sdf.parse(formatDate);
            return dt;
        }
        if (formatDate.contains("/")) {
            sdf = new SimpleDateFormat("HH/mm");
            Date dt = sdf.parse(formatDate);
            return dt;
        }
        Date dt = sdf.parse(formatDate);
        return dt;
    }

    public static final Date parseToPattern(String formatDate, String pattern) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            Date dt = sdf.parse(formatDate);
            return dt;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将字符串日期格式化为包含年月日的日期，时分秒为0.
     *
     * @param formatDate 字符串日期
     * @return 日期对象
     * @throws ParseException
     */
    public static final Date parseToHourMillis(String formatDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        if (formatDate.contains("时") || formatDate.contains("分") || formatDate.contains("秒")) {
            sdf = new SimpleDateFormat("HH时mm分ss秒");
            Date dt = sdf.parse(formatDate);
            return dt;
        }
        if (formatDate.contains("/")) {
            sdf = new SimpleDateFormat("HH/mm/ss");
            Date dt = sdf.parse(formatDate);
            return dt;
        }
        Date dt = sdf.parse(formatDate);
        return dt;
    }

    /**
     * 获取某一日期是当月的第几天，从1开始。
     *
     * @param instant 日期毫秒数
     * @return 当月第几天
     */
    public static final int getDayOfMonth(long instant) {
        return Instant.ofEpochMilli(instant).atZone(ZoneId.systemDefault()).getDayOfMonth();
    }

    public static String getBeginOfCurrentMonth(SimpleDateFormat format) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        String first = format.format(c.getTime());
        return first;
    }

    public static Date getBeginOfCurrentMonth() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        return c.getTime();
    }

    public static Date getEndOfCurrentMonth() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        return c.getTime();
    }

    public static String getEndOfCurrentMonth(long instant, SimpleDateFormat format) {
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        String last = format.format(ca.getTime());
        return last;
    }

    public static final long betweenDays(String begin, String end) throws ParseException {
        long beginTime = parseToMills(begin);
        long endTime = parseToMills(end);
        return countDays(endTime - beginTime);
    }

    public static final long betweenDays(Date begin, Date end) {
        long beginTime = begin.getTime();
        long endTime = end.getTime();
        return countDays(endTime - beginTime);
    }

    public static final long betweetMillis(Date begin, Date end) {
        long beginTime = begin.getTime();
        long endTime = end.getTime();
        return countMillis(endTime - beginTime);
    }

    public static final long countDays(long deltaMillis) {
        return Duration.ofMillis(deltaMillis).toDays();
    }

    public static final long countMillis(long deltaMillis) {
        return Duration.ofMillis(deltaMillis).toMillis();
    }


    public static final long plusMonths(long instant, long months) {
        return Instant.ofEpochMilli(instant).atZone(ZoneId.systemDefault()).plusMonths(months).toInstant().toEpochMilli();
    }

    public static final long plusDays(long instant, long days) {
        return Instant.ofEpochMilli(instant).atZone(ZoneId.systemDefault()).plusDays(days).toInstant().toEpochMilli();
    }

    public static final long minusDays(long instant, long days) {
        return Instant.ofEpochMilli(instant).atZone(ZoneId.systemDefault()).minusDays(days).toInstant().toEpochMilli();
    }

    public static final Long plusDays(String currentDay, long days){
        try{
            long current = parseToMills(currentDay);
            return plusDays(current, days);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static final Date plusYear(String currentDay, int year) throws ParseException {
        return new Date(plusDays(currentDay, year * 365));
    }

    public static final String plusDaysToString(String currentDay, long day, String pattern) throws ParseException {
        long millis = plusDays(currentDay, day);
        return millisToDateTime(millis, pattern);
    }

    public static final Date plusDaysToDate(String currentDay, long days) throws ParseException {
        long millis = plusDays(currentDay, days);
        return new Date(millis);
    }

    /**
     * 计算日期加上某天数后的日期。
     *
     * @param currentDay 原始日期
     * @param days       天数
     * @return 计算后的日期
     * @throws ParseException
     */
    public static final Date plusDaysToDate(Date currentDay, long days) {
        long millis = plusDays(currentDay.getTime(), days);
        return new Date(millis);
    }

    /**
     * 日期的毫秒数加上秒数，得到的日期毫秒。
     *
     * @param instant 日期的毫秒数
     * @param seconds 描述
     * @return 计算后的日期毫秒数
     */
    public static final long plusSeconds(long instant, long seconds) {
        return Instant.ofEpochMilli(instant).atZone(ZoneId.systemDefault()).plusSeconds(seconds).toInstant().toEpochMilli();
    }

    /**
     * 日期的毫秒数加上分钟数，得到的日期毫秒。
     *
     * @param instant 日期的毫秒数
     * @param minutes 分钟数
     * @return 计算后的日期毫秒数
     */
    public static final long plusMinutes(long instant, long minutes) {
        return Instant.ofEpochMilli(instant).atZone(ZoneId.systemDefault()).plusMinutes(minutes).toInstant().toEpochMilli();
    }

    /**
     * 计算日期加上某小时数后的日期。
     *
     * @param srcDate 原始日期
     * @param hours   小时数
     * @return 计算后日期
     */
    public static final Date plusHoursToDate(Date srcDate, int hours) {
        long milli = Instant.ofEpochMilli(srcDate.getTime()).atZone(ZoneId.systemDefault()).plusHours(hours).toInstant().toEpochMilli();
        return new Date(milli);
    }

    public static final String millisToDateTime(Long millis, String pattern) {
        LocalDateTime time = Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDateTime();
        return time.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static final String getBeginOfTheMouth(long instant) {
        return Instant.ofEpochMilli(instant).atZone(ZoneId.systemDefault()).with(TemporalAdjusters.firstDayOfMonth()).toString();
    }

    public static final boolean isBeforeThanToday(Date day) throws ParseException {
        long now = System.currentTimeMillis();
        return day.getTime() < now;
    }

    /**
     * 判断某一时间是否在一个区间内
     *
     * @param sourceTime 时间区间,半闭合,如[10:00-20:00)
     * @param curTime    需要判断的时间 如10:00
     * @return
     * @throws IllegalArgumentException
     */
    public static boolean isInTime(String sourceTime, String curTime) {
        if (sourceTime == null || !sourceTime.contains("-") || !sourceTime.contains(":")) {
            throw new IllegalArgumentException("Illegal Argument arg:" + sourceTime);
        }
        if (curTime == null || !curTime.contains(":")) {
            throw new IllegalArgumentException("Illegal Argument arg:" + curTime);
        }
        String[] args = sourceTime.split("-");
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        try {
            long now = sdf.parse(curTime).getTime();
            long start = sdf.parse(args[0]).getTime();
            long end = sdf.parse(args[1]).getTime();
            if (args[1].equals("00:00")) {
                args[1] = "24:00";
            }
            if (end < start) {
                if (now >= end && now < start) {
                    return false;
                } else {
                    return true;
                }
            } else {
                if (now >= start && now < end) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Illegal Argument arg:" + sourceTime);
        }
    }


    public static Date getWeekBegin(Date today) {
        Long intervalDay = Long.valueOf(LocalDate.now().getDayOfWeek().getValue() - DayOfWeek.MONDAY.getValue());
        Long millis = LocalDateTime.ofInstant(Instant.ofEpochMilli(today.getTime()), ZoneId.systemDefault()).toLocalDate().minusDays(intervalDay).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        return new Date(millis);
    }

    public static Date getWeekEnd(Date today) {
        Long intervalDay = Long.valueOf(DayOfWeek.SUNDAY.getValue() - LocalDate.now().getDayOfWeek().getValue());
        Long millis = LocalDateTime.ofInstant(Instant.ofEpochMilli(today.getTime()), ZoneId.systemDefault()).toLocalDate().plusDays(intervalDay).atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        return new Date(millis);
    }

    public static boolean hasTimeUnionSet(Date beg1, Date end1, Date beg2, Date end2){
        if (beg2.compareTo(end1) >= 0 || beg1.compareTo(end2) >= 0)
            return false;
        return true;
    }

    public static Date DateStringToDate(String dateTime){


        return null;
    }


    public static void main(String[] args) throws Exception {
        Date today = new Date();
//        LocalDate date = LocalDateTime.ofInstant(Instant.ofEpochMilli(today.getTime()), ZoneId.systemDefault()).toLocalDate();
//        System.out.println(DateUtil.betweenMonths("2000-2-29", "2000-1-31"));
//        Date time = DateUtil.parseToDate("2018-08-06 00:05:00");
//        time = DateUtil.plusHoursToDate(time, -1);
//        System.out.println(DateUtil.millisToDateTime(time.getTime(), "yyyy-MM-dd HH:mm:ss"));
        System.out.println(DateUtil.betweenMonths(DateUtil.parseToDate("2018-10-23"), DateUtil.parseToDate("2016-05-17")));
    }

}
