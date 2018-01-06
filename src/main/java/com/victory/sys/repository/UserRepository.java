package com.victory.sys.repository;

import com.victory.common.repository.BaseRepository;
import com.victory.hrm.entity.HrmResource;
import com.victory.sys.entity.User;
import org.springframework.stereotype.Repository;

/**
 * Created by ajkx
 * Date: 2017/8/1.
 * Time:10:25
 */
@Repository
public interface UserRepository extends BaseRepository<User,Long> {

    User findByAccount(String account);
}
