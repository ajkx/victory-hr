package com.victory.repository;

import com.victory.attendance.entity.LevelRecord;
import com.victory.attendance.repository.AttendanceResultRepository;
import com.victory.attendance.repository.CollectorRepository;
import com.victory.attendance.repository.LevelRecordRepository;
import com.victory.attendance.web.vo.ResultCollect;
import com.victory.hrm.entity.HrmResource;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/12/28.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@TransactionConfiguration(defaultRollback = false)
public class LevelRecordTest {

    @Autowired
    private LevelRecordRepository recordRepository;

    @Autowired
    private AttendanceResultRepository resultRepository;

    @Autowired
    private CollectorRepository collectorRepository;

    @Test
    public void testRespository() {
        HrmResource resource = new HrmResource();
        resource.setId(2212l);
        DateTime dateTime = new DateTime(2017,12,28,8,10);
        Date beginDate = dateTime.toDate();
        dateTime.plusHours(8);
        Date endDate = dateTime.toDate();
        List<LevelRecord> recordList = recordRepository.findAcrossRecord(resource, beginDate, endDate);
        for (LevelRecord record : recordList) {
            System.out.println(record.getId());
        }
    }

    @Test
    public void testResultRespository() {
        DateTime dateTime = new DateTime(2017,12,11,0,0);
        DateTime dateTime1 = new DateTime(2017,12,15,0,0);
//        List<ResultCollect> resultCollects = resultRepository.collectByDate(dateTime.toDate(), dateTime1.toDate());
//        for (ResultCollect collect : resultCollects) {
//            System.out.println(collect.getNormalOvertime());
//            System.out.println(collect.getAbsentCount());
//            System.out.println(collect.getAbsentTime());
//            System.out.println(collect.getActualWorkDay());
//        }
        List<Object[]> list = collectorRepository.collectResult(dateTime.toDate(), dateTime1.toDate(), null, null);
        for (Object[] object : list) {
            System.out.println(object[0]);
        }
    }
}
