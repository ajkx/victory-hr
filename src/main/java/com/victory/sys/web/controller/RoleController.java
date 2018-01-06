package com.victory.sys.web.controller;

import com.victory.common.exception.BaseException;
import com.victory.common.web.controller.BaseController;
import com.victory.sys.entity.Role;
import com.victory.sys.entity.User;
import com.victory.sys.service.RoleService;
import com.victory.sys.service.UserService;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

/**
 * Created by ajkx
 * Date: 2017/9/16.
 * Time:11:40
 */
@RestController
@RequestMapping("/api/role")
public class RoleController extends BaseController<Role,Long>{

    public RoleController() {
        setResourceIdentity("sys:role");
    }

    private RoleService getService() {
        return (RoleService) baseService;
    }

    @GetMapping
    @RequiresPermissions("sys:role:view")
    public Page<Role> list(
            @And(value = {
                    @Spec(path = "name", spec = Like.class),
            }) Specification<Role> specification,
            @PageableDefault Pageable pageable) {
        return getService().findAll(specification, pageable);
    }

    @PostMapping("updateStatus")
    @RequiresPermissions("sys:role:update")
    public void updateStatus(@RequestBody Long id) {
        if (id == null) {
            throw new BaseException("405", "请求参数不合法!");
        }
        Role role = getService().findOne(id);
        if (role == null) {
            throw new BaseException("404", "角色不存在!");
        }
        Boolean available = role.getAvailable();
        if(available == null || !available){
            //锁定
            role.setAvailable(true);
        }else{
            //解锁
            role.setAvailable(false);
        }
        getService().update(role);
    }
}
