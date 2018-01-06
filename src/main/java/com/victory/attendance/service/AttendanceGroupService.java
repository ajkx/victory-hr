package com.victory.attendance.service;

import com.victory.attendance.repository.AttendanceGroupRepository;
import com.victory.attendance.entity.AttendanceGroup;
import com.victory.attendance.entity.SpecialDate;
import com.victory.common.service.BaseService;
import com.victory.hrm.repository.HrmResourceRepository;
import com.victory.hrm.entity.HrmResource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by ajkx
 * Date: 2017/8/1.
 * Time:14:07
 */
@Service
public class AttendanceGroupService extends BaseService<AttendanceGroup,Long>{

    @Autowired
    private HrmResourceRepository resourceRepository;

    private AttendanceGroupRepository getRepository() {
        return (AttendanceGroupRepository) baseRepository;
    }


    @Transactional
    @Override
    public AttendanceGroup save(AttendanceGroup attendanceGroup) {
        setRelation(attendanceGroup);
        return super.save(attendanceGroup);
    }

    @Transactional
    @Override
    public AttendanceGroup update(AttendanceGroup attendanceGroup) {
        setRelation(attendanceGroup);
        return super.update(attendanceGroup);
    }

    public AttendanceGroup findByName(String name) {
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

    private void setRelation(AttendanceGroup group) {
        Set<SpecialDate> specialDates = group.getSpecialOnDate();
        for (SpecialDate specialDate : specialDates) {
            specialDate.setGroup(group);
        }
        Set<HrmResource> resources = group.getResources();
        List<Long> ids = new ArrayList<>();

        for (HrmResource resource : resources) {
            if(resource.getId() != null && resource.getId() > 0)
                ids.add((long)resource.getId());

        }
        /**
         * 批量清理resource可能存在的以前的关联
         */
        if(ids.size() > 0) getRepository().deleteGroupAndResourceRelation(ids);
    }

    @Override
    public Object packEntity(AttendanceGroup entity) {
        Map result = new HashMap();
        result.put("id", entity.getId());
        result.put("name", entity.getName());
        result.put("type", entity.getType());
        result.put("holidayRest", entity.getHolidayRest() == null ? 0 : entity.getHolidayRest());
        result.put("classes", entity.getClasses());
        result.put("defaultClassId", entity.getDefaultClassId());
        result.put("workDayList", entity.getWorkDayList());
        result.put("specialOffDate", entity.getSpecialOffDate());
        result.put("specialOnDate", entity.getSpecialOnDate());
        result.put("description", entity.getDescription());

        Set<HrmResource> resources = entity.getResources();
        List<Map<String, Object>> resourceList = new ArrayList<>();
        for (HrmResource resource : resources) {
            Map<String, Object> temp = new HashMap<>();
            temp.put("id", resource.getId());
            temp.put("name", resource.getName());
            resourceList.add(temp);
        }
        result.put("resources", resourceList);
        return result;
    }
}
