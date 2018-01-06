package com.victory.attendance.repository;

import com.victory.attendance.entity.LevelRecord;
import com.victory.attendance.entity.RepairRecord;
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
public interface RepairRecordRepository extends BaseRepository<RepairRecord,Long> {

    List<RepairRecord> findByResourceAndDate(HrmResource resource, Date date);

    List<RepairRecord> findByStatus(RecordStatus status);

}
