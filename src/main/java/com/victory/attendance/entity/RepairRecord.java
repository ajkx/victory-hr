package com.victory.attendance.entity;

import com.victory.attendance.enums.RecordStatus;
import com.victory.common.entity.DateEntity;
import com.victory.common.repository.hibernate.type.CollectionToStringUserType;
import com.victory.hrm.entity.HrmResource;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by ajkx
 * Date: 2017/5/24.
 * Time:9:00
 *
 * 异常记录表
 */
@TypeDef(
        name = "SetToStringDetail",
        typeClass = CollectionToStringUserType.class,
        parameters = {
                @org.hibernate.annotations.Parameter(name = "separator", value = ","),
                @org.hibernate.annotations.Parameter(name = "collectionType", value = "java.util.ArrayList"),
                @org.hibernate.annotations.Parameter(name = "elementType", value = "java.lang.Integer")
        }
)
@Entity
@Table(name = "EHR_Repairrecord")
public class RepairRecord extends DateEntity<Long> {

    @ManyToOne(targetEntity = HrmResource.class)
    @JoinColumn(name = "resource_id")
    private HrmResource resource;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date date;

    //修改那个上下班 1 - firstUp 2 - firstDown 3 - secondUp 4 - secondDown 5 - thirdUp 6 - thirdDown 7 - otFirstUp 8 - otFirstDown
    //多个按,号隔开 不使用枚举，因为一个申请可能有多个修改
    @Type(type = "SetToStringDetail")
    @Column(name = "times")
    private List<Integer> times;

    private String reason;

    private RecordStatus status;

    @ManyToOne(targetEntity = AttendanceResult.class,fetch = FetchType.LAZY)
    @JoinColumn(name = "result_id",referencedColumnName = "id")
    private AttendanceResult result;

    public HrmResource getResource() {
        return resource;
    }

    public void setResource(HrmResource resource) {
        this.resource = resource;
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

    public List<Integer> getTimes() {
        return times;
    }

    public void setTimes(List<Integer> times) {
        this.times = times;
    }

    public RecordStatus getStatus() {
        return status;
    }

    public void setStatus(RecordStatus status) {
        this.status = status;
    }

    public AttendanceResult getResult() {
        return result;
    }

    public void setResult(AttendanceResult result) {
        this.result = result;
    }
}
