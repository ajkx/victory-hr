package com.victory.attendance.repository;

import com.victory.attendance.entity.AttendanceClasses;
import com.victory.attendance.entity.AttendanceRecord;
import com.victory.common.repository.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
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
public interface AttendanceRecordRepository extends BaseRepository<AttendanceRecord,Long> {

    List<AttendanceRecord> findByCardAndDateBetween(String card, Date beginDate, Date endDate);

    long countByResourceAndDate(long resourceId, Date date);
}
