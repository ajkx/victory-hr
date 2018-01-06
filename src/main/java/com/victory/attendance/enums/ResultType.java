package com.victory.attendance.enums;

public enum ResultType {
    normal("正常"),late("迟到"),
    early("早退"),miss("旷工"),
    lack("缺卡"), rest("请假");

    private final String name;

    private ResultType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
