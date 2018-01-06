package com.victory.attendance.web.controller;

import com.victory.attendance.entity.AttendanceClasses;
import com.victory.attendance.entity.AttendanceGroup;
import com.victory.attendance.service.AttendanceClassesService;
import com.victory.attendance.service.AttendanceGroupService;
import com.victory.common.domain.result.ExceptionMsg;
import com.victory.common.domain.result.Response;
import com.victory.common.web.controller.BaseController;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * Created by ajkx
 * Date: 2017/8/2.
 * Time:9:10
 */
@RestController
@RequestMapping("api/attendanceGroup")
public class AttendanceGroupController extends BaseController<AttendanceGroup,Long>{

    @Autowired
    private AttendanceClassesService classesService;

    public AttendanceGroupController() {
        setResourceIdentity("attendance:group");
    }

    private AttendanceGroupService getService() {
        return (AttendanceGroupService) baseService;
    }

    @Override
    protected boolean checkEntity(AttendanceGroup entity, Response response) {
        // 判断考勤组名称是否重复，可以优化查询的HQL
        if(entity.getId() == null && getService().countByName(entity.getName()) > 0){
            response.setExceptionMsg(ExceptionMsg.GroupNameUsed);
            return false;
        }
        return true;
    }

    /**
     * example : page=0&size=10&sort=id,asc&sort=sex,desc
     * @param pageable
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Page list(
            @And(value = {
                @Spec(path = "name",spec = Like.class),
//                @Spec(path = "")
            })
                    Specification<AttendanceGroup> specification,
            @PageableDefault() Pageable pageable) {
        return packing((PageImpl<AttendanceGroup>) getService().findAll(specification,pageable),pageable);
    }

    public Page packing(PageImpl<AttendanceGroup> attendanceGroupPage,Pageable pageable) {
        List<AttendanceGroup> list = attendanceGroupPage.getContent();
        List newList = new ArrayList<>();
        for (AttendanceGroup group : list) {
            Map map = (Map) getService().packEntity(group);
            Set<AttendanceClasses> classesSet = (Set<AttendanceClasses>) map.get("classes");
            map.put("resourceCount", ((List)map.get("resources")).size());
            //todo 固定班制类型的内容，目前不做分割显示
            StringBuilder builder = new StringBuilder();

            List<Map<String, String>> timeList = new ArrayList<>();
            switch (group.getType()) {
                case fixed:
                    LinkedHashMap<Long, String> tempMap = new LinkedHashMap<>();
                    List<Long> workDayList = group.getWorkDayList();
                    for(int i = 0; i < workDayList.size(); i++) {
                        String value = "";
                        for (long key : tempMap.keySet()) {
                            if (key == workDayList.get(i)) {
                                value = tempMap.get(key) + "、" + getWeek(i).substring(1, 2);
                            }
                        }
                        tempMap.put(workDayList.get(i), value.equals("") ? getWeek(i) : value);
                    }
                    for (Long key : tempMap.keySet()) {
                        Map item = new HashMap();
                        item.put("week", tempMap.get(key));
                        if (key == 0) {
                            Map rest = new HashMap();
                            rest.put("name", "休息");
                            item.put("time", rest);
                        }else{
                            item.put("time", classesService.getClassesTime(getClassesBySet(classesSet, key)));
                        }
                        timeList.add(item);
                    }

                    break;
                case arrange:
                    for (AttendanceClasses classes : classesSet) {
                        builder.append(classes.getName() + "&nbsp;");
                        builder.append(classesService.getClassesTime(classes) + "</br>");
                    }
                    break;
                case free:
                    builder.append("不设置班次，自由打卡");
                    break;
            }
            map.put("timeList", timeList);
            newList.add(map);
        }
        return new PageImpl<List>(newList, pageable, attendanceGroupPage.getTotalElements());
    }

    public String getWeek(int i) {
        String week = "";
        switch (i) {
            case 0:
                week = "周一";
                break;
            case 1:
                week = "周二";
                break;
            case 2:
                week = "周三";
                break;
            case 3:
                week = "周四";
                break;
            case 4:
                week = "周五";
                break;
            case 5:
                week = "周六";
                break;
            case 6:
                week = "周日";
                break;
        }
        return week;
    }

    public AttendanceClasses getClassesBySet(Set<AttendanceClasses> classesSet, Long id) {
        for (AttendanceClasses classes : classesSet) {
            if ((long)classes.getId() == id) {
                return classes;
            }
        }
        return null;
    }

}
