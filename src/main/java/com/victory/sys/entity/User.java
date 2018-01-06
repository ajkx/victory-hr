package com.victory.sys.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.victory.common.entity.BaseEntity;
import com.victory.hrm.entity.HrmStatus;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by ajkx
 * Date: 2017/9/5.
 * Time:15:31
 */

/**
 * 也是员工HrmResource表，拿其中部门的登录信息独立成类，方便逻辑控制
 */
@Entity
@Table(name = "hrmresource")
public class User extends BaseEntity<Long>{

    //姓名
    @Column(name = "lastname")
    private String name;

    //工号
    @Column(name = "workcode")
    private String workCode;

    //登录账号
    @Column(name = "loginid")
    private String account;

    //登录密码
    @JsonIgnore
    @Column(name = "password")
    private String password;

    @Column(name = "status")
    private HrmStatus status;

    //创建日期
    @Column(name = "createdate")
    private String createDate;

    //该用户的角色集合
    @ManyToMany(targetEntity = Role.class)
    @JoinTable(name = "EHR_user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<Role>();

    //判断是否为锁定状态 判断该数值为1时即为锁定，反之
    @Column(name = "tinyintfield5")
    private Integer locked;

    @Column(name = "pinyinlastname")
    private String shortName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public HrmStatus getStatus() {
        return status;
    }

    public void setStatus(HrmStatus status) {
        this.status = status;
    }

    public String getWorkCode() {
        return workCode;
    }

    public void setWorkCode(String workCode) {
        this.workCode = workCode;
    }

    public Integer getLocked() {
        return locked;
    }

    public void setLocked(Integer locked) {
        this.locked = locked;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", status=" + status +
                ", createDate='" + createDate + '\'' +
                '}';
    }
}
