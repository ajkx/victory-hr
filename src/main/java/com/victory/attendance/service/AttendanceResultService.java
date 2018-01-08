package com.victory.attendance.service;

import com.victory.attendance.entity.AttendanceClasses;
import com.victory.attendance.entity.AttendanceResult;
import com.victory.attendance.entity.AttendanceResultDetail;
import com.victory.attendance.entity.Holiday;
import com.victory.attendance.enums.HolidayType;
import com.victory.attendance.enums.RecordStatus;
import com.victory.attendance.repository.AttendanceResultRepository;
import com.victory.attendance.repository.CollectorRepository;
import com.victory.attendance.repository.HolidayRepository;
import com.victory.attendance.web.vo.PageInfo;
import com.victory.common.service.BaseService;
import com.victory.common.utils.DateUtils;
import com.victory.common.web.vo.SearchVo;
import com.victory.hrm.web.vo.HrmResourceVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class AttendanceResultService extends BaseService<AttendanceResult,Long>{

    @Autowired
    private AttendanceClassesService classesService;

    @Autowired
    private CollectorRepository collectorRepository;

    @Autowired
    private AttendanceSettingService settingService;

    private AttendanceResultRepository getRepository() {
        return (AttendanceResultRepository) baseRepository;
    }

    public PageInfo getCollectData(Date date, SearchVo searchVo, Pageable pageable) {
        Date endDate = DateUtils.getMonthLastDay(date);
        return collectorRepository.collectResult(date, endDate, searchVo, pageable);
    }

    @Override
    public Object packEntity(AttendanceResult record) {
        Map result = new HashMap();
        result.put("id", record.getId());
        result.put("date", record.getDate());
        result.put("classId", record.getClassId());
        result.put("className", record.getClassName());
        if (record.getResource() != null) {
            result.put("resource", new HrmResourceVo(record.getResource()));
        }
        result.put("normalOvertime", settingService.calTimeByUnit(record.getNormalOvertime(), 60));
        result.put("weekendOvertime", settingService.calTimeByUnit(record.getWeekendOvertime(), 60));
        result.put("festivalOvertime", settingService.calTimeByUnit(record.getFestivalOvertime(), 60));
//        result.put("shouldWorkDay", record.getShouldWorkDay());
//        result.put("actualWorkDay", record.getActualWorkDay());
        List<AttendanceResultDetail> details = record.getDetails();
        int i = 1;
        long lateTime = 0;
        int lateCount = 0;
        long earlyTime = 0;
        int earlyCount = 0;
        long absentTime = 0;
        int absentCount = 0;
        long personalLevel = 0;
        long restLevel = 0;
        long injuryLevel = 0;
        long deliveryLevel = 0;
        long maritalLevel = 0;
        long funeralLevel = 0;
        long annualLevel = 0;
        long cancelLevel = 0;
        long shouldWorkTime = 0;
        long actualWorkTime = 0;
        for (AttendanceResultDetail detail : details) {
            lateTime += detail.getLateTime();
            lateCount += detail.getLateCount();
            earlyTime += detail.getEarlyTime();
            earlyCount += detail.getEarlyCount();
            absentTime += detail.getAbsentTime();
            absentCount += detail.getAbsentCount();
            personalLevel += detail.getPersonalLevel();
            restLevel += detail.getRestLevel();
            injuryLevel += detail.getInjuryLevel();
            deliveryLevel += detail.getDeliveryLevel();
            maritalLevel += detail.getMarriedLevel();
            funeralLevel += detail.getFuneralLevel();
            annualLevel += detail.getAnnualLevel();
            cancelLevel += detail.getCancelLevel();
            shouldWorkTime += detail.getShouldWorkTime();
            actualWorkTime += detail.getActualWorkTime();
            result.put("checkBeginTime_" + i, detail.getShouldBeginTime());
            result.put("actualBeginTime_" + i, detail.getActualBeginTime());
            result.put("beginResultType_" + i, detail.getBeginResultType());
            result.put("beginResultDesc_" + i, detail.getBeginResultDesc());
            result.put("checkEndTime_" + i, detail.getShouldEndTime());
            result.put("actualBeginTime_" + i, detail.getActualEndTime());
            result.put("endResultType_" + i, detail.getEndResultType());
            result.put("endResultDesc_" + i, detail.getEndResultDesc());
            i++;
        }
        result.put("lateTime", lateTime);
        result.put("lateCount", lateCount);
        result.put("earlyTime", earlyTime);
        result.put("earlyCount", earlyCount);
        result.put("absentTime", absentTime);
        result.put("absentCount", absentCount);
        result.put("personalLevel", personalLevel);
        result.put("restLevel", restLevel);
        result.put("injuryLevel", injuryLevel);
        result.put("deliveryLevel", deliveryLevel);
        result.put("maritalLevel", maritalLevel);
        result.put("funeralLevel", funeralLevel);
        result.put("annualLevel", annualLevel);
        result.put("cancelLevel", cancelLevel);
        result.put("shouldWorkTime", shouldWorkTime);
        result.put("actualWorkTime", actualWorkTime);

        return result;
    }
}
