package com.victory.sys.web.controller;

import com.victory.common.web.controller.BaseController;
import com.victory.sys.entity.Resource;
import com.victory.sys.entity.Role;
import com.victory.sys.service.ResourceService;
import com.victory.sys.service.RoleService;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by ajkx
 * Date: 2017/9/16.
 * Time:11:40
 */
@RestController
@RequestMapping("/api/permission")
public class ResourceController extends BaseController<Resource,Long>{

    public ResourceController() {
        this.create = false;
        this.update = false;
        this.delete = false;
        setResourceIdentity("sys:permission");
    }

    private ResourceService getService() {
        return (ResourceService) baseService;
    }


    @GetMapping
    @RequiresPermissions("sys:permission:view")
    public Page<Resource> list(
            @And(value = {
                    @Spec(path = "name", spec = Like.class),
            }) Specification<Resource> specification,
            @PageableDefault Pageable pageable) {
        return getService().findAll(specification, pageable);
    }

    /**
     * 返回tree型的数据
     * @return
     */
    @GetMapping("/tree")
    @RequiresPermissions("sys:permission:view")
    public List tree() {
        return getService().getTree();
    }
}
