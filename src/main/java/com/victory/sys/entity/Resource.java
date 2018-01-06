package com.victory.sys.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.victory.common.entity.BaseEntity;
import com.victory.common.entity.DateEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by ajkx on 2017/5/7.
 */
@Entity
@Table(name = "EHR_Resource")
public class Resource extends DateEntity<Long> {

    public static enum ResourceType {
        module("模块"), group("组"), item("权限");
        private final String info;

        private ResourceType(String info) {
            this.info = info;
        }

        public String getInfo() {
            return info;
        }
    }

    @Column
    private String name;

    @Column
    private String description;

    //所在组别
    @ManyToOne(targetEntity = Resource.class)
    @JoinColumn(name = "group_id",referencedColumnName = "id")
    private Resource parent;

    @Column
    @Enumerated(EnumType.STRING)
    private ResourceType type = ResourceType.group;

    @Column
    private String permission;

    @Column
    private String url;

    @JsonBackReference
    @ManyToMany(targetEntity = Role.class,mappedBy = "resources")
//    @JoinTable(name = "EHR_role_resource",
//            joinColumns = @JoinColumn(name = "resource_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<Role>();


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

    public ResourceType getType() {
        return type;
    }

    public void setType(ResourceType type) {
        this.type = type;
    }

    public Resource getParent() {
        return parent;
    }

    public void setParent(Resource parent) {
        this.parent = parent;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
