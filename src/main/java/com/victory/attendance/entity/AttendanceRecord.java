package com.victory.attendance.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.victory.attendance.enums.RecordType;
import com.victory.common.entity.BaseEntity;
import com.victory.common.entity.DateEntity;
import com.victory.hrm.entity.HrmResource;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


/**
 * 打卡原记录表
 *
 * @author ajkx_Du
 * @createDate 2016-10-19 14:35
 */
@Entity
@Table(name = "EHR_Attendancerecord")
public class AttendanceRecord extends DateEntity<Long> {

    @Column(name = "card_id")
    private String card;

    @Column(name = "machine_no")
    private String machineNo;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Column(name = "record_date")
    private Date date;

    @Column(name = "resource_id")
    private Long resource;

    @Column
    private RecordType type;

    @Column
    private String reason;

    public AttendanceRecord() {
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getMachineNo() {
        return machineNo;
    }

    public void setMachineNo(String machineNo) {
        this.machineNo = machineNo;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public RecordType getType() {
        return type;
    }

    public void setType(RecordType type) {
        this.type = type;
    }

    public Long getResource() {
        return resource;
    }

    public void setResource(Long resource) {
        this.resource = resource;
    }
}
