package com.victory.repository;

import com.victory.attendance.entity.*;
import com.victory.attendance.repository.AttendanceClassesRepository;
import com.victory.attendance.repository.AttendanceGroupRepository;
import com.victory.attendance.repository.AttendanceSettingRepository;
import com.victory.hrm.entity.HrmDepartment;
import com.victory.hrm.entity.HrmStatus;
import com.victory.hrm.entity.HrmSubCompany;
import com.victory.hrm.repository.HrmResourceRepository;
import com.victory.hrm.entity.HrmResource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@TransactionConfiguration(defaultRollback = false)
public class RepositoryTests {

	@Autowired
	private HrmResourceRepository resourceRepository;

	@Autowired
	private AttendanceClassesRepository classesRepository;

	@Autowired
	private AttendanceGroupRepository groupRepository;

	@Autowired
	private AttendanceSettingRepository settingRepository;

	@Test
	public void contextLoads() {

	}

	@Test
	public void testSetting() {
		AttendanceSetting setting = settingRepository.getTopRecord();
		System.out.println(setting.getId());
	}
	@Test
	public void testRepository() {
		HrmResource hrmResource = new HrmResource();
		hrmResource = resourceRepository.findOne(10l);
		System.out.println(hrmResource.getName());
		HrmSubCompany subCompany = new HrmSubCompany();
		subCompany.setId(1l);
		HrmDepartment d1 = new HrmDepartment();
		d1.setId(80l);
		List<HrmDepartment> departments = new ArrayList<>();
		departments.add(d1);
		List<Long> ll = new ArrayList<>();
		ll.add(80l);
//		List<HrmResource> resourceList = resourceRepository.findBySubCompanyAndStatusIn(subCompany, null);
//		System.out.println(resourceList.size());
	}
	
	@Test
	@Transactional
	public void testClassesDelete() {
		classesRepository.deleteClassDetailOnClassesIsNull();
	}

	@Test
	@Transactional
	public void testClasses() {
//		Session session = sessionFactory.openSession();
//		Transaction tx = session.beginTransaction();
		AttendanceClasses classes = new AttendanceClasses();
		classes.setDescription("sadfs");
		classes.setEarlyMinute(100);
		classes.setShortName("a");
		classes.setHaveRest(false);
		classes.setLateMinute(100);
		classes.setName("ads");
		classes.setOffDutyCheck(true);
		AttendanceClassesDetail detail = new AttendanceClassesDetail();
//		AttendanceClasses classes1 = entityManager.find(AttendanceClasses.class,4);
		AttendanceClasses classes1 = new AttendanceClasses();
		classes1.setId(4l);
		classes1.setName("sadf");
		classes1.setShortName("1");
		detail.setBeginAcross(false);
		detail.setBeginMinute(100);
//		detail.setBeginTime("11:00");
		detail.setEndAcross(false);
		detail.setEndMinute(12);
//		detail.setEndTime("12:00");
//		detail.setType(ClassesDetailType.normal);
//		classes1.getTimeList().add(detail);
		List<AttendanceClassesDetail> list = classes1.getTimeList();
		for (AttendanceClassesDetail detail1 : list) {
			System.out.println(detail1.getId());
		}
		List<AttendanceClassesDetail> list1 = new ArrayList<>();
		detail.setClasses(classes1);
		list1.add(detail);
		classes.setTimeList(list1);

		classesRepository.save(classes);
//
//		tx.commit();
//		session.close();
	}

	@Test
	@Transactional
	public void testGroupSave() {
		AttendanceGroup group = new AttendanceGroup();
		group.setName("test1");
		group.setHolidayRest(false);

		AttendanceClasses classes1 = new AttendanceClasses();
		classes1.setId(13l);
		AttendanceClasses classes2 = new AttendanceClasses();
		classes2.setId(14l);
		HashSet<AttendanceClasses> classes = new HashSet<>();
		classes.add(classes1);
		classes.add(classes2);

		group.setClasses(classes);
		List<Long> workList = new ArrayList<>();
		workList.add(0l);
		workList.add(0l);
		workList.add(0l);
		workList.add(0l);
		workList.add(0l);
		workList.add(13l);
		workList.add(14l);
		group.setWorkDayList(workList);

		Set<String> dates = new HashSet<>();
		Date date1 = new Date();
		Date date2 = new Date(1511884800000l);
		dates.add("2017-12-12");
		dates.add("2017-12-11");
		group.setSpecialOffDate(dates);

		Set<SpecialDate> specialDates = new HashSet<>();
		SpecialDate specialDate1 = new SpecialDate();
		specialDate1.setClassId(13l);
		specialDate1.setDate(date2);
		specialDate1.setGroup(group);
		SpecialDate specialDate2 = new SpecialDate();
		specialDate2.setClassId(14l);
		specialDate2.setDate(date2);
		specialDate2.setGroup(group);
		specialDates.add(specialDate1);
		specialDates.add(specialDate2);
		group.setSpecialOnDate(specialDates);

		Set<HrmResource> resources = new HashSet<>();
		HrmResource resource1 = new HrmResource();
		resource1.setId(3252l);
//		resource1.setGroup(group);
		HrmResource resource2 = new HrmResource();
		resource2.setId(3250l);
		resource2.setGroup(group);
		resources.add(resource1);
//		resources.add(resource2);
		group.setResources(resources);
		groupRepository.save(group);
	}

	@Test
	@Transactional
	public void testGroupUpdate() {
		AttendanceGroup group = new AttendanceGroup();
		group.setId(11l);
		group.setName("test1 update");
		Set<SpecialDate> specialDates = new HashSet<>();
		SpecialDate specialDate1 = new SpecialDate();
		specialDate1.setClassId(13l);
		specialDate1.setDate(new Date());
		specialDate1.setGroup(group);
		specialDates.add(specialDate1);
		group.setSpecialOnDate(specialDates);

		Set<HrmResource> resources = new HashSet<>();
		HrmResource resource1 = new HrmResource();
		resource1.setId(3247l);
		resources.add(resource1);
		// 要测试是否在同一个事务里 setNUll 再set resource 才会执行
		group.setResources(null);
		group.setResources(resources);


		groupRepository.save(group);

//		HrmResource resource = new HrmResource();
//		resource.setId();
	}

	@Test
	@Transactional
	public void testGroupDelete() {
//		delete from ehr_group_classes where group_id=?
//		Hibernate: delete from ehr_special_date where id=?
//		Hibernate: delete from ehr_special_date where id=?
//		Hibernate: delete from ehr_attendance_group where id=?
		groupRepository.delete(10l);
	}
}
