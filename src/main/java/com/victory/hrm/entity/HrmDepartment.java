package com.victory.hrm.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.victory.common.entity.BaseEntity;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

/**
 * 部门表
 *
 * @author ajkx_Du
 * @createDate 2016-10-19 10:20
 */
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "hrmdepartment")
public class HrmDepartment extends BaseEntity<Long> {


    @Column(name = "departmentname",nullable = false,length = 200)
    private String name;

    @JsonIgnore
    @ManyToOne(targetEntity = HrmSubCompany.class,fetch = FetchType.LAZY)
    @JoinColumn(name = "subcompanyid1",nullable = false)
    private HrmSubCompany subCompany;

    @JsonIgnore
    @ManyToOne(targetEntity = HrmDepartment.class,fetch = FetchType.LAZY)
    @JoinColumn(name = "supdepid")
    @NotFound(action= NotFoundAction.IGNORE)
    private HrmDepartment parent;

    @Column(name = "canceled")
    private Boolean cancel;

    public HrmDepartment() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HrmSubCompany getSubCompany() {
        return subCompany;
    }

    public void setSubCompany(HrmSubCompany subCompany) {
        this.subCompany = subCompany;
    }

    public HrmDepartment getParent() {
        return parent;
    }

    public void setParent(HrmDepartment parent) {
        this.parent = parent;
    }

    public Boolean getCancel() {
        return cancel;
    }

    public void setCancel(Boolean cancel) {
        this.cancel = cancel;
    }

}
