package com.victory.sys.repository;

import com.victory.common.repository.BaseRepository;
import com.victory.sys.entity.Resource;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ajkx
 * Date: 2017/8/29.
 * Time:11:23
 */
@Repository
public interface ResourceRepository extends BaseRepository<Resource,Long> {

    List<Resource> findByType(Resource.ResourceType resourceType);

    List<Resource> findByParent(Resource resource,Sort sort);
}
