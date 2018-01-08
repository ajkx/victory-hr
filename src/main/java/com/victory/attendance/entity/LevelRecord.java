package com.victory.attendance.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.victory.attendance.enums.LevelType;
import com.victory.attendance.enums.RecordStatus;
import com.victory.common.entity.DateEntity;
import com.victory.common.web.converter.JsonDateDeserializer;
import com.victory.hrm.entity.HrmResource;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Created by Administrator on 2017/12/28.
 */
@Entity
@Table(name = "EHR_levelrecord")
public class LevelRecord extends DateEntity<Long>{

    @ManyToOne(targetEntity = HrmResource.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "resource_id")
    private HrmResource resource;

    @Column
    private LevelType type;

    @JsonDeserialize(using = JsonDateDeserializer.class)
    @Column(name = "begin_date")
    private Date beginDate;

    @JsonDeserialize(using = JsonDateDeserializer.class)
    @Column(name = "end_date")
    private Date endDate;

    //申请分钟数
    @Column(name = "apply_time")
    private Long applyTime;

    //实际工作时 分钟单位
    @Column(name = "actual_time")
    private Long actualTime;

    @Column(name = "reason")
    private String reason;

    private RecordStatus status;

    // 异常备注
    private String remark;

    @ManyToMany(targetEntity = AttendanceResult.class)
    @JoinTable(name = "EHR_result_levelrecord",
            joinColumns = @JoinColumn(name = "record_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "result_id",referencedColumnName = "id"))
    private Set<AttendanceResult> results;

    public HrmResource getResource() {
        return resource;
    }

    public void setResource(HrmResource resource) {
        this.resource = resource;
    }

    public LevelType getType() {
        return type;
    }

    public void setType(LevelType type) {
        this.type = type;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Long getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Long applyTime) {
        this.applyTime = applyTime;
    }

    public Long getActualTime() {
        return actualTime;
    }

    public void setActualTime(Long actualTime) {
        this.actualTime = actualTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public RecordStatus getStatus() {
        return status;
    }

    public void setStatus(RecordStatus status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Set<AttendanceResult> getResults() {
        return results;
    }

    public void setResults(Set<AttendanceResult> results) {
        this.results = results;
    }
}
