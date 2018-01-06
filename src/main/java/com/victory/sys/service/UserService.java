package com.victory.sys.service;

import com.victory.common.service.BaseService;
import com.victory.hrm.entity.HrmStatus;
import com.victory.sys.entity.User;
import com.victory.sys.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * Created by ajkx
 * Date: 2017/8/29.
 * Time:11:25
 */
@Service
public class UserService extends BaseService<User,Long>{

    private UserRepository getRepository() {
        return (UserRepository) baseRepository;
    }

    public User findByUsername(String username) {
        return getRepository().findByAccount(username);
    }
    /**
     * 判断用户是否有效
     */
    public boolean checkUser(User user) {
        //判断CRM是否有账号
        if (StringUtils.isBlank(user.getAccount())) {
            return false;
        }
        //判断是否在职
        HrmStatus status = user.getStatus();
        if(status != HrmStatus.offical && status != HrmStatus.late_probation && status != HrmStatus.probation){
            return false;
        }
        if(user.getLocked() != null && user.getLocked() == 1){
            return false;
        }
        return true;
    }
}
