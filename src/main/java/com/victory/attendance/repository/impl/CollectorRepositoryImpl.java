package com.victory.attendance.repository.impl;

import com.victory.attendance.repository.CollectorRepository;
import com.victory.attendance.web.vo.PageInfo;
import com.victory.attendance.web.vo.ResultCollect;
import com.victory.attendance.web.vo.ResultCollectClass;
import com.victory.common.web.vo.SearchVo;
import com.victory.hrm.entity.HrmDepartment;
import com.victory.hrm.entity.HrmResource;
import javassist.convert.Transformer;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CollectorRepositoryImpl implements CollectorRepository {

//    public String backSql = "select a.resource, sum(a.shouldWorkDay) as shouldWorkDay, sum(a.actualWorkDay) as actualWorkDay, sum(a.normalOvertime) as normalOvertime, sum(a.weekendOvertime) as weekendOvertime, sum(a.festivalOvertime) as festivalOvertime, " +
//            " sum(b.lateTime) as lateTime, sum(b.lateCount) as lateCount, sum(b.earlyTime) as earlyTime, sum(b.earlyCount) as earlyCount, sum(b.absentTime) as absentTime, sum(b.absentCount) as absentCount, " +
//            " sum(b.personalLevel) as personalLevel, sum(b.restLevel) as restLevel, sum(b.injuryLevel) as injuryLevel, sum(b.deliveryLevel) as deliveryLevel, sum(b.maritalLevel) as maritalLevel, sum(b.funeralLevel) as funeralLevel, " +
//            " sum(b.annualLevel) as annualLevel, sum(b.cancelLevel) as cancelLevel, sum(b.shouldWorkTime) as shouldWorkTime, sum(b.actualWorkTime) as actualWorkTime ";

    public String backSql = "select res.lastname as resourceName, res.workCode as workCode, res.departmentId as departmentId, dep.departmentname as departmentName, d.* from " +
            "(select a.resource_id as resourceId, sum(a.should_Work_Day) as shouldWorkDay, sum(a.actual_Work_Day) as actualWorkDay, sum(a.normal_Overtime) as normalOvertime, sum(a.weekend_Overtime) as weekendOvertime, sum(a.festival_Overtime) as festivalOvertime, " +
            " sum(b.late_Time) as lateTime, sum(b.late_Count) as lateCount, sum(b.early_Time) as earlyTime, sum(b.early_Count) as earlyCount, sum(b.absent_Time) as absentTime, sum(b.absent_Count) as absentCount, " +
            " sum(b.personal_Level) as personalLevel, sum(b.rest_Level) as restLevel, sum(b.injury_Level) as injuryLevel, sum(b.delivery_Level) as deliveryLevel, sum(b.marital_Level) as maritalLevel, sum(b.funeral_Level) as funeralLevel, " +
            " sum(b.annual_Level) as annualLevel, sum(b.cancel_Level) as cancelLevel, sum(b.should_Work_Time) as shouldWorkTime, sum(b.actual_Work_Time) as actualWorkTime ";

    public String countSql = "select count(*) ";

    @Autowired
    private EntityManager entityManager;

    @Override
    public PageInfo collectResult(Date beginDate, Date endDate, SearchVo searchVo, Pageable pageable) {
        String fromSql = " from ehr_attendanceresult as  a, ehr_attendanceresult_detail as b ";
        String whereSql = " where a.id = b.result_id ";
        String joinSql = ") as d left join Hrmresource as res on d.resourceId = res.id " +
                " left join HrmDepartment as dep on res.departmentid = dep.id order by d.resourceId";
        List<Object> params = new ArrayList<>();
        params.add(beginDate);
        params.add(endDate);
        List<HrmResource> res = searchVo != null ? searchVo.getRes(searchVo.getResources()) : null;
        List<HrmDepartment> deps = searchVo != null ? searchVo.getDep(searchVo.getDeps()) : null;
        if (res != null && res.size() > 0 && deps != null && deps.size() > 0) {
            fromSql += ", HrmResource as c ";
            // 搜索全部
            if (searchVo.isAll()) {
                whereSql += "and a.resource_id = c.id and (a.resource_id in ?3 or c.departmentid in ?4) ";
            } else {    // 只搜在职
                whereSql += "and a.resource_id = c.id and (a.resource_id in ?3 or (c.departmentid in ?4 and c.status in (0,1,2,3)) ";
            }
            params.add(res);
            params.add(deps);
        } else if (res != null && res.size() > 0) {
            whereSql += "and a.resource_id in ?3";
            params.add(res);
        } else if (deps != null && deps.size() > 0) {
            fromSql += ", HrmResource as c";
            if (searchVo.isAll()) {
                whereSql += "and a.resource_id = c.id and c.departmentid in ?4 ";
            } else {
                whereSql += "and a.resource_id = c.id and c.departmentid in ?4 and c.status in (0,1,2,3) ";
            }
        } else {    //默认全部在职人员搜索
            fromSql += ", HrmResource as c";
            whereSql += "and a.resource_id = c.id and c.status in (0,1,2,3) ";
        }
        whereSql += "and a.date between ?1 and ?2 group by a.resource_id";
        // 实体内容
        Query query = entityManager.createNativeQuery(backSql + fromSql + whereSql + joinSql);
        // 统计总数
        Query countQuery = entityManager.createNativeQuery(countSql + fromSql + whereSql);
        int i = 1;
        for (Object param : params) {
            query.setParameter(i, param);
            countQuery.setParameter(i, param);
            i++;
        }
        if (pageable != null) {
            int currentPage = (pageable.getPageNumber() - 1) <= 0 ? 0 : pageable.getPageNumber() - 1;
            query.setFirstResult(currentPage * pageable.getPageSize());
            query.setMaxResults(pageable.getPageSize());
        }
//        List list = query.getResultList();
        List list = query.unwrap(SQLQuery.class).setResultTransformer(Transformers.aliasToBean(ResultCollectClass.class)).list();

        long count = Long.parseLong(countQuery.getSingleResult().toString());
        return new PageInfo(count, list);
    }
}
