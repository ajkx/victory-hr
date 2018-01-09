package com.victory.attendance.entity;

import com.victory.common.entity.DateEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 * 表示考勤计算结果最后生成日期
 */
@Entity
@Table(name="EHR_Daterecord")
public class DateRecord extends DateEntity<Integer>{

    @Column
    @Temporal(TemporalType.DATE)
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DateRecord that = (DateRecord) o;
        return Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), date);
    }
}
