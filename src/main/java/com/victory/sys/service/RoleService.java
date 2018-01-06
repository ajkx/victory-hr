package com.victory.sys.service;

import com.google.common.collect.Sets;
import com.victory.common.exception.BaseException;
import com.victory.common.service.BaseService;
import com.victory.sys.entity.Resource;
import com.victory.sys.entity.Role;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.Set;

/**
 * Created by ajkx
 * Date: 2017/8/29.
 * Time:11:25
 */
@Service
public class RoleService extends BaseService<Role,Long>{

    /**
     * 这里只给前端使用表单提交修改使用,在Service中限制了只修改部分字段
     * @param role
     * @return
     */
    @Override
    public Role update(Role role) {
        Role oldRole = findOne(role.getId());
        if(oldRole == null ) throw new BaseException("该角色不存在，更新失败！");
        if(oldRole.getSuper()) throw new BaseException("该角色为超级管理员，不可操作!");
        oldRole.setName(role.getName());
        oldRole.setDescription(role.getDescription());
        oldRole.setAvailable(role.getAvailable());
        oldRole.setResources(role.getResources());
        return super.update(oldRole);
    }

    @Override
    public void delete(Long id) {
        Role oldRole = findOne(id);
        if(oldRole == null ) throw new BaseException("该角色不存在，删除失败！");
        if(oldRole.getSuper()) throw new BaseException("该角色为超级管理员，不可操作!");
        super.delete(id);
    }
}
