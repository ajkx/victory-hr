package com.victory.attendance.web.vo;

/**
 * 用于生成考勤汇总数据的载体，spring会自动给该接口生成代理类来接收返回的结果
 */
public class ResultCollectClass {

    private String resourceName;
    private String workCode;
    private int departmentId;
    private String departmentName;

    private int resourceId;

    private int shouldWorkDay;
    private double actualWorkDay;

    private int normalOvertime;
    private int weekendOvertime;
    private int festivalOvertime;

    private int lateTime;
    private int lateCount;
    private int earlyTime;
    private int earlyCount;
    private int absentTime;
    private int absentCount;
    private int personalLevel;
    private int restLevel;
    private int injuryLevel;
    private int deliveryLevel;
    private int maritalLevel;
    private int funeralLevel;
    private int annualLevel;
    private int cancelLevel;

    private int shouldWorkTime;
    private int actualWorkTime;

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getWorkCode() {
        return workCode;
    }

    public void setWorkCode(String workCode) {
        this.workCode = workCode;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public int getShouldWorkDay() {
        return shouldWorkDay;
    }

    public void setShouldWorkDay(int shouldWorkDay) {
        this.shouldWorkDay = shouldWorkDay;
    }

    public double getActualWorkDay() {
        return actualWorkDay;
    }

    public void setActualWorkDay(double actualWorkDay) {
        this.actualWorkDay = actualWorkDay;
    }

    public int getNormalOvertime() {
        return normalOvertime;
    }

    public void setNormalOvertime(int normalOvertime) {
        this.normalOvertime = normalOvertime;
    }

    public int getWeekendOvertime() {
        return weekendOvertime;
    }

    public void setWeekendOvertime(int weekendOvertime) {
        this.weekendOvertime = weekendOvertime;
    }

    public int getFestivalOvertime() {
        return festivalOvertime;
    }

    public void setFestivalOvertime(int festivalOvertime) {
        this.festivalOvertime = festivalOvertime;
    }

    public int getLateTime() {
        return lateTime;
    }

    public void setLateTime(int lateTime) {
        this.lateTime = lateTime;
    }

    public int getLateCount() {
        return lateCount;
    }

    public void setLateCount(int lateCount) {
        this.lateCount = lateCount;
    }

    public int getEarlyTime() {
        return earlyTime;
    }

    public void setEarlyTime(int earlyTime) {
        this.earlyTime = earlyTime;
    }

    public int getEarlyCount() {
        return earlyCount;
    }

    public void setEarlyCount(int earlyCount) {
        this.earlyCount = earlyCount;
    }

    public int getAbsentTime() {
        return absentTime;
    }

    public void setAbsentTime(int absentTime) {
        this.absentTime = absentTime;
    }

    public int getAbsentCount() {
        return absentCount;
    }

    public void setAbsentCount(int absentCount) {
        this.absentCount = absentCount;
    }

    public int getPersonalLevel() {
        return personalLevel;
    }

    public void setPersonalLevel(int personalLevel) {
        this.personalLevel = personalLevel;
    }

    public int getRestLevel() {
        return restLevel;
    }

    public void setRestLevel(int restLevel) {
        this.restLevel = restLevel;
    }

    public int getInjuryLevel() {
        return injuryLevel;
    }

    public void setInjuryLevel(int injuryLevel) {
        this.injuryLevel = injuryLevel;
    }

    public int getDeliveryLevel() {
        return deliveryLevel;
    }

    public void setDeliveryLevel(int deliveryLevel) {
        this.deliveryLevel = deliveryLevel;
    }

    public int getMaritalLevel() {
        return maritalLevel;
    }

    public void setMaritalLevel(int maritalLevel) {
        this.maritalLevel = maritalLevel;
    }

    public int getFuneralLevel() {
        return funeralLevel;
    }

    public void setFuneralLevel(int funeralLevel) {
        this.funeralLevel = funeralLevel;
    }

    public int getAnnualLevel() {
        return annualLevel;
    }

    public void setAnnualLevel(int annualLevel) {
        this.annualLevel = annualLevel;
    }

    public int getCancelLevel() {
        return cancelLevel;
    }

    public void setCancelLevel(int cancelLevel) {
        this.cancelLevel = cancelLevel;
    }

    public int getShouldWorkTime() {
        return shouldWorkTime;
    }

    public void setShouldWorkTime(int shouldWorkTime) {
        this.shouldWorkTime = shouldWorkTime;
    }

    public int getActualWorkTime() {
        return actualWorkTime;
    }

    public void setActualWorkTime(int actualWorkTime) {
        this.actualWorkTime = actualWorkTime;
    }
}
