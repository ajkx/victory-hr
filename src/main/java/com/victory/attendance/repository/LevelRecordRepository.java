package com.victory.attendance.repository;

import com.victory.attendance.entity.AttendanceRecord;
import com.victory.attendance.entity.LevelRecord;
import com.victory.attendance.enums.RecordStatus;
import com.victory.common.repository.BaseRepository;
import com.victory.hrm.entity.HrmResource;
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
public interface LevelRecordRepository extends BaseRepository<LevelRecord,Long> {

    /**
     * 找出该员工的有时间交集的请假记录
     * @param resource
     * @param beginDate
     * @param endDate
     * @return
     */
    @Query("from LevelRecord where resource = ?1 and (beginDate between ?2 and ?3 or endDate between ?2 and ?3" +
            " or ?2 between beginDate and endDate or ?3 between beginDate and endDate)")
    List<LevelRecord> findAcrossRecord(HrmResource resource, Date beginDate, Date endDate);

    List<LevelRecord> findByStatus(RecordStatus status);

    List<LevelRecord> findByResourceAndBeginDateBetween(HrmResource resource, Date beginDate, Date endDate);
    /**
     * 找出不为销假的时间交集记录
     * @param resource
     * @param beginDate
     * @param endDate
     * @return
     */
    @Query("from LevelRecord where resource = ?1 and (beginDate between ?2 and ?3 or endDate between ?2 and ?3" +
            " or ?2 between beginDate and endDate or ?3 between beginDate and endDate) and type != 7")
    List<LevelRecord> findNormalAcrossRecord(HrmResource resource, Date beginDate, Date endDate);
}
