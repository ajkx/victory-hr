package com.victory.hrm.repository;

import com.victory.common.repository.BaseRepository;
import com.victory.hrm.entity.HrmDepartment;
import com.victory.hrm.entity.HrmResource;
import com.victory.hrm.entity.HrmStatus;
import com.victory.hrm.entity.HrmSubCompany;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by ajkx
 * Date: 2017/8/1.
 * Time:10:25
 */
@Repository
public interface HrmResourceRepository extends BaseRepository<HrmResource,Long> {

    /**
     * statuses 不可为null，即搜索全部要载入所有的status枚举
     * @param subCompany
     * @param statuses
     * @return
     */
    @Query("from HrmResource where department = ?1 and status in ?2")
    List<HrmResource> findBySubCompany(HrmSubCompany subCompany, HrmStatus[] statuses);

    @Query("from HrmResource where department = ?1 and status in ?2")
    List<HrmResource> findByDepartment(HrmDepartment department, HrmStatus[] statuses);

    @Query("from HrmResource where department = ?1 and status in ?2")
    List<HrmResource> findByDepartment(long id, HrmStatus[] statuses);

    @Query("from HrmResource where department in ?1 and status in ?2")
    List<HrmResource> findByDepartmentIn(List<HrmDepartment> departments, HrmStatus[] statuses);

    /**
     * 找出在职的并且日期处于合同开始与结束之间
     * @param date
     * @return
     */
    @Query(value = "from HrmResource where createDate < ?1 and status in (0,1,2,3)")
    List<HrmResource> findOnWokingAndbetweenEntryDate(Date date);

    List<HrmResource> findByIdIn(List<Long> ids);

    long countById(long id);
}
