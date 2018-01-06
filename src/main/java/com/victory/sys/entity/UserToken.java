package com.victory.sys.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.victory.common.entity.BaseEntity;
import com.victory.hrm.entity.HrmResource;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by ajkx
 * Date: 2017/8/29.
 * Time:14:23
 */
@Entity
@Table(name = "EHR_User_Token")
public class UserToken extends BaseEntity<Long>{

    //用户
    @JsonIgnore
    @OneToOne(targetEntity = User.class)
    private User user;
    //token字符串
    private String token;
    //过期时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date expireTime;
    //更新时间
    private Date updateTime;

    public User getUser() {
        return user;
    }
    @JsonIgnore
    @Override
    public Long getId() {
        return super.getId();
    }

    @Override
    public void setId(Long id) {
        super.setId(id);
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
