package com.victory.attendance.web.vo;

import com.victory.attendance.enums.CalculateType;
import com.victory.attendance.enums.UnitType;

import java.sql.Time;
import java.util.Set;

/**
 * Created by Administrator on 2017/12/29.
 */
public class SettingOvertimeInfo {

    private UnitType overtimeUnitType;

    private CalculateType calculateType = CalculateType.regist;

    private int beginMinute = 30;

    private int endMinute = 30;

    private Boolean acrossDay = false;

    private Time acrossOffset;

    private int normalPeriod = 0;

    private int weekendPeriod = 3;

    private int festivalPeriod = 3;

    public CalculateType getCalculateType() {
        return calculateType;
    }

    public UnitType getOvertimeUnitType() {
        return overtimeUnitType;
    }

    public void setOvertimeUnitType(UnitType overtimeUnitType) {
        this.overtimeUnitType = overtimeUnitType;
    }

    public void setCalculateType(CalculateType calculateType) {
        this.calculateType = calculateType;
    }

    public int getBeginMinute() {
        return beginMinute;
    }

    public void setBeginMinute(int beginMinute) {
        this.beginMinute = beginMinute;
    }

    public int getEndMinute() {
        return endMinute;
    }

    public void setEndMinute(int endMinute) {
        this.endMinute = endMinute;
    }

    public Boolean getAcrossDay() {
        return acrossDay;
    }

    public void setAcrossDay(Boolean acrossDay) {
        this.acrossDay = acrossDay;
    }

    public Time getAcrossOffset() {
        return acrossOffset;
    }

    public void setAcrossOffset(Time acrossOffset) {
        this.acrossOffset = acrossOffset;
    }

    public int getNormalPeriod() {
        return normalPeriod;
    }

    public void setNormalPeriod(int normalPeriod) {
        this.normalPeriod = normalPeriod;
    }

    public int getWeekendPeriod() {
        return weekendPeriod;
    }

    public void setWeekendPeriod(int weekendPeriod) {
        this.weekendPeriod = weekendPeriod;
    }

    public int getFestivalPeriod() {
        return festivalPeriod;
    }

    public void setFestivalPeriod(int festivalPeriod) {
        this.festivalPeriod = festivalPeriod;
    }
}
