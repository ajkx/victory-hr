package com.victory.hrm.service;

import com.victory.hrm.repository.HrmDepartmentRepository;
import com.victory.hrm.repository.HrmSubCompanyRepository;
import com.victory.hrm.entity.HrmDepartment;
import com.victory.hrm.entity.HrmSubCompany;
import com.victory.hrm.web.vo.OrganizationTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ajkx
 * Date: 2017/8/1.
 * Time:14:07
 */
@Service
public class OrganizationService{

    @Autowired
    private HrmSubCompanyRepository subCompanyRepository;

    @Autowired
    private HrmDepartmentRepository departmentRepository;


    public List<OrganizationTree> getSubTree(List<HrmSubCompany> list) {
        List<OrganizationTree> organizationTrees = new ArrayList<>();
        for (HrmSubCompany subCompany : list) {
            //排除封存
            if(subCompany.getCancel() != null && subCompany.getCancel()) continue;

            OrganizationTree tree = new OrganizationTree();
            tree.setId(subCompany.getId());
            tree.setName(subCompany.getName());
            tree.setType("sub");
            List<OrganizationTree> subList = getSubTree(subCompanyRepository.findByParent(subCompany));
            List<OrganizationTree> depList = getDepTree(departmentRepository.findRootDepBySubCompany(subCompany));

            //让部门放在前面
            depList.addAll(subList);
            tree.setChildren(depList);
            organizationTrees.add(tree);
        }
        return organizationTrees;
    }

    public List<OrganizationTree> getDepTree(List<HrmDepartment> list) {
        List<OrganizationTree> organizationTrees = new ArrayList<>();
        for (HrmDepartment department : list) {
            //排除封存
            if(department.getCancel() != null && department.getCancel()) continue;

            OrganizationTree tree = new OrganizationTree();
            tree.setId(department.getId());
            tree.setName(department.getName());
            tree.setType("dep");
            List<OrganizationTree> depList = getDepTree(departmentRepository.findByParent(department));

            tree.setChildren(depList);
            organizationTrees.add(tree);
        }
        return organizationTrees;
    }

    public List<HrmSubCompany> findRootSubCompany() {
        return subCompanyRepository.findRootSubCompany();
    }

    public List<HrmSubCompany> findSubByParent(HrmSubCompany subCompany) {
        return subCompanyRepository.findByParent(subCompany);
    }

    public List<HrmDepartment> findDepBySubCompany(HrmSubCompany subCompany) {
        return departmentRepository.findBySubCompany(subCompany);
    }

    public List<HrmDepartment> findDepByParent(HrmDepartment department) {
        return departmentRepository.findByParent(department);
    }

    public List<HrmDepartment> findRootDepBySubCompany(HrmSubCompany subCompany) {
        return departmentRepository.findRootDepBySubCompany(subCompany);
    }
}
