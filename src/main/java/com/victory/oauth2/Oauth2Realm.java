package com.victory.oauth2;

import com.victory.hrm.entity.HrmResource;
import com.victory.hrm.service.HrmResourceService;
import com.victory.sys.entity.User;
import com.victory.sys.entity.UserToken;
import com.victory.sys.service.ShiroService;
import com.victory.sys.service.UserService;
import com.victory.sys.service.UserTokenService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Created by ajkx
 * Date: 2017/8/29.
 * Time:13:42
 */
public class Oauth2Realm extends AuthorizingRealm{

    @Autowired
    private ShiroService shiroService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserTokenService userTokenService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof OAuth2Token;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        User user = (User) principalCollection.getPrimaryPrincipal();

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(shiroService.findRoles(user));
        authorizationInfo.setStringPermissions(shiroService.findPermissions(user));
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String accessToken = (String) authenticationToken.getPrincipal();

        UserToken userToken = userTokenService.findByToken(accessToken);
        //将延迟加载的数据加载一下，防止后续取时报LazyInit错误
        if (userToken == null || userToken.getExpireTime().getTime() < System.currentTimeMillis()) {
            throw new IncorrectCredentialsException("token失效,请重新登录!");
        }
        User user = userToken.getUser();
        if (userToken.getUser() == null || !userService.checkUser(user)) {
            throw new LockedAccountException("账号失效");
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, accessToken, getName());
        return info;
    }
}
