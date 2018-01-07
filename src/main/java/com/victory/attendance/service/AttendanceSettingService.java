package com.victory.attendance.service;

import com.victory.attendance.entity.AttendanceSetting;
import com.victory.attendance.enums.UnitType;
import com.victory.attendance.repository.AttendanceSettingRepository;
import com.victory.common.service.BaseService;
import com.victory.hrm.service.HrmResourceService;
import com.victory.hrm.web.vo.HrmResourceVo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class AttendanceSettingService extends BaseService<AttendanceSetting,Long>{

    @Autowired
    private HrmResourceService resourceService;

    private static Logger logger = Logger.getLogger(AttendanceSetting.class);

    private AttendanceSettingRepository getRepository() {
        return (AttendanceSettingRepository) baseRepository;
    }

    @Override
    public Object packEntity(AttendanceSetting setting) {
        Map result = new HashMap();
        // 基本信息
        result.put("unitType", setting.getUnitType());
        Set<Long> res = setting.getIgnoreResources();
        List resList = new ArrayList<>();
        for (Long id : res) {
            resList.add(new HrmResourceVo(resourceService.findOne(id)));
        }
        result.put("ignoreResources", resList);

        // 大小周信息
        result.put("oddEvenWeek", setting.getOddEvenWeek()  != null && setting.getOddEvenWeek() ? 1 : 0);

        Set<Long> odd = setting.getOddResource();
        List oddList = new ArrayList<>();
        for (Long id : odd) {
            oddList.add(new HrmResourceVo(resourceService.findOne(id)));
        }
        result.put("oddResource", oddList);

        Set<Long> even = setting.getEvenResource();
        List evenList = new ArrayList<>();
        for (Long id : even) {
            evenList.add(new HrmResourceVo(resourceService.findOne(id)));
        }
        result.put("evenResource", evenList);

        // 加班设置
        result.put("calculateType", setting.getCalculateType());
        result.put("beginMinute", setting.getBeginMinute());
        result.put("endMinute", setting.getEndMinute());
        result.put("acrossDay", setting.getAcrossDay());
        result.put("acrossOffset", setting.getAcrossOffset());
        result.put("normalPeriod", setting.getNormalPeriod());
        result.put("weekendPeriod", setting.getWeekendPeriod());
        result.put("festivalPeriod", setting.getFestivalPeriod());
        return result;
    }

    @CachePut(value = "setting")
    @Override
    public AttendanceSetting update(AttendanceSetting attendanceSetting) {
        return super.update(attendanceSetting);
    }

    @Cacheable(value = "setting")
    public AttendanceSetting getTopRecord() {
        AttendanceSetting setting = getRepository().getTopRecord();
        if (setting == null) {
            setting = new AttendanceSetting();
            setting.setUnitType(UnitType.halfHour);
            setting.setOddEvenWeek(false);
            save(setting);
            logger.error("重新生成了考勤设置数据，请检查以前的数据为什么被删除了！");
        }
        return setting;
    }

    public Object calTimeByUnit(long value, int unit) {
        if(value == 0) return 0;
        AttendanceSetting setting = getTopRecord();
        UnitType unitType = setting.getUnitType();

        double result = (double)value / (double)unit;
        int integer = (int) (value / unit);  // 整数部分
        double decimal = result - integer;   // 小数部分
        if (decimal == 0) return integer;    // 如果小数为0 即返回整数

        switch (unitType) {
            case min:
                result = ((int)(result*100)) / 100d;        // min 返回的是相除后保留两位小数的值
                break;
            case halfHour:
                if(decimal > 0.5) result = (double) integer + 0.5;                    // 判断小数部分是否大于unit的一半
                else return integer;
                break;
            case hour:
                return integer;         // 直接返回整数部分
        }
        return result;
    }

    public Object calTimeByMin(long value, int unit) {
        if(value == 0) return 0;

        double result = (double)value / (double)unit;
        int integer = (int) (value / unit);  // 整数部分
        double decimal = result - integer;   // 小数部分
        if (decimal == 0) return integer;    // 如果小数为0 即返回整数
        result = ((int)(result*100)) / 100d;        // min 返回的是相除后保留两位小数的值
        return result;
    }
}
