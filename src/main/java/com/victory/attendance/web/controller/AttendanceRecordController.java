package com.victory.attendance.web.controller;

import com.victory.attendance.entity.AttendanceRecord;
import com.victory.attendance.entity.Card;
import com.victory.attendance.enums.RecordType;
import com.victory.attendance.service.AttendanceRecordService;
import com.victory.attendance.service.CardService;
import com.victory.common.domain.result.ExceptionMsg;
import com.victory.common.domain.result.Response;
import com.victory.common.web.controller.BaseController;
import com.victory.common.web.vo.SearchVo;
import com.victory.hrm.entity.HrmDepartment;
import com.victory.hrm.entity.HrmResource;
import com.victory.hrm.service.HrmResourceService;
import net.kaczmarzyk.spring.data.jpa.domain.*;
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
import java.util.*;

/**
 * Created by ajkx
 * Date: 2017/8/2.
 * Time:9:10
 */
@RestController
@RequestMapping("api/attendanceRecord")
public class AttendanceRecordController extends BaseController<AttendanceRecord, Long> {

    @Autowired
    private HrmResourceService resourceService;

    @Autowired
    private CardService cardService;

    public AttendanceRecordController() {
        setResourceIdentity("attendance:record");
    }

    private AttendanceRecordService getService() {
        return (AttendanceRecordService) baseService;
    }

    @Override
    protected boolean checkEntity(AttendanceRecord entity, Response response) {

        // 更新的合法性判断
        if (entity.getId() != null) {
            AttendanceRecord record = getService().findOne(entity.getId());
            if (record.getType() != RecordType.manual || record.getDate().before(new Date())) {
                response.setExceptionMsg(ExceptionMsg.RefuseEdit);
                return false;
            }
        }

        if(entity.getDate() == null || entity.getResource() == null) {
            response.setExceptionMsg(ExceptionMsg.ParamError);
            return false;
        }
        if (!resourceService.checkID(entity.getResource())) {
            response.setExceptionMsg(ExceptionMsg.resourceNotExist);
            return false;
        }

        if (!getService().checkUnique(entity.getResource(), entity.getDate())) {
            response.setExceptionMsg(ExceptionMsg.recordRepeat);
            return false;
        }

        // 通过API新增的Record都是属于签卡类型
        entity.setType(RecordType.manual);
        return true;
    }

    @Override
    protected boolean delCheck(Long id, Response response) {
        AttendanceRecord record = getService().findOne(id);
        if (record != null && record.getType() == RecordType.manual) {
            response.setExceptionMsg(ExceptionMsg.ParamError);
            return false;
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
    @RequiresPermissions("attendance:record:view")
    @RequestMapping(value = {"", "list"})
    public Response list(
            @And(value = {
                    @Spec(path = "type", spec = Equal.class),
                    @Spec(path = "date", params = {"beginDate", "endDate"}, spec = DateBetween.class, config = "yyyy-MM-dd HH:mm:ss")
            })
                    Specification<AttendanceRecord> specification,
            @PageableDefault() Pageable pageable, @RequestBody(required = false) SearchVo searchVo) {
        // 搜索条件初始化
        List<Long> resources = searchVo == null ? null : searchVo.getResources();
        List<HrmDepartment> deps = searchVo == null ? null : searchVo.getDep(searchVo.getDeps());
        boolean isAll = searchVo == null ? false : searchVo.isAll();

        // 再添加搜索条件
        ArrayList list = new ArrayList();
        list.add(specification);
        Set<HrmResource> resourceSet = new HashSet<>();
        List<String> cardList = new ArrayList<>();
        if (resources != null && resources.size() > 0) {
            resourceSet.addAll(resourceService.findByIds(resources));
        }
        if (deps != null && deps.size() > 0) {
            resourceSet.addAll(resourceService.findByDepartmentIn(deps, isAll));
        }
        if(resourceSet != null && resourceSet.size() > 0) {
            List<Card> cards = cardService.findByResourceIn(resourceSet);
            for (Card card : cards) {
                cardList.add(card.getCardNo());
            }
        }
        // 添加cardList
        if(cardList.size() > 0){
            Specification resourceSpec = new Specification<AttendanceRecord>() {
                @Override
                public Predicate toPredicate(Root<AttendanceRecord> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    return root.get("card").in(cardList);
                }
            };
            list.add(resourceSpec);
        }
        Conjunction conjunction = new Conjunction(list);
        return Response.ok(pack(getService().findAll(conjunction, pageable), pageable));
    }

}
