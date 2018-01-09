package com.victory.common.utils;

import java.util.Collection;

public class NullUtils {

    public static boolean check(String str) {
        if(str == null) return false;
        return str.equals("");
    }
    public static boolean check(Boolean flag) {
        if(flag == null) return false;
        return flag;
    }

    public static boolean check(Long l) {
        if(l == null) return false;
        return l > 0;
    }
    public static boolean check(Collection collection) {
        if(collection == null) return false;
        return collection.size() == 0;
    }

    public static boolean check(Collection collection, Object o) {
        if(collection == null || collection.size() == 0 || o == null) return false;
        return collection.contains(o);
    }
}
