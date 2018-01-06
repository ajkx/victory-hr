package com.victory.hrm.repository;

import com.victory.common.repository.BaseRepository;
import com.victory.hrm.entity.HrmSubCompany;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/12/7.
 */
@Repository
public interface HrmSubCompanyRepository extends BaseRepository<HrmSubCompany,Long>{

    @Query(value = "from HrmSubCompany where parent = 0 and (cancel is null or cancel = 0)")
    List<HrmSubCompany> findRootSubCompany();

    List<HrmSubCompany> findByParent(HrmSubCompany subCompany);
}
