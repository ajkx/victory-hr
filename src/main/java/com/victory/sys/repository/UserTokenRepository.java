package com.victory.sys.repository;

import com.victory.common.repository.BaseRepository;
import com.victory.hrm.entity.HrmResource;
import com.victory.sys.entity.User;
import com.victory.sys.entity.UserToken;
import org.springframework.stereotype.Repository;

/**
 * Created by ajkx
 * Date: 2017/8/29.
 * Time:14:27
 */
@Repository
public interface UserTokenRepository extends BaseRepository<UserToken,Long> {

    UserToken findByUser(User user);

    UserToken findByToken(String token);
}
