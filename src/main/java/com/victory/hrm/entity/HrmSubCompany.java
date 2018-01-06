package com.victory.hrm.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.victory.common.entity.BaseEntity;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

/**
 * 分部表
 *
 * @author ajkx_Du
 * @createDate 2016-10-19 9:49
 */
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "hrmsubcompany")
public class HrmSubCompany extends BaseEntity<Long> {


    @Column(name = "subcompanyname")
    private String name;

    @JsonIgnore
    @ManyToOne(targetEntity = HrmSubCompany.class,fetch = FetchType.LAZY)
    @JoinColumn(name = "supsubcomid")
    @NotFound(action= NotFoundAction.IGNORE)
    private HrmSubCompany parent;

    @Column(name = "canceled")
    private Boolean cancel;

    public HrmSubCompany() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HrmSubCompany getParent() {
        return parent;
    }

    public void setParent(HrmSubCompany parent) {
        this.parent = parent;
    }

    public Boolean getCancel() {
        return cancel;
    }

    public void setCancel(Boolean cancel) {
        this.cancel = cancel;
    }
}



