package com.victory.common.utils;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/12/28.
 */
public class DateUtils {

    public static final long MILLIS_PER_SECOND = 1000L;
    public static final long MILLIS_PER_MINUTE = 60000L;
    public static final long MILLIS_PER_HOUR = 3600000L;
    public static final long MILLIS_PER_DAY = 86400000L;

    public static final long SECOND_PER_DAY = 86400L;

    public static Date getToday() {
        DateTime dateTime = new DateTime();
        return dateTime.withTimeAtStartOfDay().toDate();
    }

    public static Date getYesterday() {
        DateTime dateTime = new DateTime();
        dateTime.plusDays(-1);
        return dateTime.withTimeAtStartOfDay().toDate();
    }

    public static Date getYesterday(Date date) {
        DateTime dateTime = new DateTime(date);
        dateTime.plusDays(-1);
        return dateTime.withTimeAtStartOfDay().toDate();
    }

    public static Date getNextDay() {
        DateTime dateTime = new DateTime();
        dateTime.plusDays(1);
        return dateTime.withTimeAtStartOfDay().toDate();
    }

    public static Date getNextDay(Date date) {
        DateTime dateTime = new DateTime(date);
        dateTime.plusDays(1);
        return dateTime.withTimeAtStartOfDay().toDate();
    }
    /**
     * 不包含首尾
     * @param beginDate
     * @param endDate
     * @return
     */
    public static List<Date> getBetweenDays(Date beginDate, Date endDate) {
        List<Date> dateList = new ArrayList<>();
        DateTime begin = new DateTime(beginDate);
        DateTime end = new DateTime(endDate);
        begin = begin.plusDays(1);
        while (Days.daysBetween(begin, end).getDays() > 0) {
            dateList.add(begin.toDate());
            begin = begin.plusDays(1);
        }
        return dateList;
    }

    public static long getInterval(Date beginDate, Date endDate) {
        long timeMill = endDate.getTime() - beginDate.getTime();
        return timeMill;
    }

    public static Date plusMonths(Date date, int month) {
        DateTime dateTime = new DateTime(date);
        dateTime.plusMonths(month);
        // 返回该天的最后一秒
        return dateTime.millisOfDay().withMaximumValue().toDate();
    }

    public static Date getMonthFirstDay() {
        DateTime dateTime = new DateTime();
        return dateTime.dayOfMonth().withMinimumValue().toDate();
    }

    public static Date getMonthLastDay(Date date) {
        DateTime dateTime = new DateTime(date);
        return dateTime.dayOfMonth().withMaximumValue().toDate();
    }

    public static int getDayOfWeek(Date date) {
        DateTime dateTime = new DateTime(date);
        return dateTime.getDayOfWeek();
    }

    /**
     * true 为单周
     * false 为双周
     * @param date
     * @return
     */
    public static boolean checkOddWeek(Date date) {
        DateTime dateTime = new DateTime(date);
        int weekOfYear = dateTime.getWeekOfWeekyear();
        return weekOfYear % 2 != 0;
    }

    public static String getTimeBySecond(Long value) {
        if(value == null || value <= 0) return "";
        DateTime dateTime = new DateTime(value * 1000);
        String result = dateTime.toString("HH:mm");
        if (value > MILLIS_PER_DAY) {
            result = "次日"+result;
        }
        return result;
    }

    public static String getTimeByMilli(Long value) {
        if(value == null || value <= 0) return "";
        DateTime dateTime = new DateTime(value);
        String result = dateTime.toString("HH:mm");
        if (value > MILLIS_PER_DAY) {
            result = "次日"+result;
        }
        return result;
    }
}
