package com.victory.attendance.web.vo;

import com.victory.attendance.enums.UnitType;

import java.util.Set;

/**
 * Created by Administrator on 2017/12/29.
 */
public class SettingBaseInfo {

    private UnitType unitType;

    private Set<Long> ignoreResources;

    public UnitType getUnitType() {
        return unitType;
    }

    public void setUnitType(UnitType unitType) {
        this.unitType = unitType;
    }

    public Set<Long> getIgnoreResources() {
        return ignoreResources;
    }

    public void setIgnoreResources(Set<Long> ignoreResources) {
        this.ignoreResources = ignoreResources;
    }
}
