package com.victory.common.web.controller;

import com.victory.common.domain.result.ExceptionMsg;
import com.victory.common.domain.result.Response;
import com.victory.common.entity.AbstractEntity;
import com.victory.common.entity.BaseEntity;
import com.victory.common.exception.BaseException;
import com.victory.common.service.BaseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.victory.common.domain.result.Response.error;
/**
 * Created by ajkx
 * Date: 2017/9/15.
 * Time:16:26
 */
public abstract class BaseController<T extends AbstractEntity,ID extends Serializable> {

    @Autowired
    protected BaseService<T,ID> baseService;

    protected PermissionList permissionList;

    //是否开放对应的方法
    protected boolean create = true;
    protected boolean update = true;
    protected boolean delete = true;

    public void setResourceIdentity(String resourceIdentity) {
        if (!StringUtils.isEmpty(resourceIdentity)) {
            permissionList = PermissionList.newPermissionList(resourceIdentity);
        }
    }

    protected boolean checkEntity(T entity, Response response){
        return true;
    }

    protected boolean delCheck(ID id, Response response) {
        return true;
    }
    /**
     * 根据ID查询
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    @ResponseBody
    public Response view(@PathVariable("id") ID id) {
        if (permissionList != null) {
                this.permissionList.assertHasViewPermission();
        }
        return Response.ok(baseService.packEntity(baseService.findOne(id)));
    }

    /**
     * 创建实体
     * @param entity
     * @param result
     */
    @PostMapping
    @ResponseBody
    public Response create(@RequestBody @Valid T entity, BindingResult result) {
        if(!create) return Response.error(ExceptionMsg.METHOD_NOT_ALLOWED);
        if (permissionList != null) {
            this.permissionList.assertHasViewPermission();
        }
        Response response = new Response();
        //统一的参数判断
        if(!hasError(entity, result)){
            response = Response.error("400", "参数错误");
            return response;
        }
        //自定义的逻辑判断
        boolean flag = checkEntity(entity, response);
        if (flag) {
            response = Response.ok();
            baseService.save(entity);
        }
        return response;
    }

    /**
     * 更新实体
     * @param entity
     * @param result
     */
    @PutMapping
    @ResponseBody
    public Response update(@RequestBody @Valid T entity, BindingResult result) {
        if(!update) return Response.error(ExceptionMsg.METHOD_NOT_ALLOWED);
        if (permissionList != null) {
            this.permissionList.assertHasUpdatePermission();
        }
        Response response = new Response();
        //统一的参数判断
        if(!hasError(entity, result)){
            response = Response.error("400", "参数错误");
            return response;
        }
        //自定义的逻辑判断
        boolean flag = checkEntity(entity, response);
        if (flag) {
            baseService.update(entity);
            response = Response.ok();
        }
        return response;
    }

    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public Response delete(@PathVariable("id") ID id) {
        if(!delete) return Response.error(ExceptionMsg.METHOD_NOT_ALLOWED);
        if (permissionList != null) {
            this.permissionList.assertHasDeletePermission();
        }
        Response response = new Response();
        boolean flag = delCheck(id, response);
        if (flag) {
            baseService.delete(id);
            response = Response.ok();
        }
        return response;
    }

    protected boolean hasError(T t, BindingResult result) {
        if (t == null || result.hasErrors()) {
            return false;
//            throw new BaseException("400", "实体为空");
        }
        return true;
//      if(result.hasErrors()){
//            throw new BaseException("400", "参数不符");
//        }
    }

    protected Object pack(Page<T> page, Pageable pageable) {
        PageImpl<T> result = (PageImpl<T>) page;
        List<T> list = result.getContent();
        List newList = new ArrayList();
        for (T t : list) {
            newList.add(baseService.packEntity(t));
        }
        Map map = new HashMap();
        map.put("content", newList);
        map.put("totalElements", result.getTotalElements());
        return map;
    }
}
