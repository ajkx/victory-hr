package com.victory.attendance.entity;

import com.victory.attendance.enums.RecordStatus;
import com.victory.attendance.enums.ResultType;
import com.victory.common.entity.DateEntity;
import com.victory.hrm.entity.HrmResource;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.*;

/**
 * Created by ajkx
 * Date: 2017/7/3.
 * Time:15:08
 */
@Entity
@Table(name="EHR_Attendanceresult")
public class AttendanceResult extends DateEntity<Long> {

    @ManyToOne(targetEntity = HrmResource.class)
    @JoinColumn(name = "resource_id")
    private HrmResource resource;

    @ManyToOne(targetEntity = AttendanceClasses.class)
    @JoinColumn(name = "class_id")
    private AttendanceClasses classes;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date date;

    // 加班时间 分钟为单位
    @Column
    private Integer normalOvertime = 0;

    @Column
    private Integer weekendOvertime = 0;

    @Column
    private Integer festivalOvertime = 0;

    @Column
    private Integer shouldWorkDay = 0;

    @Column
    private Double actualWorkDay = 0.0;

    //三种数据状态
    //normal - 正常（初始化状态）  abnormal - 异常（有异常出勤）  calculate - 待计算（跨天明细）
    private RecordStatus status;


    @OneToMany(targetEntity = AttendanceResultDetail.class,cascade = CascadeType.ALL,orphanRemoval = true,mappedBy = "result")
//    @JoinColumn(name = "class_id")
    private List<AttendanceResultDetail> details = new ArrayList<>();

    //请假关联
    @ManyToMany(targetEntity = LevelRecord.class)
    @JoinTable(name = "EHR_result_levelRecord",
            joinColumns = @JoinColumn(name = "result_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "record_id",referencedColumnName = "id"))
    private Set<LevelRecord> levelRecords = new HashSet<>();

    //加班关联
    @OneToMany(targetEntity = OverTimeRecord.class,mappedBy = "result")
    private Set<OverTimeRecord> overTimeRecords = new HashSet<>();

    //异常记录关联
    @OneToMany(targetEntity = RepairRecord.class, mappedBy = "result")
    private Set<RepairRecord> repairRecords = new HashSet<>();

    public HrmResource getResource() {
        return resource;
    }

    public void setResource(HrmResource resource) {
        this.resource = resource;
    }

    public AttendanceClasses getClasses() {
        return classes;
    }

    public void setClasses(AttendanceClasses classes) {
        this.classes = classes;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getNormalOvertime() {
        return normalOvertime;
    }

    public void setNormalOvertime(Integer normalOvertime) {
        this.normalOvertime = normalOvertime;
    }

    public Integer getWeekendOvertime() {
        return weekendOvertime;
    }

    public void setWeekendOvertime(Integer weekendOvertime) {
        this.weekendOvertime = weekendOvertime;
    }

    public Integer getFestivalOvertime() {
        return festivalOvertime;
    }

    public void setFestivalOvertime(Integer festivalOvertime) {
        this.festivalOvertime = festivalOvertime;
    }

    public Integer getShouldWorkDay() {
        return shouldWorkDay;
    }

    public void setShouldWorkDay(Integer shouldWorkDay) {
        this.shouldWorkDay = shouldWorkDay;
    }

    public Double getActualWorkDay() {
        return actualWorkDay;
    }

    public void setActualWorkDay(Double actualWorkDay) {
        this.actualWorkDay = actualWorkDay;
    }

    public RecordStatus getStatus() {
        return status;
    }

    public void setStatus(RecordStatus status) {
        this.status = status;
    }

    public Set<LevelRecord> getLevelRecords() {
        return levelRecords;
    }

    public void setLevelRecords(Set<LevelRecord> levelRecords) {
        this.levelRecords = levelRecords;
    }

    public Set<OverTimeRecord> getOverTimeRecords() {
        return overTimeRecords;
    }

    public void setOverTimeRecords(Set<OverTimeRecord> overTimeRecords) {
        this.overTimeRecords = overTimeRecords;
    }

    public Set<RepairRecord> getRepairRecords() {
        return repairRecords;
    }

    public void setRepairRecords(Set<RepairRecord> repairRecords) {
        this.repairRecords = repairRecords;
    }

    public List<AttendanceResultDetail> getDetails() {
        return details;
    }

    public void setDetails(List<AttendanceResultDetail> details) {
        this.details = details;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        if (this.resource.getId() == ((AttendanceResult)o).getResource().getId() && this.date.equals(((AttendanceResult)o).getDate())) return true;
        DateEntity<?> that = (DateEntity<?>) o;

        return getId() == that.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), resource, date);
    }
}
