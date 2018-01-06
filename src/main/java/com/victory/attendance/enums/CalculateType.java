package com.victory.attendance.enums;

/**
 * 加班计算规则 是按登记的时间算加班时数还是打卡时间
 */
public enum CalculateType {

    regist("登记"),punch("打卡");

    private final String name;

    private CalculateType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
