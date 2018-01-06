package com.victory.hrm.web.vo;

import com.victory.hrm.entity.HrmResource;

/**
 * Created by Administrator on 2017/12/27.
 */
public class HrmResourceVo {

    private long id;

    private String name;

    private String workCode;

    private String departmentName;

    public HrmResourceVo() {

    }
    public HrmResourceVo(HrmResource resource) {
        this.id = resource.getId();
        this.name = resource.getName();
        this.workCode = resource.getWorkCode();
        this.departmentName = resource.getDepartment().getName();
    }
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

    public String getWorkCode() {
        return workCode;
    }

    public void setWorkCode(String workCode) {
        this.workCode = workCode;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
