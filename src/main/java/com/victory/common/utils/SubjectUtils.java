package com.victory.common.utils;

import com.victory.hrm.entity.HrmResource;
import org.apache.shiro.SecurityUtils;

/**
 * Created by ajkx
 * Date: 2017/9/5.
 * Time:15:23
 */
public class SubjectUtils {

    public static HrmResource currentUser() {
        return (HrmResource) SecurityUtils.getSubject().getPrincipal();
    }
}
