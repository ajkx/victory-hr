package com.victory.attendance.repository.impl;

import com.victory.attendance.repository.CollectorRepository;
import com.victory.attendance.web.vo.ResultCollect;
import com.victory.common.web.vo.SearchVo;
import com.victory.hrm.entity.HrmDepartment;
import com.victory.hrm.entity.HrmResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CollectorRepositoryImpl implements CollectorRepository {

    public String backSql = "select sum(a.shouldWorkDay) as shouldWorkDay, sum(a.actualWorkDay) as actualWorkDay, sum(a.normalOvertime) as normalOvertime, sum(a.weekendOvertime) as weekendOvertime, sum(a.festivalOvertime) as festivalOvertime, " +
            " sum(b.lateTime) as lateTime, sum(b.lateCount) as lateCount, sum(b.earlyTime) as earlyTime, sum(b.earlyCount) as earlyCount, sum(b.absentTime) as absentTime, sum(b.absentCount) as absentCount, " +
            " sum(b.personalLevel) as personalLevel, sum(b.restLevel) as restLevel, sum(b.injuryLevel) as injuryLevel, sum(b.deliveryLevel) as deliveryLevel, sum(b.maritalLevel) as maritalLevel, sum(b.funeralLevel) as funeralLevel, " +
            " sum(b.annualLevel) as annualLevel, sum(b.cancelLevel) as cancelLevel, sum(b.shouldWorkTime) as shouldWorkTime, sum(b.actualWorkTime) as actualWorkTime ";

    @Autowired
    private EntityManager entityManager;

    @Override
    public List collectResult(Date beginDate, Date endDate, SearchVo searchVo, Pageable pageable) {
        String fromSql = " from AttendanceResult a, AttendanceResultDetail  b ";
        String whereSql = " where a.id = b.result ";

        List<Object> params = new ArrayList<>();
        params.add(beginDate);
        params.add(endDate);
        List<HrmResource> res = searchVo != null ? searchVo.getRes(searchVo.getResources()) : null;
        List<HrmDepartment> deps = searchVo != null ? searchVo.getDep(searchVo.getDeps()) : null;
        if (res != null && res.size() > 0 && deps != null && deps.size() > 0) {
            fromSql += ", HrmResource c";
            // 搜索全部
            if (searchVo.isAll()) {
                whereSql += "and a.resource = c.id and (a.resource in ?3 or c.department in ?4) ";
            } else {    // 只搜在职
                whereSql += "and a.resource = c.id and (a.resource in ?3 or (c.department in ?4 and c.status in (0,1,2,3)) ";
            }
            params.add(res);
            params.add(deps);
        } else if (res != null && res.size() > 0) {
            whereSql += "and a.resource in ?3";
            params.add(res);
        } else if (deps != null && deps.size() > 0) {
            fromSql += ", HrmResource c";
            if (searchVo.isAll()) {
                whereSql += "and a.resource = c.id and c.department in ?4 ";
            } else {
                whereSql += "and a.resource = c.id and c.department in ?4 and c.status in (0,1,2,3) ";
            }
        } else {    //默认全部在职人员搜索
            fromSql += ", HrmResource c";
            whereSql += "and c.status in (0,1,2,3) ";
        }
        whereSql += "and a.date between ?1 and ?2 group by a.resource order by a.resource";
        Query query = entityManager.createQuery(backSql + fromSql + whereSql);
        int i = 1;
        for (Object param : params) {
            query.setParameter(i, param);
            i++;
        }
        if (pageable != null) {
            int currentPage = (pageable.getPageNumber() - 1) <= 0 ? 0 : pageable.getPageNumber() - 1;
            query.setFirstResult(currentPage * pageable.getPageSize());
            query.setMaxResults(pageable.getPageSize());
        }
        List list = query.getResultList();
        entityManager.close();
        return list;
    }
}
