package com.victory.attendance.service;

import com.victory.attendance.repository.AttendanceClassesRepository;
import com.victory.attendance.entity.AttendanceClasses;
import com.victory.attendance.entity.AttendanceClassesDetail;
import com.victory.common.service.BaseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ajkx
 * Date: 2017/8/1.
 * Time:14:07
 */
@Service
public class AttendanceClassesService extends BaseService<AttendanceClasses,Long>{


    private AttendanceClassesRepository getRepository() {
        return (AttendanceClassesRepository) baseRepository;
    }
    
    // TODO: 2017/11/23 timelist应该有一个时间顺序是否正确的检测

    @Override
    public AttendanceClasses save(AttendanceClasses attendanceClasses) {
        setRelation(attendanceClasses);
        return super.save(attendanceClasses);
    }

    @Override
    public AttendanceClasses update(AttendanceClasses attendanceClasses) {
        setRelation(attendanceClasses);
        return super.update(attendanceClasses);
    }

    public AttendanceClasses findByName(String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        return getRepository().findByName(name);
    }

    public long countByName(String name) {
        if (StringUtils.isEmpty(name)) {
            return 0;
        }
        return getRepository().countByName(name);
    }
    private void setRelation(AttendanceClasses attendanceClasses) {
        List<AttendanceClassesDetail> list = attendanceClasses.getTimeList();
        for(int i = 0; i < list.size(); i++) {  // 由many的一端控制关联关系，优先性能
            AttendanceClassesDetail detail = list.get(i);
            detail.setClasses(attendanceClasses);
        }
    }

    public Map getClassesTime(AttendanceClasses classes) {
        if(classes == null) return null;
        List<AttendanceClassesDetail> timeList = classes.getTimeList();
        Map map = new HashMap();
        List list = new ArrayList();
        map.put("name", classes.getName());
        for (AttendanceClassesDetail detail : timeList) {
            String result = "";
            if (detail.getBeginAcross()) {
                result += "次日";
            }
            result += detail.getBeginTime().toString().substring(0,5) + "-";
            if (detail.getEndAcross()) {
                result += "次日";
            }
            result += detail.getEndTime().toString().substring(0,5);
            list.add(result);
        }
        map.put("list", list);
        return map;
    }

//    @Override
//    public Object packEntity(AttendanceClasses classes) {
//        Map result = new HashMap();
//        result.put("id", classes.getId());
//        result.put("name", classes.getName());
//        result.put("shortName", classes.getShortName());
//        result.put("haveRest", classes.getHaveRest());
//        result.put("offDutyCheck", classes.getOffDutyCheck());
//        result.put("lateMinute", classes.getLateMinute());
//        result.put("earlyMinute", classes.getEarlyMinute());
//        result.put("earlyMinute", classes.getEarlyMinute());
//        result.put("workTime", classes.getWorkTime());
//        return super.packEntity(classes);
//    }
}
