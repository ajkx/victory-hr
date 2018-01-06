package com.victory.hrm.service;

import com.victory.common.service.BaseService;
import com.victory.hrm.repository.HrmDepartmentRepository;
import com.victory.hrm.entity.HrmDepartment;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ajkx
 * Date: 2017/8/1.
 * Time:14:07
 */
@Service
public class HrmDepartmentService extends BaseService<HrmDepartment,Long>{


    private HrmDepartmentRepository getRepository() {
        return (HrmDepartmentRepository) baseRepository;
    }

    public List<HrmDepartment> findByParent(HrmDepartment department) {
        return getRepository().findByParent(department);
    }
}
