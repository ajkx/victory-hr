package com.victory.attendance.web.controller;

import com.victory.attendance.entity.AttendanceSetting;
import com.victory.attendance.entity.LevelRecord;
import com.victory.attendance.entity.OverTimeRecord;
import com.victory.attendance.enums.RecordStatus;
import com.victory.attendance.service.AttendanceSettingService;
import com.victory.attendance.service.OverTimeService;
import com.victory.common.domain.result.ExceptionMsg;
import com.victory.common.domain.result.Response;
import com.victory.common.utils.DateUtils;
import com.victory.common.web.controller.BaseController;
import com.victory.common.web.vo.SearchVo;
import com.victory.hrm.service.HrmResourceService;
import net.kaczmarzyk.spring.data.jpa.domain.DateBetween;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ajkx
 * Date: 2017/8/2.
 * Time:9:10
 */
@RestController
@RequestMapping("api/overtimeRecord")
public class OverTimeRecordController extends BaseController<OverTimeRecord, Long> {

    @Autowired
    private HrmResourceService resourceService;

    @Autowired
    private AttendanceSettingService settingService;

    public OverTimeRecordController() {
        setResourceIdentity("attendance:overtime");
    }

    private OverTimeService getService() {
        return (OverTimeService) baseService;
    }

    @Override
    protected boolean checkEntity(OverTimeRecord entity, Response response) {

        // 更新合法性判断
        if (entity.getId() != null) {
            OverTimeRecord record = getService().findOne(entity.getId());
            if (record == null) {
                response.setExceptionMsg(ExceptionMsg.ParamError);
                return false;
            }
            if (record.getStatus() != RecordStatus.calculate) {
                response.setExceptionMsg(ExceptionMsg.RefuseEdit);
                return false;
            }
        }

        if(entity.getBeginDate() == null || entity.getEndDate() == null || entity.getResource() == null || entity.getType() == null) {
            response.setExceptionMsg(ExceptionMsg.ParamError);
            return false;
        }
        if (entity.getBeginDate().after(entity.getEndDate())) {
            response.setExceptionMsg(ExceptionMsg.timeSequenceError);
            return false;
        }
        if (!resourceService.checkID(entity.getResource())) {
            response.setExceptionMsg(ExceptionMsg.resourceNotExist);
            return false;
        }

        AttendanceSetting setting = settingService.getTopRecord();
        // 倍数和调休有效时间计算
        int month = 0;
        switch (entity.getType()) {
            case normal:
                entity.setMultiple(1f);
                month = setting.getNormalPeriod();
                break;
            case weekend:
                entity.setMultiple(1f);
                month = setting.getWeekendPeriod();
                break;
            case festival:
                if (entity.getMultiple() == null && entity.getMultiple() > 0) {
                    entity.setMultiple(1f);
                }
                month = setting.getFestivalPeriod();
                break;
        }
        // 设置有效范围
        entity.setMinScope(entity.getBeginDate());
        entity.setMaxScope(DateUtils.plusMonths(entity.getBeginDate(), month));

        //todo 这里还可以添加加班时间重复交集的判断
        entity.setApplyTime(DateUtils.getInterval(entity.getBeginDate(), entity.getEndDate()) / DateUtils.MILLIS_PER_MINUTE);
        entity.setStatus(RecordStatus.calculate);
        return true;
    }

    @Override
    protected boolean delCheck(Long aLong, Response response) {
        if (aLong != null) {
            OverTimeRecord record = getService().findOne(aLong);
            if (record == null) {
                response.setExceptionMsg(ExceptionMsg.ParamError);
                return false;
            }
            if (record.getStatus() != RecordStatus.calculate) {
                response.setExceptionMsg(ExceptionMsg.RefuseEdit);
                return false;
            }
        }
        return true;
    }

    /**
     * 传非法格式的日期会抛出错误，没有错误处理
     * 这里存在当url 为 "" 而 method 为post 则会变为create
     * @param specification
     * @param pageable
     * @return
     */
    @RequiresPermissions("attendance:overtime:view")
    @RequestMapping(value = {"", "list"})
    public Response list(
            @And(value = {
                    @Spec(path = "beginDate", params = {"beginDate", "endDate"}, spec = DateBetween.class, config = "yyyy-MM-dd HH:mm:ss")
            })
                    Specification<OverTimeRecord> specification,
            @PageableDefault() Pageable pageable, @RequestBody(required = false) SearchVo searchVo) {
        return Response.ok(pack(getService().findAll(searchVo.getSpec(specification,resourceService), pageable), pageable));
    }

}
