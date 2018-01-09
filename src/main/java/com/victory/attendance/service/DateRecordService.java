package com.victory.attendance.service;

import com.victory.attendance.entity.Card;
import com.victory.attendance.entity.DateRecord;
import com.victory.attendance.repository.AttendanceResultRepository;
import com.victory.attendance.repository.CardRepository;
import com.victory.attendance.repository.DateRecordRepository;
import com.victory.common.service.BaseService;
import com.victory.hrm.entity.HrmResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class DateRecordService extends BaseService<DateRecord,Long>{

    @Autowired
    private AttendanceResultRepository resultRepository;

    private DateRecordRepository getRepository() {
        return (DateRecordRepository) baseRepository;
    }

    @CachePut(value = "date")
    @Override
    public DateRecord update(DateRecord dateRecord) {
        return super.update(dateRecord);
    }

    @CachePut(value = "date")
    @Override
    public DateRecord save(DateRecord dateRecord) {
        return super.save(dateRecord);
    }

    @Cacheable(value = "date")
    public DateRecord getTopRecord() {
        DateRecord record = getRepository().findFirstByOrderByDateDesc();
        if(record == null) {
            record = new DateRecord();
            Date date = resultRepository.getTopRecordDate();
            if(date == null) date = new Date();
            record.setDate(date);
            save(record);
        }
        return record;
    }
}
