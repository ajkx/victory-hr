package com.victory.common.utils;

import org.joda.time.DateTime;

import java.util.Date;

/**
 * Created by Administrator on 2017/12/28.
 */
public class DateUtils {

    public static final long MILLIS_PER_SECOND = 1000L;
    public static final long MILLIS_PER_MINUTE = 60000L;
    public static final long MILLIS_PER_HOUR = 3600000L;
    public static final long MILLIS_PER_DAY = 86400000L;

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
}
