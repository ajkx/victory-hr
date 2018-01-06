package com.victory.attendance.service;

import com.victory.attendance.entity.LevelRecord;
import com.victory.attendance.entity.OverTimeRecord;
import com.victory.attendance.enums.RecordStatus;
import com.victory.attendance.repository.LevelRecordRepository;
import com.victory.attendance.repository.OverTimeRecordRepository;
import com.victory.common.service.BaseService;
import com.victory.hrm.web.vo.HrmResourceVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class OverTimeService extends BaseService<OverTimeRecord,Long>{

    @Autowired
    private AttendanceSettingService settingService;

    private OverTimeRecordRepository getRepository() {
        return (OverTimeRecordRepository) baseRepository;
    }

    @Override
    public Object packEntity(OverTimeRecord record) {
        Map result = new HashMap();
        result.put("id", record.getId());
        result.put("type", record.getType());
        result.put("typeName", record.getType().getName());
        result.put("beginDate", record.getBeginDate());
        result.put("endDate", record.getEndDate());
        result.put("actualBeginDate", record.getActualBeginDate());
        result.put("actualEndDate", record.getActualEndDate());
        result.put("applyTime", settingService.calTimeByUnit(record.getApplyTime(), 60));
        result.put("actualTime", record.getActualTime() == null ? "" : settingService.calTimeByUnit(record.getActualTime(), 60));
        result.put("reason", record.getReason());
        result.put("status", record.getStatus());
        result.put("statusName", record.getStatus().getName());
        if(record.getStatus() != RecordStatus.calculate) result.put("canEdit",Boolean.FALSE);
        else result.put("canEdit",Boolean.TRUE);
        result.put("link", record.getLink());
        result.put("remark", record.getRemark());
        if (record.getResource() != null) {
            result.put("resource", new HrmResourceVo(record.getResource()));
        }
        result.put("maxScope", record.getMaxScope());
        result.put("minScope", record.getMinScope());
        result.put("multiple", record.getMultiple());
        return result;
    }
}
