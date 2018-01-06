package com.victory.sys.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.victory.common.entity.BaseEntity;
import com.victory.common.entity.DateEntity;
import com.victory.hrm.entity.HrmResource;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by ajkx on 2017/5/7.
 */
@Entity
@Table(name = "EHR_Role")
public class Role extends DateEntity<Long> {

    @Column
    private String name;

    @Column
    private String description;

    //是否启用
    @Column
    private Boolean available;

    //可以用来区别超级管理员 不可更新，默认为false  注意前端传的参数名是super,非isSuper
    @Column(name = "super", updatable = false)
    private Boolean isSuper = false;

    //角色的权限范围 预留字段
    @Column(name = "range_type")
    private Integer rangeType;

    @ManyToMany(targetEntity = Resource.class)
    @JoinTable(name = "EHR_role_resource",
            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "resource_id", referencedColumnName = "id"))
    private Set<Resource> resources = new HashSet<Resource>();

    @JsonBackReference
    @ManyToMany(targetEntity = User.class,mappedBy = "roles")
//    @JoinTable(name = "EHR_user_role",
//            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private Set<User> users = new HashSet<User>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
//    @JsonBackReference
    public Set<Resource> getResources() {
        return resources;
    }
//    @JsonBackReference
    public void setResources(Set<Resource> resources) {
        this.resources = resources;
    }
//    @JsonBackReference
    public Set<User> getUsers() {
        return users;
    }
//    @JsonBackReference
    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Boolean getSuper() {
        return isSuper;
    }

    public void setSuper(Boolean Super) {
        isSuper = false;    //固定为false
    }

    public Integer getRangeType() {
        return rangeType;
    }

    public void setRangeType(Integer rangeType) {
        this.rangeType = rangeType;
    }

}
