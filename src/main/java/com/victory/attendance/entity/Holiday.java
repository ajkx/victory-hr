package com.victory.attendance.entity;

import com.victory.attendance.enums.HolidayType;
import com.victory.common.entity.DateEntity;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * 法定假日，调配出勤日，调配休息日
 * Created by Administrator on 2018/1/2.
 */
@Entity
@Table(name = "EHR_Holiday")
public class Holiday extends DateEntity<Long>{

    // 名称 描述
    @Column
    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column
    private HolidayType type;

    // 当为工作日时，是否统一使用班次
    @Column(name = "useclass")
    private Boolean useClass;

    // 统一使用的班次
    @Column(name = "classes")
    private Long classId;

    // 休息日是否启用自动调休，会自动使用年假或平时加班或周末加班剩余的时数自动调休
    // 如果不开启，即当天的出勤算作请假，如开启，会判断剩余的可调休时数判断调休或年假或请假
    @Column(name = "userest")
    private Boolean useRest;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public HolidayType getType() {
        return type;
    }

    public void setType(HolidayType type) {
        this.type = type;
    }

    public Boolean getUseClass() {
        return useClass;
    }

    public void setUseClass(Boolean useClass) {
        this.useClass = useClass;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClasses(Long classId) {
        this.classId = classId;
    }

    public Boolean getUseRest() {
        return useRest;
    }

    public void setUseRest(Boolean useRest) {
        this.useRest = useRest;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        if (this.date.equals(((Holiday)o).getDate())) return true;
        DateEntity<?> that = (DateEntity<?>) o;

        return getId() == that.getId();
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getId() != null ? getId().hashCode() : 0);
        result = 31 * result + (getDate() != null ? getDate().hashCode() : 0);
        return result;
    }
}
