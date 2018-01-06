package com.victory.sys.service;

import com.victory.common.service.BaseService;
import com.victory.common.utils.TokenGenerator;
import com.victory.hrm.entity.HrmResource;
import com.victory.sys.entity.User;
import com.victory.sys.entity.UserToken;
import com.victory.sys.repository.UserTokenRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by ajkx
 * Date: 2017/8/29.
 * Time:14:28
 */
@Service
public class UserTokenService extends BaseService<UserToken,Long>{

    //12小时后过期
    private final static int EXPIRE = 3600 * 12;

    private UserTokenRepository getRepository() {
        return (UserTokenRepository) baseRepository;
    }


    public UserToken findByToken(String token) {
        return getRepository().findByToken(token);
    }

    public UserToken findByUser(User user) {
        return getRepository().findByUser(user);
    }

    public UserToken createToken(User user) {
        String token = TokenGenerator.generateValue();

        //当前时间
        Date now = new Date();
        //过期时间
        Date expireTime = new Date(now.getTime() + EXPIRE * 1000);

        UserToken userToken = getRepository().findByUser(user);
        if (userToken == null) {
            userToken = new UserToken();
            userToken.setUser(user);
            userToken.setToken(token);
            userToken.setExpireTime(expireTime);
            userToken.setUpdateTime(now);

            save(userToken);
        }else{
            userToken.setUpdateTime(now);
            userToken.setToken(token);
            userToken.setExpireTime(expireTime);

            update(userToken);
        }
        return userToken;
    }

}
