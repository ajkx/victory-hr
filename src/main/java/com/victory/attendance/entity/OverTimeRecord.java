package com.victory.attendance.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.victory.attendance.enums.LevelType;
import com.victory.attendance.enums.OverTimeType;
import com.victory.attendance.enums.RecordStatus;
import com.victory.common.entity.DateEntity;
import com.victory.common.web.converter.JsonDateDeserializer;
import com.victory.hrm.entity.HrmResource;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Administrator on 2017/12/28.
 */
@Entity
@Table(name = "EHR_overtimerecord")
public class OverTimeRecord extends DateEntity<Long>{

    @ManyToOne(targetEntity = HrmResource.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "resource_id")
    private HrmResource resource;

    @Column
    private OverTimeType type;

    @JsonDeserialize(using = JsonDateDeserializer.class)
    @Column(name = "begin_date")
    private Date beginDate;

    @JsonDeserialize(using = JsonDateDeserializer.class)
    @Column(name = "end_date")
    private Date endDate;

    // 实际上班打卡时间
    @Column(name = "actual_begin_date")
    private Date actualBeginDate;

    // 实际下班打卡时间
    @Column(name = "actual_end_date")
    private Date actualEndDate;

    //申请分钟数  可调时间将以这个时间为准
    @Column(name = "apply_time")
    private Long applyTime;

    //实际加班时数 分钟单位
    @Column(name = "actual_time")
    private Long actualTime;

    @Column(name = "reason")
    private String reason;

    private RecordStatus status;

    // 异常备注
    private String remark;

    //是否连班
    @Column(name = "link")
    private Boolean link = false;

    //用于判断可调休的有效时间范围
    //minScope 总等于加班开始时间，即限制能用该加班记录的调休记录日期的开始日期必须大于加班结束日期
    @Column
    private Date minScope;

    @Column
    private Date maxScope;

    @Column
    private Float multiple = 1f;

    @ManyToOne(targetEntity = AttendanceResult.class,fetch = FetchType.LAZY)
    @JoinColumn(name = "result_id",referencedColumnName = "id")
    private AttendanceResult result;

    public HrmResource getResource() {
        return resource;
    }

    public void setResource(HrmResource resource) {
        this.resource = resource;
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

    public OverTimeType getType() {
        return type;
    }

    public void setType(OverTimeType type) {
        this.type = type;
    }

    public Date getActualBeginDate() {
        return actualBeginDate;
    }

    public void setActualBeginDate(Date actualBeginDate) {
        this.actualBeginDate = actualBeginDate;
    }

    public Date getActualEndDate() {
        return actualEndDate;
    }

    public void setActualEndDate(Date actualEndDate) {
        this.actualEndDate = actualEndDate;
    }

    public Boolean getLink() {
        return link;
    }

    public void setLink(Boolean link) {
        this.link = link;
    }

    public Date getMinScope() {
        return minScope;
    }

    public void setMinScope(Date minScope) {
        this.minScope = minScope;
    }

    public Date getMaxScope() {
        return maxScope;
    }

    public void setMaxScope(Date maxScope) {
        this.maxScope = maxScope;
    }

    public Float getMultiple() {
        return multiple;
    }

    public void setMultiple(Float multiple) {
        this.multiple = multiple;
    }

    public AttendanceResult getResult() {
        return result;
    }

    public void setResult(AttendanceResult result) {
        this.result = result;
    }
}
