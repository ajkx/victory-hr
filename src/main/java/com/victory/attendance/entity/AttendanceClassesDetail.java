package com.victory.attendance.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.victory.common.entity.BaseEntity;
import com.victory.common.web.converter.JsonTimeDeserializer;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Time;

/**
 * Created by Administrator on 2017/11/22.
 */
@JsonIgnoreProperties({"id"})
@Entity
@Table(name = "EHR_Attendanceclasses_Detail")
public class AttendanceClassesDetail extends BaseEntity<Long>{

    @JsonBackReference
    @ManyToOne(targetEntity = AttendanceClasses.class)
    @JoinColumn(name = "class_id")
    private AttendanceClasses classes;

//    @Column
//    private ClassesDetailType type;
//    @JsonDeserialize(using = JsonTimeDeserializer.class)
    @Column(name = "begin_time")    // 班次上班时间
    private Time beginTime;

//    @JsonDeserialize(using = JsonTimeDeserializer.class)
    @Column(name = "end_time")      // 班次下班时间
    private Time endTime;

    @Column(name = "begin_across")  // 上班班次是否跨天
    private Boolean beginAcross;

    @Column(name = "end_across")    // 下班班次是否跨天
    private Boolean endAcross;

    @Column(name = "begin_minute")  // 上班有效打卡分钟数
    private Integer beginMinute = 30;

    @Column(name = "end_minute")    // 下班有效打卡分钟数
    private Integer endMinute = 30 ;

//    public ClassesDetailType getType() {
//        return type;
//    }
//
//    public void setType(ClassesDetailType type) {
//        this.type = type;
//    }

    public Time getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Time beginTime) {
        this.beginTime = beginTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public Boolean getBeginAcross() {
        return beginAcross;
    }

    public void setBeginAcross(Boolean beginAcross) {
        this.beginAcross = beginAcross;
    }

    public Boolean getEndAcross() {
        return endAcross;
    }

    public void setEndAcross(Boolean endAcross) {
        this.endAcross = endAcross;
    }

    public Integer getBeginMinute() {
        return beginMinute;
    }

    public void setBeginMinute(Integer beginMinute) {
        this.beginMinute = beginMinute;
    }

    public Integer getEndMinute() {
        return endMinute;
    }

    public void setEndMinute(Integer endMinute) {
        this.endMinute = endMinute;
    }

    public AttendanceClasses getClasses() {
        return classes;
    }

    public void setClasses(AttendanceClasses classes) {
        this.classes = classes;
    }

}
