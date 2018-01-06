package com.victory.attendance.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.victory.common.entity.BaseEntity;
import com.victory.common.entity.DateEntity;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;


/**
 * Created by ajkx
 * Date: 2017/7/31.
 * Time:9:42
 */
@Entity
@Table(name = "EHR_Attendanceclasses")
public class AttendanceClasses extends DateEntity<Long> {

    //todo save时要判断name和shortname不能重复

    @Column(name = "class_name")
    private String name;

    @Column(name = "short_name")    // 简称
    private String shortName;

    @Column(name = "have_rest")     // 是否有休息班次 仅单班次有
    private Boolean haveRest;

    @Column(name = "off_duty_check")    // 下班是否打卡
    private Boolean offDutyCheck;

    @Column(name = "late_minute")   // 迟到的限定时间
    private Integer lateMinute;

    @Column(name = "early_minute")  //早退的限定时间
    private Integer earlyMinute;

    @Column(name = "work_time")     // 合计工作时间
    private Integer workTime;

    @OneToOne(targetEntity = AttendanceClassesDetail.class,cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "rest_time",referencedColumnName = "id")
    private AttendanceClassesDetail restTime;       // 单班次时的休息时间

    private String description;

    @OneToMany(targetEntity = AttendanceClassesDetail.class,cascade = CascadeType.ALL,orphanRemoval = true,mappedBy = "classes")
//    @JoinColumn(name = "class_id")
    private List<AttendanceClassesDetail> timeList = new ArrayList<>();

    @JsonBackReference
    @ManyToMany(targetEntity = AttendanceGroup.class, mappedBy = "classes")
    private Set<AttendanceGroup> groups;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Boolean getHaveRest() {
        return haveRest;
    }

    public void setHaveRest(Boolean haveRest) {
        this.haveRest = haveRest;
    }

    public Boolean getOffDutyCheck() {
        return offDutyCheck;
    }

    public void setOffDutyCheck(Boolean offDutyCheck) {
        this.offDutyCheck = offDutyCheck;
    }

    public Integer getLateMinute() {
        return lateMinute;
    }

    public void setLateMinute(Integer lateMinute) {
        this.lateMinute = lateMinute;
    }

    public Integer getEarlyMinute() {
        return earlyMinute;
    }

    public void setEarlyMinute(Integer earlyMinute) {
        this.earlyMinute = earlyMinute;
    }

    public Integer getWorkTime() {
        return workTime;
    }

    public void setWorkTime(Integer workTime) {
        this.workTime = workTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<AttendanceClassesDetail> getTimeList() {
        return timeList;
    }

    public void setTimeList(List<AttendanceClassesDetail> timeList) {
        this.timeList = timeList;
    }

    public AttendanceClassesDetail getRestTime() {
        return restTime;
    }

    public void setRestTime(AttendanceClassesDetail restTime) {
        this.restTime = restTime;
    }

    public Set<AttendanceGroup> getGroups() {
        return groups;
    }

    public void setGroups(Set<AttendanceGroup> groups) {
        this.groups = groups;
    }


}
