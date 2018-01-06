package com.victory.hrm.service;

import com.victory.common.service.BaseService;
import com.victory.hrm.entity.HrmStatus;
import com.victory.hrm.repository.HrmResourceRepository;
import com.victory.hrm.entity.HrmDepartment;
import com.victory.hrm.entity.HrmResource;
import com.victory.hrm.entity.HrmSubCompany;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ajkx
 * Date: 2017/8/1.
 * Time:14:07
 */
@Service
public class HrmResourceService extends BaseService<HrmResource,Long>{


    private HrmResourceRepository getRepository() {
        return (HrmResourceRepository) baseRepository;
    }

    /**
     * 根据分部搜索，可搜索全部或在职
     * @param subCompany
     * @return
     */
    public List<HrmResource> findBySubCompany(HrmSubCompany subCompany) {
        return findBySubCompany(subCompany, false);
    }
    public List<HrmResource> findBySubCompany(HrmSubCompany subCompany, boolean isAll) {
        return getRepository().findBySubCompany(subCompany, isAll(isAll));
    }

    /**
     * 根据部门搜索，可搜索全部或在职
     * @param department
     * @return
     */
    public List<HrmResource> findByDepartment(HrmDepartment department) {
        return findByDepartment(department, false);
    }
    public List<HrmResource> findByDepartment(HrmDepartment department, boolean isAll) {
        return getRepository().findByDepartment(department, isAll(isAll));
    }
    public List<HrmResource> findByDepartment(long id) {
        return findByDepartment(id, false);
    }
    public List<HrmResource> findByDepartment(long id, boolean isAll) {
        return getRepository().findByDepartment(id, isAll(isAll));
    }

    /**
     * 根据部门列表搜索，可搜索全部或在职
     * @param departments
     * @return
     */
    public List<HrmResource> findByDepartmentIn(List<HrmDepartment> departments) {
        return findByDepartmentIn(departments, false);
    }
    public List<HrmResource> findByDepartmentIn(List<HrmDepartment> departments, boolean isAll) {
        return getRepository().findByDepartmentIn(departments, isAll(isAll));
    }

    public List<HrmResource> findByIds(List<Long> ids) {
        return getRepository().findByIdIn(ids);
    }
    public List<HrmResource> findByName(Specification specification,Sort sort) {
        return getRepository().findAll(specification,sort);
    }

    /**
     * 判断是否合法ID
     * @param id
     * @return
     */
    public boolean checkID(long id) {
        if(getRepository().countById(id) > 0) return true;
        return false;
    }

    public boolean checkID(HrmResource resource) {
        if(resource == null) return false;
        if(resource.getId() == null) return false;
        if(getRepository().countById(resource.getId()) <= 0) return false;
        return true;
    }

    /**
     * 区分是否搜索全部或在职
     * @param isAll
     * @return
     */
    public HrmStatus[] isAll(boolean isAll) {
        if (isAll) {
            return HrmStatus.allStatus;
        } else {
            return HrmStatus.workStatus;
        }
    }
}
