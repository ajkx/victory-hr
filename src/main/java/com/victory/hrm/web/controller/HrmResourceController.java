package com.victory.hrm.web.controller;

import com.victory.common.domain.result.Response;
import com.victory.common.web.controller.BaseController;
import com.victory.hrm.entity.HrmDepartment;
import com.victory.hrm.entity.HrmResource;
import com.victory.hrm.entity.HrmStatus;
import com.victory.hrm.entity.HrmSubCompany;
import com.victory.hrm.service.HrmDepartmentService;
import com.victory.hrm.service.HrmResourceService;
import com.victory.hrm.service.HrmSubCompanyService;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Or;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ajkx
 * Date: 2017/8/2.
 * Time:9:10
 */
@RestController
@RequestMapping("api/hrmresources")
public class HrmResourceController extends BaseController<HrmResource,Long>{

    @Autowired
    private HrmSubCompanyService subCompanyService;

    @Autowired
    private HrmDepartmentService departmentService;

    public HrmResourceController() {
        this.create = false;
        this.delete = false;
        setResourceIdentity("hrm:resource");
    }

    private HrmResourceService getService() {
        return (HrmResourceService) baseService;
    }

    /**
     * example : page=0&size=10&sort=id,asc&sort=sex,desc
     * @param pageable
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Page<HrmResource> list(
            @Or(value = {
                @Spec(path = "name",spec = Like.class),
                @Spec(path = "department.name", params = "dep", spec = Like.class)
            })
                    Specification<HrmResource> specification,
            @PageableDefault() Pageable pageable) {
        System.out.println(specification);
        System.out.println(pageable);
        return getService().findAll(specification,pageable);
    }

    /**
     * 名称搜索 和 是否搜索所有员工
     * @param name
     * @param isAll
     * @return
     */
    @RequestMapping("name")
    public @ResponseBody Response listByName(String name, Boolean isAll) {
        if(StringUtils.isEmpty(name)) return Response.ok(null);
        String[] arr = name.split(",");
        Specification specification = new Specification<HrmResource>() {
            @Override
            public Predicate toPredicate(Root<HrmResource> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                for(int i = 0; i < arr.length; i++) {
                    if(arr[i].equals("")) continue;
                    predicates.add(criteriaBuilder.like(root.get("name"),"%"+arr[i]+"%"));
                    predicates.add(criteriaBuilder.like(root.get("shortName"),"%"+arr[i]+"%"));
                    predicates.add(criteriaBuilder.like(root.get("department").get("name"),"%"+arr[i]+"%"));
                }
                Predicate result = criteriaBuilder.or(predicates.toArray(new Predicate[predicates.size()]));
                // 默认只搜索在职员工
                if (isAll == null || !isAll) {
                    result = criteriaBuilder.and(result,root.get("status").in(HrmStatus.workStatus));
                }
                return result;
            }
        };
        List resources = getResourceList(getService().findByName(specification, new Sort(Sort.Direction.ASC, "department.id")));
        return Response.ok(resources);
    }

    @RequestMapping("sub/{id}")
    public @ResponseBody Response listBySub(@PathVariable long id, boolean isAll) {
        HrmSubCompany subCompany = subCompanyService.findOne(id);
        List resources = null;
        if(subCompany != null){
            resources = getResourceList(getService().findBySubCompany(subCompany, isAll));
            return Response.ok(resources);
        }else{
            return Response.error("401", "参数不合法");
        }
    }

    @RequestMapping("dep/{id}")
    public @ResponseBody Response listByDep(@PathVariable long id,boolean child, boolean isAll) {
        HrmDepartment department = departmentService.findOne(id);
        List resources = null;
        if(department != null){
            resources = getResourceList(getService().findByDepartment(department, isAll));
            if (child) {
                List<HrmDepartment> departments = findTreeDep(department);
                List resources1 = getResourceList(getService().findByDepartmentIn(departments, isAll));
                resources.addAll(resources1);
            }
            return Response.ok(resources);
        }else{
            return Response.error("401", "参数不合法");
        }
    }

    public List<HrmDepartment> findTreeDep(HrmDepartment department) {
        List<HrmDepartment> list = new ArrayList();
        list.add(department);
        List<HrmDepartment> departments = departmentService.findByParent(department);
        for (HrmDepartment temp : departments) {
            list.addAll(findTreeDep(temp));
        }
        return list;
    }

    public List getResourceList(List<HrmResource> resources) {
        List<Map> mapList = new ArrayList<>();
        for (HrmResource resource : resources) {
            Map<String, Object> temp = new HashMap<>();
            temp.put("id", resource.getId());
            temp.put("name", resource.getName());
            String subCompany = "";
            if (resource.getSubCompany() != null) {
                subCompany = resource.getSubCompany().getName();
            }
            String department = "";
            if (resource.getSubCompany() != null) {
                department = resource.getDepartment().getName();
            }

            temp.put("subcompany", subCompany);
            temp.put("department", department);
            mapList.add(temp);
        }
        return mapList;
    }
}
