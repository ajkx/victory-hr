package com.victory.attendance.enums;

/**
 * Created by Administrator on 2017/12/28.
 */
public enum OverTimeType {

    normal("平时加班"),weekend("周末加班"),
    festival("节日加班");

    private final String name;

    private OverTimeType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
