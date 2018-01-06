package com.victory.attendance.entity;

import com.victory.attendance.enums.CalculateType;
import com.victory.attendance.enums.UnitType;
import com.victory.common.entity.DateEntity;
import com.victory.common.repository.hibernate.type.CollectionToStringUserType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Time;
import java.util.Set;

/**
 * Created by Administrator on 2017/12/28.
 */
@TypeDef(
        name = "SetToStringResource",
        typeClass = CollectionToStringUserType.class,
        parameters = {
                @Parameter(name = "separator", value = ","),
                @Parameter(name = "collectionType", value = "java.util.HashSet"),
                @Parameter(name = "elementType", value = "java.lang.Long")
        }
)
@Entity
@Table(name = "EHR_attendancesetting")
public class AttendanceSetting extends DateEntity<Long> {

    @Column(name = "unit")
    private UnitType unitType;

    @Type(type = "SetToStringResource")
    @Column(name = "ignore_resource")
    private Set<Long> ignoreResources;

    // ============大小周规则============
    // 是否开启大小周
    @Column(name = "odd_even_week")
    private Boolean oddEvenWeek = false;

    // 单数周人员
    @Type(type = "SetToStringResource")
    @Column(name = "odd_week_resource")
    private Set<Long> oddResource;

    // 偶数周人员
    @Type(type = "SetToStringResource")
    @Column(name = "even_week_resource")
    private Set<Long> evenResource;
    // ============大小周规则结束==========

    // ============加班规则============
    @Column(name = "calculate_type")
    private CalculateType calculateType;

    // 加班开始打卡时间
    @Column(name = "begin_minute")
    private Integer beginMinute;

    // 加班结束打卡时间
    @Column(name = "end_minute")
    private Integer endMinute;

    // 是否开启加班开始时间小于指定时间则算做昨天的考勤明细
    @Column(name = "across_day")
    private Boolean acrossDay;

    // 指定时间 分钟
    @Column(name = "across_offset")
    private Time acrossOffset;

    // 平时加班有效期 以月为单位
    @Column(name = "normal_period")
    private Integer normalPeriod;

    // 周末加班有效期 以月为单位
    @Column(name = "weekend_period")
    private Integer weekendPeriod;

    // 节日加班有效期 以月为单位
    @Column(name = "festival_period")
    private Integer festivalPeriod;

    public UnitType getUnitType() {
        return unitType;
    }

    public void setUnitType(UnitType unitType) {
        this.unitType = unitType;
    }

    public Set<Long> getIgnoreResources() {
        return ignoreResources;
    }

    public void setIgnoreResources(Set<Long> ignoreResources) {
        this.ignoreResources = ignoreResources;
    }

    public Boolean getOddEvenWeek() {
        return oddEvenWeek;
    }

    public void setOddEvenWeek(Boolean oddEvenWeek) {
        this.oddEvenWeek = oddEvenWeek;
    }

    public Set<Long> getOddResource() {
        return oddResource;
    }

    public void setOddResource(Set<Long> oddResource) {
        this.oddResource = oddResource;
    }

    public Set<Long> getEvenResource() {
        return evenResource;
    }

    public void setEvenResource(Set<Long> evenResource) {
        this.evenResource = evenResource;
    }

    public CalculateType getCalculateType() {
        return calculateType;
    }

    public void setCalculateType(CalculateType calculateType) {
        this.calculateType = calculateType;
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

    public Boolean getAcrossDay() {
        return acrossDay;
    }

    public void setAcrossDay(Boolean acrossDay) {
        this.acrossDay = acrossDay;
    }

    public Time getAcrossOffset() {
        return acrossOffset;
    }

    public void setAcrossOffset(Time acrossOffset) {
        this.acrossOffset = acrossOffset;
    }

    public Integer getNormalPeriod() {
        return normalPeriod;
    }

    public void setNormalPeriod(Integer normalPeriod) {
        this.normalPeriod = normalPeriod;
    }

    public Integer getWeekendPeriod() {
        return weekendPeriod;
    }

    public void setWeekendPeriod(Integer weekendPeriod) {
        this.weekendPeriod = weekendPeriod;
    }

    public Integer getFestivalPeriod() {
        return festivalPeriod;
    }

    public void setFestivalPeriod(Integer festivalPeriod) {
        this.festivalPeriod = festivalPeriod;
    }
}
