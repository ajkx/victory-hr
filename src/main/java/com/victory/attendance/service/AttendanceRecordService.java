package com.victory.attendance.service;

import com.victory.attendance.entity.AttendanceRecord;
import com.victory.attendance.entity.Card;
import com.victory.attendance.enums.RecordType;
import com.victory.attendance.repository.AttendanceRecordRepository;
import com.victory.attendance.repository.CardRepository;
import com.victory.common.service.BaseService;
import com.victory.hrm.entity.HrmResource;
import com.victory.hrm.repository.HrmResourceRepository;
import com.victory.hrm.web.vo.HrmResourceVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class AttendanceRecordService extends BaseService<AttendanceRecord,Long>{

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private HrmResourceRepository resourceRepository;

    private AttendanceRecordRepository getRepository() {
        return (AttendanceRecordRepository) baseRepository;
    }

    public boolean checkUnique(long resourceId, Date date) {
        if(getRepository().countByResourceAndDate(resourceId, date) > 0) return false;
        return true;
    }

    @Override
    public Object packEntity(AttendanceRecord record) {
        Map result = new HashMap();

        result.put("date", record.getDate());
        HrmResource resource = null;
        if (record.getType() == RecordType.machine) {
            String cardNo = record.getCard();
            result.put("card", cardNo);
            Card card = cardRepository.findByCardNo(cardNo);
            if (card != null) {
                resource = card.getResource();
            }

            result.put("machineNo", record.getMachineNo());
        } else if (record.getType() == RecordType.manual) {
            if (record.getResource() != null){
                resource = resourceRepository.findOne(record.getResource());
            }
            result.put("id", record.getId());
            result.put("reason", record.getReason());
            // 如果签卡时间已过去 则不可再修改该数据
            if(record.getDate().before(new Date())) result.put("canEdit",Boolean.FALSE);
            else result.put("canEdit",Boolean.TRUE);
        }
        if (resource != null) {
            HrmResourceVo resourceVo = new HrmResourceVo(resource);
            result.put("resource", resourceVo);
        }
        return result;
    }
}
