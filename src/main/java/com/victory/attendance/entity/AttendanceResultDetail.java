package com.victory.attendance.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.victory.attendance.enums.ResultType;
import com.victory.common.entity.BaseEntity;
import com.victory.common.entity.DateEntity;

import javax.persistence.*;
import java.sql.Time;

/**
 * Created by ajkx
 * Date: 2017/7/3.
 * Time:15:08
 */
@Entity
@Table(name="EHR_AttendanceResult_Detail")
public class AttendanceResultDetail extends BaseEntity<Long> {

    @JsonBackReference
    @ManyToOne(targetEntity = AttendanceClasses.class)
    @JoinColumn(name = "result_id")
    private AttendanceResult result;

    @Column(name = "should_begin_time")    // 班次规定上班时间
    private Time shouldBeginTime;

    @Column(name = "actual_begin_time")   // 实际上班打卡时间
    private Time actualBeginTime;

    @Column(name = "begin_result_type")   // 上班出勤结果
    private ResultType beginResultType;

    @Column(name = "should_end_time")      // 班次规定下班时间
    private Time shouldEndTime;

    @Column(name = "actual_end_time")     // 实际下班打卡时间
    private Time actualEndTime;

    @Column(name = "end_result_type")     // 下班出勤结果
    private ResultType endResultType;

    // long 类型都以秒为单位存
    //迟到时间
    @Column
    private Long lateTime;

    //迟到次数
    @Column
    private Integer lateCount;

    //早退时间
    @Column
    private Long earlyTime;

    //早退次数
    @Column
    private Integer earlyCount;

    //旷工时间 包括严重迟到和缺卡
    @Column
    private Long absentTime;

    //旷工次数 包括严重迟到和缺卡
    @Column
    private Integer absentCount;

    //事假
    @Column
    private Long personalLevel;

    //调休
    @Column
    private Long restLevel;

    //工伤
    @Column
    private Long injuryLevel;

    //产假
    @Column
    private Long deliveryLevel;

    //婚假
    @Column
    private Long maritalLevel;

    //丧假
    @Column
    private Long funeralLevel;

    //年假
    @Column
    private Long annualLevel;

    //销假
    @Column
    private Long cancelLevel;

    @Column
    private Long shouldWorkTime;

    @Column
    private Long actualWorkTime;

    public AttendanceResult getResult() {
        return result;
    }

    public void setResult(AttendanceResult result) {
        this.result = result;
    }

    public Time getShouldBeginTime() {
        return shouldBeginTime;
    }

    public void setShouldBeginTime(Time shouldBeginTime) {
        this.shouldBeginTime = shouldBeginTime;
    }

    public Time getActualBeginTime() {
        return actualBeginTime;
    }

    public void setActualBeginTime(Time actualBeginTime) {
        this.actualBeginTime = actualBeginTime;
    }

    public ResultType getBeginResultType() {
        return beginResultType;
    }

    public void setBeginResultType(ResultType beginResultType) {
        this.beginResultType = beginResultType;
    }

    public Time getShouldEndTime() {
        return shouldEndTime;
    }

    public void setShouldEndTime(Time shouldEndTime) {
        this.shouldEndTime = shouldEndTime;
    }

    public Time getActualEndTime() {
        return actualEndTime;
    }

    public void setActualEndTime(Time actualEndTime) {
        this.actualEndTime = actualEndTime;
    }

    public ResultType getEndResultType() {
        return endResultType;
    }

    public void setEndResultType(ResultType endResultType) {
        this.endResultType = endResultType;
    }

    public Long getLateTime() {
        return lateTime;
    }

    public void setLateTime(Long lateTime) {
        this.lateTime = lateTime;
    }

    public Integer getLateCount() {
        return lateCount;
    }

    public void setLateCount(Integer lateCount) {
        this.lateCount = lateCount;
    }

    public Long getEarlyTime() {
        return earlyTime;
    }

    public void setEarlyTime(Long earlyTime) {
        this.earlyTime = earlyTime;
    }

    public Integer getEarlyCount() {
        return earlyCount;
    }

    public void setEarlyCount(Integer earlyCount) {
        this.earlyCount = earlyCount;
    }

    public Long getAbsentTime() {
        return absentTime;
    }

    public void setAbsentTime(Long absentTime) {
        this.absentTime = absentTime;
    }

    public Integer getAbsentCount() {
        return absentCount;
    }

    public void setAbsentCount(Integer absentCount) {
        this.absentCount = absentCount;
    }

    public Long getPersonalLevel() {
        return personalLevel;
    }

    public void setPersonalLevel(Long personalLevel) {
        this.personalLevel = personalLevel;
    }

    public Long getRestLevel() {
        return restLevel;
    }

    public void setRestLevel(Long restLevel) {
        this.restLevel = restLevel;
    }

    public Long getInjuryLevel() {
        return injuryLevel;
    }

    public void setInjuryLevel(Long injuryLevel) {
        this.injuryLevel = injuryLevel;
    }

    public Long getDeliveryLevel() {
        return deliveryLevel;
    }

    public void setDeliveryLevel(Long deliveryLevel) {
        this.deliveryLevel = deliveryLevel;
    }

    public Long getMarriedLevel() {
        return maritalLevel;
    }

    public void setMarriedLevel(Long maritalLevel) {
        this.maritalLevel = maritalLevel;
    }

    public Long getFuneralLevel() {
        return funeralLevel;
    }

    public void setFuneralLevel(Long funeralLevel) {
        this.funeralLevel = funeralLevel;
    }

    public Long getAnnualLevel() {
        return annualLevel;
    }

    public void setAnnualLevel(Long annualLevel) {
        this.annualLevel = annualLevel;
    }

    public Long getCancelLevel() {
        return cancelLevel;
    }

    public void setCancelLevel(Long cancelLevel) {
        this.cancelLevel = cancelLevel;
    }

    public Long getShouldWorkTime() {
        return shouldWorkTime;
    }

    public void setShouldWorkTime(Long shouldWorkTime) {
        this.shouldWorkTime = shouldWorkTime;
    }

    public Long getActualWorkTime() {
        return actualWorkTime;
    }

    public void setActualWorkTime(Long actualWorkTime) {
        this.actualWorkTime = actualWorkTime;
    }
}
