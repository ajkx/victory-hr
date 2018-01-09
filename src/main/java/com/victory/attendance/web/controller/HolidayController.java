package com.victory.attendance.web.controller;


import com.victory.attendance.entity.AttendanceRecord;
import com.victory.attendance.entity.Holiday;
import com.victory.attendance.enums.HolidayType;
import com.victory.attendance.enums.RecordType;
import com.victory.attendance.service.AttendanceClassesService;
import com.victory.attendance.service.HolidayService;
import com.victory.common.domain.result.ExceptionMsg;
import com.victory.common.domain.result.Response;
import com.victory.common.utils.NullUtils;
import com.victory.common.web.controller.BaseController;
import net.kaczmarzyk.spring.data.jpa.domain.DateBetween;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created by ajkx
 * Date: 2017/8/2.
 * Time:9:10
 */
@RestController
@RequestMapping("api/holiday")
public class HolidayController extends BaseController<Holiday,Long> {

    @Autowired
    private AttendanceClassesService classesService;

    public HolidayController() {
        setResourceIdentity("attendance:holiday");
    }

    private HolidayService getService() {
        return (HolidayService) baseService;
    }

    @Override
    protected boolean checkEntity(Holiday entity, Response response) {

        if (entity.getId() != null) {
            Holiday holiday = getService().findOne(entity.getId());
            if (holiday.getDate().before(new Date())) {
                response.setExceptionMsg(ExceptionMsg.RefuseEdit);
                return false;
            }
        }
        // 判断classes是否存在
        if (entity.getType() == HolidayType.work) {
            if (NullUtils.check(entity.getClassId())) {
                if(classesService.findOne(entity.getClassId()) == null){
                    response.setExceptionMsg(ExceptionMsg.ParamError);
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    protected boolean delCheck(Long aLong, Response response) {
        if (aLong != null) {
            Holiday holiday = getService().findOne(aLong);
            if (holiday.getDate().before(new Date())) {
                response.setExceptionMsg(ExceptionMsg.RefuseEdit);
                return false;
            }
        }
        return true;
    }

    @RequiresPermissions("attendance:holiday:view")
    @RequestMapping(method = RequestMethod.GET)
    public Response list(
            @And(value = {
                    @Spec(path = "name",spec = Like.class),
                    @Spec(path = "date", params = {"beginDate", "endDate"}, spec = DateBetween.class, config = "yyyy-MM-dd")
            })
                    Specification<Holiday> specification,
            @PageableDefault() Pageable pageable) {
        return Response.ok(pack(getService().findAll(specification, pageable), pageable));

    }

}
