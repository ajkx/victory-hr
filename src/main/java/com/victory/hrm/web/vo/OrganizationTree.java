package com.victory.hrm.web.vo;

import java.util.List;

/**
 * Created by Administrator on 2017/12/7.
 */
public class OrganizationTree {

    private long id;
    private String name;
    private String type;
    private List<OrganizationTree> children;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<OrganizationTree> getChildren() {
        return children;
    }

    public void setChildren(List<OrganizationTree> children) {
        this.children = children;
    }
}
