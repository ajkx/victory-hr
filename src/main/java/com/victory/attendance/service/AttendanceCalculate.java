package com.victory.attendance.service;

import com.victory.attendance.entity.*;
import com.victory.attendance.enums.HolidayType;
import com.victory.attendance.enums.RecordStatus;
import com.victory.attendance.repository.AttendanceClassesRepository;
import com.victory.attendance.repository.AttendanceResultRepository;
import com.victory.attendance.repository.HolidayRepository;
import com.victory.common.utils.DateUtils;
import com.victory.common.utils.NullUtils;
import com.victory.hrm.entity.HrmResource;
import com.victory.hrm.repository.HrmResourceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static com.victory.common.utils.NullUtils.check;

@Service
public class AttendanceCalculate {

    private static Logger logger = LoggerFactory.getLogger(AttendanceCalculate.class);

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-DD");

    @Autowired
    private DateRecordService dateRecordService;

    @Autowired
    private AttendanceSettingService settingService;

    @Autowired
    private AttendanceResultRepository resultRepository;

    @Autowired
    private HrmResourceRepository resourceRepository;

    @Autowired
    private HolidayRepository holidayRepository;

    @Autowired
    private AttendanceClassesRepository classesRepository;



    /**
     * 循环计算最后考勤日期和今日之间（不包头尾）的日期的计算
     */
    @Transactional
    public void calculateAll() {
        DateRecord dateRecord = dateRecordService.getTopRecord();
        Date beginDate = dateRecord.getDate();
        Date endDate = new Date();
        List<Date> dateList = DateUtils.getBetweenDays(beginDate, endDate);
        if (dateList.size() == 0) {
            System.out.println(dateFormat.format(beginDate) + "该日期已生成数据");
            return;
        }

        long startTime = System.currentTimeMillis();

        for (Date date : dateList) {
            calculateByDate(date);
        }
        dateRecord.setDate(DateUtils.getYesterday());
        dateRecordService.update(dateRecord);

        //todo 今日的计算
        long endTime = System.currentTimeMillis();
        System.out.println("本次全局考勤计算完成，共耗时" + (endTime - startTime) / 1000 + "秒, 最后考勤结果生成日期为:" + dateFormat.format(DateUtils.getYesterday()));
    }

    @Transactional
    public void calculateByDate(Date date) {
        System.out.println("==========正在执行" + dateFormat.format(date) + "的考勤计算");

        // 先计算跨天明细记录
        List<AttendanceResult> acrossResult = resultRepository.findAcrossDayByDate(date);
        for (AttendanceResult result : acrossResult) {
            calculateResult(result);
            resultRepository.save(result);
        }

        List<HrmResource> resources = resourceRepository.findOnWokingAndbetweenEntryDate(date);
        for (HrmResource resource : resources) {

        }
    }

    @Transactional
    public void calculateByDateAndResource(Date date, HrmResource resource) {
        AttendanceSetting setting = settingService.getTopRecord();
        // 不进行考勤的人员
        if (check(setting.getIgnoreResources(), resource.getId())) {
            return;
        }
        // 定义result
        AttendanceResult result = resultRepository.findByDateAndResource(date, resource);
        if (result == null) {
            result = new AttendanceResult();
            result.setDate(date);
            result.setResource(resource);
        }

        AttendanceGroup group = resource.getGroup();
        if (group == null) {
            result.setClassName("不在考勤组");
        } else {
            switch (group.getType()) {
                case fixed:
                    long classId = getClassByGroup(group, date);
                    result.setClassId(classId);

                    // 判断优先级 局部特殊出勤日期 > 局部特殊休息日期 > 全局假日 > 大小周 > 考勤班次
                    SpecialDate specialDate = checkSpecialDate(group.getSpecialOnDate(), date);
                    if (specialDate != null) {  // 特殊出勤日期
                        result.setClassId(specialDate.getClassId());
                    } else {
                        if (check(group.getSpecialOffDate(), dateFormat.format(date))) {  // 特殊休息日期
                            result.setClassId(0);
                            result.setClassName("休息");
                        } else {
                            Holiday holiday = null;
                            if (check(group.getHolidayRest())) {
                                holiday = holidayRepository.findByDate(date);
                            }
                            if (holiday != null) {  // 全局假日判断
                                switch (holiday.getType()) {
                                    case official:
                                        result.setClassId(0);
                                        result.setClassName("法定假期");
                                        break;
                                    case rest:
                                        result.setClassId(0);
                                        result.setClassName("调配休息日");
                                        if (check(holiday.getUseRest())) {

                                        }
                                        break;
                                    case work:
                                        // 调配出勤日统一班次或者使用考勤组默认班次
                                        if (check(holiday.getUseClass())) {
                                            result.setClassId(holiday.getClassId());
                                        } else {
                                            result.setClassId(group.getDefaultClassId());
                                        }
                                        break;
                                }
                            } else {    // 大小周判断 周六周日都为休息日
                                int week = DateUtils.getDayOfWeek(date);
                                if (week == 6 || week == 7) {
                                    if (check(setting.getIgnoreResources())) {
                                        if (DateUtils.checkOddWeek(date)) {
                                            if (check(setting.getOddResource(), resource.getId())) {
                                                result.setClassId(0);
                                                result.setClassName("单周休息");
                                            }
                                        } else {
                                            if (check(setting.getEvenResource(), resource.getId())) {
                                                result.setClassId(0);
                                                result.setClassName("双周休息");
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
                case arrange:
                    break;
                case free:
                    break;
            }
            // 判断是否为休息班次
            if (check(result.getClassId())) {
                if (check(result.getClassName())) {
                    result.setClassName("休息");
                }
            } else {
                AttendanceClasses classes = classesRepository.findOne(result.getClassId());
                if (classes == null) {
                    result.setClassName("关联的班次不存在");
                } else {
                    // 跨天的出勤整条都放在下一天计算（暂时无法分段计算
                    boolean isAcross = false;
                    List<AttendanceClassesDetail> details = classes.getTimeList();
                    for (AttendanceClassesDetail detail : details) {
                        if (check(detail.getBeginAcross()) || check(detail.getBeginAcross())) {
                            result.setStatus(RecordStatus.calculate);
                            isAcross = true;
                            break;
                        }
                    }
                    if (!isAcross) {
                        result.setClasses(classes);
                        calculateResult(result);
                    }
                }
            }
        }
        resultRepository.save(result);
    }

    public void calculateResult(AttendanceResult result) {
        AttendanceClasses classes = result.getClasses();
        if (classes == null) {
            classes = classesRepository.findOne(result.getClassId());
        }
    }

    /**
     * 根据考勤组设置的班次列表获取该日对应的班次
     * @param group
     * @param date
     * @return
     */
    public long getClassByGroup(AttendanceGroup group, Date date) {
        List<Long> workDayList = group.getWorkDayList();
        int week = DateUtils.getDayOfWeek(date);
        long classId = workDayList.get(week - 1);
        return classId;
    }

    public SpecialDate checkSpecialDate(Set<SpecialDate> dates, Date date) {
        if(dates == null || dates.size() == 0) return null;
        for (SpecialDate specialDate : dates) {
            if (specialDate.equals(date)) {
                return specialDate;
            }
        }
        return null;
    }
}
