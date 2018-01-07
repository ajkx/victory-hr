package com.victory.common.web.vo;

import com.victory.attendance.entity.OverTimeRecord;
import com.victory.hrm.entity.HrmDepartment;
import com.victory.hrm.entity.HrmResource;
import com.victory.hrm.service.HrmResourceService;
import net.kaczmarzyk.spring.data.jpa.domain.Conjunction;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/26.
 */
public class SearchVo {

    private List<Long> resources;

    private List<Long> deps;

    private boolean isAll;

    public List<Long> getResources() {
        return resources;
    }

    public void setResources(List<Long> resources) {
        this.resources = resources;
    }

    public List<Long> getDeps() {
        return deps;
    }

    public void setDeps(List<Long> deps) {
        this.deps = deps;
    }

    public boolean isAll() {
        return isAll;
    }

    public void setAll(boolean all) {
        isAll = all;
    }

    public List<HrmDepartment> getDep(List<Long> ids) {
        if(ids == null || ids.size() == 0) return null;
        List<HrmDepartment> result = new ArrayList<>();
        for (Long id : ids) {
            HrmDepartment department = new HrmDepartment();
            department.setId(id);
            result.add(department);
        }
        return result;
    }

    public List<HrmResource> getRes(List<Long> ids) {
        if(ids == null || ids.size() == 0) return null;
        List<HrmResource> result = new ArrayList<>();
        for (Long id : ids) {
            HrmResource resource = new HrmResource();
            resource.setId(id);
            result.add(resource);
        }
        return result;
    }

    public <T> Specification<T> getSpec(Specification<?> specification, HrmResourceService resourceService) {
        List<HrmResource> resources = this.resources == null ? null : getRes(this.resources);
        List<HrmDepartment> deps = this.deps == null ? null : getDep(this.deps);
        boolean isAll = this.isAll;

        // 再添加搜索条件
        ArrayList list = new ArrayList();
        list.add(specification);

        if((resources != null && resources.size() > 0) || (deps != null && deps.size() > 0)){
            Specification resourceSpec = new Specification<T>() {
                @Override
                public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    if (resources != null && resources.size() > 0) {
                        Predicate p1 = root.get("resource").in(resources);
                        predicates.add(p1);
                    }
                    if (deps != null && deps.size() > 0) {
                        Predicate p1 = root.get("resource").get("department").in(deps);
                        Predicate p2 = root.get("resource").get("status").in(resourceService.isAll(isAll));
                        Predicate p3 = criteriaBuilder.and(p1, p2);
                        predicates.add(p3);
                    }
                    return criteriaBuilder.or(predicates.toArray(new Predicate[predicates.size()]));
                }
            };
            list.add(resourceSpec);
        } else {
            Specification resourceSpec = new Specification<T>() {
                @Override
                public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    Predicate p = root.get("resource").get("status").in(resourceService.isAll(isAll));
                    return p;
                }
            };
            list.add(resourceSpec);
        }
        Conjunction conjunction = new Conjunction(list);
        return conjunction;
    }
}
