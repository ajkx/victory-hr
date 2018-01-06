package com.victory.hrm.repository;

import com.victory.common.repository.BaseRepository;
import com.victory.hrm.entity.HrmDepartment;
import com.victory.hrm.entity.HrmSubCompany;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/12/7.
 */
@Repository
public interface HrmDepartmentRepository extends BaseRepository<HrmDepartment,Long>{

    /**
     * 找出分部下最顶层的部门列表
     * @param subCompany
     * @return
     */
    @Query(value = "from HrmDepartment where parent = 0 and (cancel is null or cancel = 0) and subCompany = ?1")
    List<HrmDepartment> findRootDepBySubCompany(HrmSubCompany subCompany);

    List<HrmDepartment> findByParent(HrmDepartment department);

    List<HrmDepartment> findBySubCompany(HrmSubCompany subCompany);
}
