package com.victory.attendance.web.controller;


import com.victory.attendance.entity.AttendanceResult;
import com.victory.attendance.entity.Holiday;
import com.victory.attendance.enums.HolidayType;
import com.victory.attendance.service.AttendanceClassesService;
import com.victory.attendance.service.AttendanceResultService;
import com.victory.attendance.service.HolidayService;
import com.victory.attendance.service.LevelRecordService;
import com.victory.common.domain.result.ExceptionMsg;
import com.victory.common.domain.result.Response;
import com.victory.common.utils.DateUtils;
import com.victory.common.web.controller.BaseController;
import com.victory.common.web.vo.SearchVo;
import net.kaczmarzyk.spring.data.jpa.domain.DateBetween;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ajkx
 * Date: 2017/8/2.
 * Time:9:10
 */
@RestController
@RequestMapping("api/result")
public class AttendanceResultController extends BaseController<AttendanceResult, Long>{

    @Autowired
    private AttendanceClassesService classesService;

    public AttendanceResultController() {
        setResourceIdentity("attendance:result");
    }

    private AttendanceResultService getService() {
        return (AttendanceResultService) baseService;
    }

    @RequiresPermissions("attendance:result:view")
    @RequestMapping(method = RequestMethod.GET)
    public Response list(
            @And(value = {
                    @Spec(path = "status",spec = Equal.class),
                    @Spec(path = "date", params = {"beginDate", "endDate"}, spec = DateBetween.class, config = "yyyy-MM-dd")
            })
                    Specification<AttendanceResult> specification,
            @PageableDefault() Pageable pageable) {
        return Response.ok(pack(getService().findAll(specification, pageable), pageable));

    }

    @RequiresPermissions("attendance:collect:view")
    @RequestMapping(value = "/collect")
    public Response collectList(String dateStr, SearchVo searchVo, @PageableDefault Pageable pageable) {
        Date beginDate = null;
        if(dateStr.equals("")){
            beginDate = DateUtils.getMonthFirstDay();
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
            try{
                beginDate = sdf.parse(dateStr);
            } catch (ParseException e) {
                return Response.error(ExceptionMsg.ParamError);
            }
        }
        getService().getCollectData(beginDate, searchVo, pageable);
        return null;
    }
}
