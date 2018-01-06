package com.victory.attendance.service;

import com.victory.attendance.entity.LevelRecord;
import com.victory.attendance.entity.RepairRecord;
import com.victory.attendance.enums.RecordStatus;
import com.victory.attendance.repository.LevelRecordRepository;
import com.victory.attendance.repository.RepairRecordRepository;
import com.victory.common.service.BaseService;
import com.victory.hrm.web.vo.HrmResourceVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class RepairRecordService extends BaseService<RepairRecord,Long>{

    private RepairRecordRepository getRepository() {
        return (RepairRecordRepository) baseRepository;
    }

    @Override
    public Object packEntity(RepairRecord record) {
        Map result = new HashMap();
        result.put("id", record.getId());
        result.put("date", record.getDate());
        result.put("times", record.getTimes());
        result.put("reason", record.getReason());
        result.put("status", record.getStatus());
        result.put("statusName", record.getStatus().getName());
        if(record.getStatus() != RecordStatus.calculate) result.put("canEdit",Boolean.FALSE);
        else result.put("canEdit",Boolean.TRUE);
        if (record.getResource() != null) {
            result.put("resource", new HrmResourceVo(record.getResource()));
        }
        return result;
    }
}
