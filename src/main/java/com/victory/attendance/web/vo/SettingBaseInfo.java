package com.victory.attendance.web.vo;

import com.victory.attendance.enums.UnitType;

import java.util.Set;

/**
 * Created by Administrator on 2017/12/29.
 */
public class SettingBaseInfo {


    private Set<Long> ignoreResources;

    public Set<Long> getIgnoreResources() {
        return ignoreResources;
    }

    public void setIgnoreResources(Set<Long> ignoreResources) {
        this.ignoreResources = ignoreResources;
    }
}
