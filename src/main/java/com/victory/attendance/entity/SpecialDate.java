package com.victory.attendance.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.victory.common.entity.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * 考勤组里的特殊日期表
 * Created by Administrator on 2017/12/1.
 */
@JsonIgnoreProperties({"id"})
@Entity
@Table(name = "EHR_Specialdate")
public class SpecialDate extends BaseEntity<Long>{

    @JsonBackReference
    @ManyToOne(targetEntity = AttendanceGroup.class)
    @JoinColumn(name = "group_id")
    private AttendanceGroup group;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @Column(name = "date")
    private Date date;

    @Column(name = "class_id")
    private Long classId;

    @Column(name = "description")
    private String description;

    public AttendanceGroup getGroup() {
        return group;
    }

    public void setGroup(AttendanceGroup group) {
        this.group = group;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        SpecialDate that = (SpecialDate) o;

        if (this.getId() == that.getId()) {
            return true;
        }else{
            if (group != null ? group.getId() != that.group.getId() : that.group != null) return false;
            if (date != null ? !date.equals(that.date) : that.date != null) return false;
            return true;
        }
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getId() != null ? getId().hashCode() : 0);
        result = 31 * result + (group != null ? group.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }
}
