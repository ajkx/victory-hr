package com.victory.sys.service;

import com.victory.common.service.BaseService;
import com.victory.sys.entity.Resource;
import com.victory.sys.repository.ResourceRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by ajkx
 * Date: 2017/8/29.
 * Time:11:25
 */
@Service
public class ResourceService extends BaseService<Resource,Long>{

    private ResourceRepository getRepository() {
        return (ResourceRepository) baseRepository;
    }
    public List getTree() {
        List<Map<String, Object>> list = new ArrayList<>();
        List<Resource> modules = getRepository().findByType(Resource.ResourceType.module);
        for (Resource moduleResource : modules) {
            Map<String, Object> moduleMap = new HashMap<>();

            moduleMap.put("id", moduleResource.getId());
            moduleMap.put("name", moduleResource.getName());
            moduleMap.put("type", moduleResource.getType());
            List<Map<String, Object>> groupList = new ArrayList<>();

            List<Resource> groups = getRepository().findByParent(moduleResource, new Sort(Sort.Direction.DESC, "type"));
            for (Resource groupResource : groups) {
                Map<String, Object> groupMap = new HashMap<>();
                groupMap.put("id", groupResource.getId());
                groupMap.put("name", groupResource.getName());
                groupMap.put("type", groupResource.getType());

                List<Map<String, Object>> itemList = new ArrayList<>();
                List<Resource> items = getRepository().findByParent(groupResource, new Sort(Sort.Direction.DESC, "type"));
                for (Resource itemResource : items) {
                    Map<String, Object> itemMap = new HashMap<>();
                    itemMap.put("id", itemResource.getId());
                    itemMap.put("name", itemResource.getName());
                    itemMap.put("type", itemResource.getType());
                    groupList.add(itemMap);
                }
                groupList.add(groupMap);
            }
            moduleMap.put("children", groupList);
            list.add(moduleMap);
        }
        return list;
    }
}
