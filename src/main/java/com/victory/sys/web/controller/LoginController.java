package com.victory.sys.web.controller;

import com.victory.common.domain.result.ExceptionMsg;
import com.victory.common.domain.result.Response;
import com.victory.common.utils.EncryptUtils;
import com.victory.sys.entity.User;
import com.victory.sys.entity.UserToken;
import com.victory.sys.service.ShiroService;
import com.victory.sys.service.UserService;
import com.victory.sys.service.UserTokenService;
import com.victory.sys.web.vo.UserVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ajkx
 * Date: 2017/9/16.
 * Time:10:31
 */
@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserTokenService tokenService;

    @Autowired
    private ShiroService shiroService;

    //登录认证接口
    @PostMapping("/auth")
    public Response login(@RequestBody UserVo userVo) throws Exception {
        String username = userVo.getUsername();
        String password = userVo.getPassword();

        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return Response.error(ExceptionMsg.METHOD_NOT_ALLOWED);
        }
        User user = userService.findByUsername(username);

        if (user == null || !EncryptUtils.Bit32(password).equals(user.getPassword())) {
            return Response.error(ExceptionMsg.LoginNameOrPassWordError);
        }

        if (!userService.checkUser(user)) {
            return Response.error(ExceptionMsg.AccountExpiredOrFreezed);
        }

        UserToken userToken = tokenService.createToken(user);
        return Response.ok(userToken);
    }

    @PostMapping("/logout")
    public void logout(){
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        UserToken userToken = tokenService.findByUser(user);
        tokenService.delete(userToken);
    }

    /**
     * 获取当前token的员工信息
     */
    @GetMapping("/userinfo")
    public Map getUserInfo() {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Map<String, Object> map = new HashMap<>();
        map.put("name", user.getName());
        map.put("workCode", user.getWorkCode());
        map.put("account", user.getAccount());
        map.put("createDate", user.getCreateDate());
        map.put("roles", user.getRoles());
        map.put("permissions", shiroService.findPermissions(user));
        return map;
    }

}
