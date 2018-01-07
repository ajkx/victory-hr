package com.victory.attendance.service;

import com.victory.attendance.entity.LevelRecord;
import com.victory.attendance.enums.RecordStatus;
import com.victory.attendance.repository.LevelRecordRepository;
import com.victory.common.service.BaseService;
import com.victory.hrm.web.vo.HrmResourceVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class LevelRecordService extends BaseService<LevelRecord,Long>{

    @Autowired
    private AttendanceSettingService settingService;

    private LevelRecordRepository getRepository() {
        return (LevelRecordRepository) baseRepository;
    }

    @Override
    public Object packEntity(LevelRecord record) {
        Map result = new HashMap();
        result.put("id", record.getId());
        result.put("type", record.getType());
        result.put("typeName", record.getType().getName());
        result.put("beginDate", record.getBeginDate());
        result.put("endDate", record.getEndDate());
        result.put("applyTime", settingService.calTimeByMin(record.getApplyTime(), 60));
        result.put("actualTime", record.getActualTime() == null ? "" : settingService.calTimeByMin(record.getActualTime(), 60));
        result.put("reason", record.getReason());
        result.put("status", record.getStatus());
        result.put("statusName", record.getStatus().getName());
        if(record.getStatus() != RecordStatus.calculate) result.put("canEdit",Boolean.FALSE);
        else result.put("canEdit",Boolean.TRUE);
        result.put("remark", record.getRemark());
        if (record.getResource() != null) {
            result.put("resource", new HrmResourceVo(record.getResource()));
        }
        return result;
    }
}
