package com.victory.attendance.enums;

/**
 * Created by Administrator on 2017/12/28.
 */
public enum RecordStatus {

    calculate("待计算"),abnormal("异常"),
    finish("已计算");

    private final String name;

    private RecordStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
