package com.victory.attendance.repository;

import com.victory.attendance.entity.LevelRecord;
import com.victory.attendance.entity.OverTimeRecord;
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
public interface OverTimeRecordRepository extends BaseRepository<OverTimeRecord,Long> {

    /**
     * 找出对应人员的加班开始日期在传进的时间区间，一般是求某天某人有的加班记录,如2018-12-12 00:00:00 ~ 2018-12-12 23:59:59
     * 如果搜出的加班记录的结束时间为跨天，则需要在计算时排除掉
     * @param resource
     * @param beginDate
     * @param endDate
     * @return
     */
    List<OverTimeRecord> findByResourceAndBeginDateBetween(HrmResource resource, Date beginDate, Date endDate);

    List<OverTimeRecord> findByStatus(RecordStatus status);

}
