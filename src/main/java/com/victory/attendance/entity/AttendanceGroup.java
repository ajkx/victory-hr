package com.victory.attendance.entity;

import com.victory.attendance.enums.GroupType;
import com.victory.common.entity.DateEntity;
import com.victory.common.repository.hibernate.type.CollectionToStringUserType;
import com.victory.hrm.entity.HrmResource;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2017/12/1.
 */

@TypeDefs({
        @TypeDef(
                name = "SetToStringClasses",
                typeClass = CollectionToStringUserType.class,
                parameters = {
                        @Parameter(name = "separator", value = ","),
                        @Parameter(name = "collectionType", value = "java.util.ArrayList"),
                        @Parameter(name = "elementType", value = "java.lang.Long")
                }
        ),
        @TypeDef(
                name = "SetToStringDate",
                typeClass = CollectionToStringUserType.class,
                parameters = {
                        @Parameter(name = "separator", value = ","),
                        @Parameter(name = "collectionType", value = "java.util.HashSet"),
                        @Parameter(name = "elementType", value = "java.lang.String")
                }
        )
})
@Entity
@Table(name = "EHR_Attendancegroup")
public class AttendanceGroup extends DateEntity<Long>{

    @Column(name = "group_name")
    private String name;

    @Column(name = "group_type")
    private GroupType type;

    /**
     * 节假日自动排休
     */
    @Column(name = "holiday_rest")
    private Boolean holidayRest;

    /**
     * 关联的班次列表
     */
    @ManyToMany(targetEntity = AttendanceClasses.class)
    @JoinTable(name = "EHR_group_classes",
            joinColumns = @JoinColumn(name = "group_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "class_id", referencedColumnName = "id"))
    private Set<AttendanceClasses> classes;

    @Column(name = "default_class_id")
    private Long defaultClassId;
    /**
     * 周班次
     */
    @Type(type = "SetToStringClasses")
    @Column(name = "work_day_list")
    private List<Long> workDayList;

    /**
     * 不用打卡的日期 使用yyyy-MM-dd的日期存储
     */
    @Type(type = "SetToStringDate")
    @Column(name = "special_off_duty")
    private Set<String> specialOffDate;

    /**
     * 按指定班次打卡的特殊日期
     */
    @OneToMany(targetEntity = SpecialDate.class, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "group")
    private Set<SpecialDate> specialOnDate;

    /**
     * 使用JoinTable在One的一端更新关联关系时，hibernate会自动删除以前的关联，再重新添加（但添加的resource有可能报错（不存在ID或关联关系重复））
     * 所以要在update或save时，循环持久化该resource，手工setGroup
     *
     * 1.如果使用mappdBy 会导致group之前的resource不会被删除(弃)
     * 2.如果不使用级联，会导致resource不会删除以前的数据
     * 3.因为使用了级联更新，新的resource因为里面的group为NULL，所以会delete了所有的关联关系，但如果某个resource与group之前也有关联，而Hibernate会
     *   当这种情况不会进行新的插入，所以这时会缺失数据
     */
    @OneToMany(targetEntity = HrmResource.class)
    @JoinTable(name = "EHR_group_resource",
            joinColumns = @JoinColumn(name = "group_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "resource_id", referencedColumnName = "id", unique = true))
    private Set<HrmResource> resources = new HashSet<>();

    @Column(name = "description")
    private String description;

    public Set<HrmResource> getResources() {
        return resources;
    }

    public void setResources(Set<HrmResource> resources) {
        this.resources = resources;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GroupType getType() {
        return type;
    }

    public void setType(GroupType type) {
        this.type = type;
    }

    public Boolean getHolidayRest() {
        return holidayRest;
    }

    public void setHolidayRest(Boolean holidayRest) {
        this.holidayRest = holidayRest;
    }

    public Set<AttendanceClasses> getClasses() {
        return classes;
    }

    public void setClasses(Set<AttendanceClasses> classes) {
        this.classes = classes;
    }

    public List<Long> getWorkDayList() {
        return workDayList;
    }

    public void setWorkDayList(List<Long> workDayList) {
        this.workDayList = workDayList;
    }

    public Set<String> getSpecialOffDate() {
        return specialOffDate;
    }

    public void setSpecialOffDate(Set<String> specialOffDate) {
        this.specialOffDate = specialOffDate;
    }

    public Set<SpecialDate> getSpecialOnDate() {
        return specialOnDate;
    }

    public void setSpecialOnDate(Set<SpecialDate> specialOnDate) {
        this.specialOnDate = specialOnDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getDefaultClassId() {
        return defaultClassId;
    }

    public void setDefaultClassId(Long defaultClassId) {
        this.defaultClassId = defaultClassId;
    }
}
