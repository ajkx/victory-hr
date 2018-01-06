package com.victory.attendance.enums;


/**
 * Created by ajkx
 * Date: 2017/5/12.
 * Time:15:50
 */
public enum RecordType {
    machine("考勤机"),manual("签卡");

    private final String name;

    private RecordType(String name) {
        this.name = name;
    }

//    @JsonCreator
//    public static RecordType getType(int id) {
//        for (RecordType type : values()) {
//            if (type.getId() == id) {
//                return type;
//            }
//        }
//        return null;
//    }


    public String getName() {
        return name;
    }


}
