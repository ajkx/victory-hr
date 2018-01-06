package com.victory.attendance.web.controller;

import com.victory.attendance.entity.AttendanceClasses;
import com.victory.attendance.service.AttendanceClassesService;
import com.victory.common.domain.result.ExceptionMsg;
import com.victory.common.domain.result.Response;
import com.victory.common.web.controller.BaseController;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by ajkx
 * Date: 2017/8/2.
 * Time:9:10
 */
@RestController
@RequestMapping("api/attendanceClasses")
public class AttendanceClassesController extends BaseController<AttendanceClasses,Long>{


    public AttendanceClassesController() {
        setResourceIdentity("attendance:classes");
    }

    private AttendanceClassesService getService() {
        return (AttendanceClassesService) baseService;
    }

    @Override
    protected boolean checkEntity(AttendanceClasses entity, Response response) {
        // 判断考勤组名称是否重复，可以优化查询的HQL
        if(entity.getId() == null && getService().countByName(entity.getName()) > 0){
            response.setExceptionMsg(ExceptionMsg.ClassesNameUsed);
            return false;
        }
        return true;
    }
    /**
     * example : page=0&size=10&sort=id,asc&sort=sex,desc
     * @param pageable
     * @return
     */
    @RequiresPermissions("attendance:classes:view")
    @RequestMapping(method = RequestMethod.GET)
    public Page<AttendanceClasses> list(
            @And(value = {
                @Spec(path = "name",spec = Like.class),
//                @Spec(path = "")
            })
                    Specification<AttendanceClasses> specification,
            @PageableDefault() Pageable pageable, Boolean isAll) {
        if (isAll != null && isAll) {
            List result = getService().findAll();
            return new PageImpl<AttendanceClasses>(result, null, result.size());
        }
        return getService().findAll(specification,pageable);
    }

}
