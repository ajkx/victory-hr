package com.victory.attendance.repository;

import com.victory.attendance.entity.AttendanceResult;
import com.victory.attendance.web.vo.ResultCollect;
import com.victory.common.repository.BaseRepository;
import com.victory.hrm.entity.HrmResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by ajkx
 * Date: 2017/8/1.
 * Time:10:25
 */
@Repository
public interface AttendanceResultRepository extends BaseRepository<AttendanceResult,Long> {

    String collectSql = "select sum(a.shouldWorkDay) as shouldWorkDay, sum(a.actualWorkDay) as actualWorkDay, sum(a.normalOvertime) as normalOvertime, sum(a.weekendOvertime) as weekendOvertime, sum(a.festivalOvertime) as festivalOvertime, " +
            "sum(b.lateTime) as lateTime, sum(b.lateCount) as lateCount, sum(b.earlyTime) as earlyTime, sum(b.earlyCount) as earlyCount, sum(b.absentTime) as absentTime, sum(b.absentCount) as absentCount, " +
            "sum(b.personalLevel) as personalLevel, sum(b.restLevel) as restLevel, sum(b.injuryLevel) as injuryLevel, sum(b.deliveryLevel) as deliveryLevel, sum(b.maritalLevel) as maritalLevel, sum(b.funeralLevel) as funeralLevel, " +
            "sum(b.annualLevel) as annualLevel, sum(b.cancelLevel) as cancelLevel, sum(b.shouldWorkTime) as shouldWorkTime, sum(b.actualWorkTime) as actualWorkTime " +
            "from AttendanceResult a, AttendanceResultDetail b where a.id = b.result";
    /**
     * 找date之前的跨天明细
     * @param date
     * @return
     */
    @Query("from AttendanceResult where status = 0 and date < ?1")
    List<AttendanceResult> findAcrossDayByDate(Date date);

    @Query(collectSql + " and a.date between ?1 and ?2 group by a.resource")
    Page<ResultCollect> collectByDate(Date beginDate, Date endDate, Pageable pageable);

    @Query("select max(date) from AttendanceResult ")
    Date getTopRecordDate();

    AttendanceResult findByDateAndResource(Date date, HrmResource resource);
}
