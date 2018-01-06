package com.victory.attendance.web.vo;

import java.util.Set;

/**
 * Created by Administrator on 2017/12/29.
 */
public class SettingWeekInfo {

    private Boolean oddEvenWeek;

    private Set<Long> oddResource;

    private Set<Long> evenResource;

    public Boolean getOddEvenWeek() {
        return oddEvenWeek;
    }

    public void setOddEvenWeek(Boolean oddEvenWeek) {
        this.oddEvenWeek = oddEvenWeek;
    }

    public Set<Long> getOddResource() {
        return oddResource;
    }

    public void setOddResource(Set<Long> oddResource) {
        this.oddResource = oddResource;
    }

    public Set<Long> getEvenResource() {
        return evenResource;
    }

    public void setEvenResource(Set<Long> evenResource) {
        this.evenResource = evenResource;
    }
}
