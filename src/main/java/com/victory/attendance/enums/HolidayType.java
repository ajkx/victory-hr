package com.victory.attendance.enums;

/**
 * Created by Administrator on 2018/1/2.
 */
public enum HolidayType {
    official("法定假日"), rest("调配休息日"), work("调配工作日");

    private final String name;

    private HolidayType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
