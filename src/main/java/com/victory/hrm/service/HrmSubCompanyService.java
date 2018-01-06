package com.victory.hrm.service;

import com.victory.common.service.BaseService;
import com.victory.hrm.repository.HrmSubCompanyRepository;
import com.victory.hrm.entity.HrmSubCompany;
import org.springframework.stereotype.Service;

/**
 * Created by ajkx
 * Date: 2017/8/1.
 * Time:14:07
 */
@Service
public class HrmSubCompanyService extends BaseService<HrmSubCompany,Long>{


    private HrmSubCompanyRepository getRepository() {
        return (HrmSubCompanyRepository) baseRepository;
    }

}
