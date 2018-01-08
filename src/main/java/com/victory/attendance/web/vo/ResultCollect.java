package com.victory.attendance.web.vo;

import com.victory.hrm.entity.HrmResource;

/**
 * 用于生成考勤汇总数据的载体，spring会自动给该接口生成代理类来接收返回的结果
 */
public interface ResultCollect {


    long getResourceId();

    String getResourceName();

    String getWorkCode();

    String getDepartmentId();

    String getDepartmentName();

    int getShouldWorkDay();

    double getActualWorkDay();

    long getShouldWorkTime();

    long getActualWorkTime();

    long getNormalOvertime();
    long getWeekendOvertime();
    long getFestivalOvertime();

    long getLateTime();
    long getLateCount();
    long getEarlyTime();

    long getEarlyCount();

    long getAbsentTime();
    long getAbsentCount();
    long getPersonalLevel();
    long getRestLevel();
    long getInjuryLevel();
    long getDeliveryLevel();

    long getMaritalLevel();

    long getFuneralLevel();

    long getAnnualLevel();

    long getCancelLevel();
}
