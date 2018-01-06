package com.victory.sys.service;

import com.google.common.collect.Sets;
import com.victory.hrm.entity.HrmResource;
import com.victory.hrm.service.HrmResourceService;
import com.victory.sys.entity.Resource;
import com.victory.sys.entity.Role;
import com.victory.sys.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by ajkx
 * Date: 2017/8/29.
 * Time:14:21
 */
@Service
public class ShiroService {

    public Set<String> findRoles(User user) {
        Set<Role> roles = user.getRoles();
        Set<String> list = new HashSet<>();
        for (Role role : roles) {
            if(!role.getAvailable()){
                continue;
            }
            list.add(role.getName());
        }
        return list;
    }

    public Set<String> findPermissions(User user) {
        Set<Role> roles = user.getRoles();
        Set<String> permissions = Sets.newHashSet();
        for(Role role : roles) {
            if(!role.getAvailable()){
                continue;
            }
            Set<Resource> resources = role.getResources();

            for (Resource resource : resources) {
                permissions.add(resource.getPermission());
            }
        }
        return permissions;
    }
}
