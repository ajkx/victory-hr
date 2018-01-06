package com.victory.attendance.web.controller;

import com.victory.attendance.entity.LevelRecord;
import com.victory.attendance.entity.OverTimeRecord;
import com.victory.attendance.entity.RepairRecord;
import com.victory.attendance.enums.RecordStatus;
import com.victory.attendance.service.LevelRecordService;
import com.victory.attendance.service.RepairRecordService;
import com.victory.common.domain.result.ExceptionMsg;
import com.victory.common.domain.result.Response;
import com.victory.common.utils.DateUtils;
import com.victory.common.web.controller.BaseController;
import com.victory.common.web.vo.SearchVo;
import com.victory.hrm.service.HrmResourceService;
import net.kaczmarzyk.spring.data.jpa.domain.DateBetween;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

/**
 * Created by ajkx
 * Date: 2017/8/2.
 * Time:9:10
 */
@RestController
@RequestMapping("api/repairRecord")
public class RepairRecordController extends BaseController<RepairRecord, Long> {

    @Autowired
    private HrmResourceService resourceService;

    public RepairRecordController() {
        setResourceIdentity("attendance:repair");
    }

    private RepairRecordService getService() {
        return (RepairRecordService) baseService;
    }

    @Override
    protected boolean checkEntity(RepairRecord entity, Response response) {

        // 更新合法性判断
        if (entity.getId() != null) {
            RepairRecord record = getService().findOne(entity.getId());
            if (record == null) {
                response.setExceptionMsg(ExceptionMsg.ParamError);
                return false;
            }
            if (record.getStatus() != RecordStatus.calculate) {
                response.setExceptionMsg(ExceptionMsg.RefuseEdit);
                return false;
            }
        }

        if(entity.getDate() == null || entity.getTimes() == null || entity.getTimes().size() == 0 || entity.getResource() == null) {
            response.setExceptionMsg(ExceptionMsg.ParamError);
            return false;
        }
        if (!resourceService.checkID(entity.getResource())) {
            response.setExceptionMsg(ExceptionMsg.resourceNotExist);
            return false;
        }

        // 升序排序
        Collections.sort(entity.getTimes());
        // 通过API新增的Record都是属于签卡类型
        entity.setStatus(RecordStatus.calculate);
        return true;
    }

    @Override
    protected boolean delCheck(Long aLong, Response response) {
        if (aLong != null) {
            RepairRecord record = getService().findOne(aLong);
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
    @RequiresPermissions("attendance:repair:view")
    @RequestMapping(value = {"", "list"})
    public Response list(
            @And(value = {
                    @Spec(path = "date", params = {"beginDate", "endDate"}, spec = DateBetween.class, config = "yyyy-MM-dd")
            })
                    Specification<LevelRecord> specification,
            @PageableDefault() Pageable pageable, @RequestBody(required = false) SearchVo searchVo) {
        return Response.ok(pack(getService().findAll(searchVo.getSpec(specification,resourceService), pageable), pageable));
    }
}
