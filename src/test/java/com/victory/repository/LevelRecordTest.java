package com.victory.repository;

import com.victory.attendance.entity.DateRecord;
import com.victory.attendance.entity.LevelRecord;
import com.victory.attendance.repository.AttendanceResultRepository;
import com.victory.attendance.repository.CollectorRepository;
import com.victory.attendance.repository.DateRecordRepository;
import com.victory.attendance.repository.LevelRecordRepository;
import com.victory.attendance.service.DateRecordService;
import com.victory.attendance.web.vo.PageInfo;
import com.victory.attendance.web.vo.ResultCollectClass;
import com.victory.common.utils.DateUtils;
import com.victory.hrm.entity.HrmResource;
import com.victory.hrm.repository.HrmResourceRepository;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    @Autowired
    private DateRecordRepository dateRecordRepository;

    @Autowired
    private DateRecordService recordService;

    @Autowired
    private HrmResourceRepository resourceRepository;

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
        DateTime dateTime1 = new DateTime(2018,12,15,0,0);
//        List<ResultCollect> resultCollects = resultRepository.collectByDate(dateTime.toDate(), dateTime1.toDate());
//        for (ResultCollect collect : resultCollects) {
//            System.out.println(collect.getNormalOvertime());
//            System.out.println(collect.getAbsentCount());
//            System.out.println(collect.getAbsentTime());
//            System.out.println(collect.getActualWorkDay());
//        }
        PageInfo list = collectorRepository.collectResult(dateTime.toDate(), dateTime1.toDate(), null, null);
        System.out.println(list.getTotalElements());
    }

    @Test
    public void testHrmResourceRespository() {
        DateTime dateTime = new DateTime(2018,12,31,0,0);
//        System.out.println(dateTime.getDayOfWeek());
//        System.out.println(dateTime.getWeekOfWeekyear());
//        System.out.println(dateTime.getWeekyear());
        DateTime dateTime1 = new DateTime(86390000);
        System.out.println(dateTime1.toString("HH:mm:ss"));
//        List l = resourceRepository.findOnWokingAndbetweenEntryDate(dateTime.toDate());
//        System.out.println(l.size());
    }

    @Test
    public void testTopRecord() throws ParseException {
        DateRecord dateRecord = dateRecordRepository.findFirstByOrderByDateDesc();
//        System.out.println(dateRecord.getDate());
        System.out.println(recordService.getTopRecord().getDate());
        System.out.println(recordService.getTopRecord().getDate());

//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-DD");
//        Date date1 = sdf.parse("2018-01-12");
//        Date date2 = sdf.parse("2018-01-15");
//        List<Date> dateList = DateUtils.getBetweenDays(date1, date2);
//        System.out.println(dateList.size());
//        for (Date date : dateList) {
//            System.out.println(sdf.format(date));
//        }
    }
}
