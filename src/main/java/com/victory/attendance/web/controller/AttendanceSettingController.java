package com.victory.attendance.web.controller;

import com.victory.attendance.entity.AttendanceSetting;
import com.victory.attendance.service.AttendanceSettingService;
import com.victory.attendance.web.vo.SettingOvertimeInfo;
import com.victory.attendance.web.vo.SettingWeekInfo;
import com.victory.common.domain.result.ExceptionMsg;
import com.victory.common.domain.result.Response;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.victory.attendance.web.vo.SettingBaseInfo;
/**
 * Created by ajkx
 * Date: 2017/8/2.
 * Time:9:10
 */
@RestController
@RequestMapping("api/attendanceSetting")
public class AttendanceSettingController {

    @Autowired
    private AttendanceSettingService settingService;

    @RequiresPermissions("Attendance:setting:view")
    @RequestMapping(method = RequestMethod.GET)
    public Response getTopRecord() {
        return Response.ok(settingService.packEntity(settingService.getTopRecord()));
    }

    /**
     * 更新基础信息
     * @return
     */
    @RequiresPermissions("attendance:setting:update")
    @RequestMapping(value = "/base",method = RequestMethod.POST)
    public Response updateBaseInfo(@RequestBody SettingBaseInfo baseInfo) {
        AttendanceSetting setting = settingService.getTopRecord();
        setting.setIgnoreResources(baseInfo.getIgnoreResources());
        settingService.update(setting);
        return Response.ok();
    }

    @RequiresPermissions("attendance:setting:update")
    @RequestMapping(value = "/week", method = RequestMethod.POST)
    public Response updateWeekInfo(@RequestBody SettingWeekInfo weekInfo) {
        if(weekInfo.getOddEvenWeek() == null) return Response.error(ExceptionMsg.ParamError);
        AttendanceSetting setting = settingService.getTopRecord();
        setting.setOddEvenWeek(weekInfo.getOddEvenWeek());
        setting.setOddResource(weekInfo.getOddResource());
        setting.setEvenResource(weekInfo.getEvenResource());
        settingService.update(setting);
        return Response.ok();
    }

    @RequiresPermissions("attendance:setting:update")
    @RequestMapping(value = "/ot", method = RequestMethod.POST)
    public Response updateOvertimeInfo(@RequestBody SettingOvertimeInfo overtimeInfo) {
        if(overtimeInfo.getOvertimeUnitType() == null || (overtimeInfo.getAcrossDay() && overtimeInfo.getAcrossOffset() == null)) return Response.error(ExceptionMsg.ParamError);
        AttendanceSetting setting = settingService.getTopRecord();
        setting.setOvertimeUnitType(overtimeInfo.getOvertimeUnitType());
        setting.setAcrossDay(overtimeInfo.getAcrossDay());
        if(overtimeInfo.getAcrossDay()) setting.setAcrossOffset(overtimeInfo.getAcrossOffset());
        setting.setBeginMinute(overtimeInfo.getBeginMinute());
        setting.setEndMinute(overtimeInfo.getEndMinute());
        setting.setCalculateType(overtimeInfo.getCalculateType());
        setting.setNormalPeriod(overtimeInfo.getNormalPeriod());
        setting.setWeekendPeriod(overtimeInfo.getWeekendPeriod());
        setting.setFestivalPeriod(overtimeInfo.getFestivalPeriod());

        settingService.update(setting);
        return Response.ok();
    }

}
