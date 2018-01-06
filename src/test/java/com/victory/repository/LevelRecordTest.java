package com.victory.repository;

import com.victory.attendance.entity.LevelRecord;
import com.victory.attendance.repository.LevelRecordRepository;
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
}
