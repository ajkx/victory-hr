package com.victory.sys.repository;

import com.victory.common.repository.BaseRepository;
import com.victory.sys.entity.Role;
import com.victory.sys.entity.UserToken;
import org.springframework.stereotype.Repository;

/**
 * Created by ajkx
 * Date: 2017/8/29.
 * Time:11:23
 */
@Repository
public interface RoleRepository extends BaseRepository<Role,Long> {


}
