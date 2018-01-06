package com.victory.attendance.enums;

/**
 * Created by Administrator on 2017/12/28.
 */
public enum LevelType {

    personal("事假"),rest("调休"),
    injury("工伤"),annual("年假"),
    funeral("丧假"),married("婚假"),
    delivery("产假"),cancel("销假");

    private final String name;

    private LevelType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
