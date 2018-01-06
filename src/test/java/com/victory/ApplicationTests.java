package com.victory;

import com.victory.attendance.repository.AttendanceClassesRepository;
import com.victory.attendance.entity.AttendanceClasses;
import com.victory.attendance.entity.AttendanceClassesDetail;
import com.victory.hrm.repository.HrmResourceRepository;
import com.victory.hrm.entity.HrmResource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@TransactionConfiguration(defaultRollback = false)
public class ApplicationTests {

	@Autowired
	private HrmResourceRepository resourceRepository;

	@Autowired
	private AttendanceClassesRepository classesRepository;

	@Autowired
	private EntityManager entityManager;
	@Test
	public void contextLoads() {

	}

	@Test
	public void testRepository() {
		HrmResource hrmResource = new HrmResource();
		hrmResource = resourceRepository.findOne(10l);
		System.out.println(hrmResource.getName());

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
}
