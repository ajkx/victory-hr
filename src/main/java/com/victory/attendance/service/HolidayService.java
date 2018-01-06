package com.victory.attendance.service;

import com.victory.attendance.entity.AttendanceClasses;
import com.victory.attendance.entity.Holiday;
import com.victory.attendance.enums.HolidayType;
import com.victory.attendance.repository.HolidayRepository;
import com.victory.common.service.BaseService;
import com.victory.hrm.web.vo.HrmResourceVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Service
public class HolidayService extends BaseService<Holiday,Long>{

    @Autowired
    private AttendanceClassesService classesService;

    private HolidayRepository getRepository() {
        return (HolidayRepository) baseRepository;
    }

    @Override
    public Object packEntity(Holiday holiday) {
        Map result = new HashMap();
        result.put("id", holiday.getId());
        result.put("date", holiday.getDate());
        result.put("name", holiday.getName());
        HolidayType type = holiday.getType();
        result.put("type", type);
        result.put("typeName", type.getName());
        // 如果假日已过去 则不可再修改该数据
        if(holiday.getDate().before(new Date())) result.put("canEdit",Boolean.FALSE);
        else result.put("canEdit",Boolean.TRUE);
        if (type == HolidayType.rest) {
            result.put("useRest", holiday.getUseRest());
        } else if (type == HolidayType.work) {
            result.put("useClass", holiday.getUseClass());
            result.put("classes", holiday.getClasses());
        }
        return result;
    }
}
