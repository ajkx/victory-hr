package com.victory.sys.web.controller;

import com.victory.common.domain.result.Response;
import com.victory.common.exception.BaseException;
import com.victory.common.web.controller.BaseController;
import com.victory.sys.entity.Role;
import com.victory.sys.entity.User;
import com.victory.sys.service.UserService;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.domain.NotEqual;
import net.kaczmarzyk.spring.data.jpa.domain.NotNull;
import net.kaczmarzyk.spring.data.jpa.web.annotation.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by ajkx
 * Date: 2017/8/30.
 * Time:13:35
 */
@RestController
@RequestMapping("/api/user")
public class UserController extends BaseController<User,Long> {

    public UserController() {
        this.create = false;
        this.delete = false;
        setResourceIdentity("sys:user");
    }

    private UserService getService() {

        return (UserService) baseService;
    }

    @GetMapping
    @RequiresPermissions("sys:user:view")
    public Page<User> list(
            @Conjunction( value = {
                @Or({
                    @Spec(params = "name",path = "name", spec = Like.class),
                    @Spec(params = "name",path = "shortName", spec = Like.class)
                    })
            },and = {
                    @Spec(path = "account",spec = NotNull.class,constVal = "true"),
                    @Spec(path = "account",spec = NotEqual.class,constVal = "")
            }) Specification<User> specification, @PageableDefault Pageable pageable) {
        //组合两个条件
//        Specification<User> specification = Specifications.where(specification1).and(specification2);
        Page<User> page = getService().findAll(specification, pageable);
        return page;
    }

    /**
     * 更新用户角色
     * @param user
     * @param roles
     */
    @PostMapping("updateRole")
    @RequiresPermissions("sys:user:update")
    public void updateRole(User user,Set<Role> roles) {
        if (user == null) {
            throw new BaseException("请求参数不合法!");
        }
        user.setRoles(roles);
        getService().update(user);
    }

    /**
     * 锁定用户或解锁用户
     * @param id
     */
    @PostMapping("updateStatus")
    @RequiresPermissions("sys:user:update")
    public void updateStatus(@RequestBody Long id) {
        if (id == null) {
            throw new BaseException("请求参数不合法!");
        }
        User user = getService().findOne(id);
        if (user == null) {
            throw new BaseException("该用户不存在!");
        }
        Integer locked = user.getLocked();
        if(locked == null || locked == 0){
            //锁定
            user.setLocked(1);
        }else{
            //解锁
            user.setLocked(0);
        }
        getService().update(user);
    }


}
