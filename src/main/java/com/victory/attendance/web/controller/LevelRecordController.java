package com.victory.attendance.web.controller;

import com.victory.attendance.entity.AttendanceRecord;
import com.victory.attendance.entity.Holiday;
import com.victory.attendance.entity.LevelRecord;
import com.victory.attendance.enums.RecordStatus;
import com.victory.attendance.service.LevelRecordService;
import com.victory.common.domain.result.ExceptionMsg;
import com.victory.common.domain.result.Response;
import com.victory.common.utils.DateUtils;
import com.victory.common.web.controller.BaseController;
import com.victory.common.web.vo.SearchVo;
import com.victory.hrm.entity.HrmDepartment;
import com.victory.hrm.entity.HrmResource;
import com.victory.hrm.service.HrmResourceService;
import net.kaczmarzyk.spring.data.jpa.domain.Conjunction;
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

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

/**
 * Created by ajkx
 * Date: 2017/8/2.
 * Time:9:10
 */
@RestController
@RequestMapping("api/levelRecord")
public class LevelRecordController extends BaseController<LevelRecord, Long> {

    @Autowired
    private HrmResourceService resourceService;

    public LevelRecordController() {
        setResourceIdentity("attendance:level");
    }

    private LevelRecordService getService() {
        return (LevelRecordService) baseService;
    }

    @Override
    protected boolean checkEntity(LevelRecord entity, Response response) {
        // 更新合法性判断
        if (entity.getId() != null) {
            LevelRecord record = getService().findOne(entity.getId());
            // 判断是否为空
            if (record == null) {
                response.setExceptionMsg(ExceptionMsg.ParamError);
                return false;
            }
            // 判断数据状态是否已计算
            if (record.getStatus() != RecordStatus.calculate) {
                response.setExceptionMsg(ExceptionMsg.RefuseEdit);
                return false;
            }
        }

        if (entity.getBeginDate() == null || entity.getEndDate() == null || entity.getResource() == null || entity.getType() == null) {
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

        //todo 这里还可以添加请假时间重复交集的判断

        // 通过API新增的Record都是属于签卡类型
        entity.setStatus(RecordStatus.calculate);
        entity.setApplyTime(DateUtils.getInterval(entity.getBeginDate(), entity.getEndDate()) / DateUtils.MILLIS_PER_MINUTE);
        return true;
    }

    @Override
    protected boolean delCheck(Long aLong, Response response) {
        if (aLong != null) {
            LevelRecord record = getService().findOne(aLong);
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
     *
     * @param specification
     * @param pageable
     * @return
     */
    @RequiresPermissions("attendance:level:view")
    @RequestMapping(value = {"", "list"})
    public Response list(
            @And(value = {
                    @Spec(path = "beginDate", params = {"beginDate", "endDate"}, spec = DateBetween.class, config = "yyyy-MM-dd HH:mm:ss")
            })
                    Specification<LevelRecord> specification,
            @PageableDefault() Pageable pageable, @RequestBody(required = false) SearchVo searchVo) {
        return Response.ok(pack(getService().findAll(searchVo.getSpec(specification, resourceService), pageable), pageable));
    }

}
