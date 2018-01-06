package com.victory.oauth2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.victory.common.domain.result.ExceptionMsg;
import com.victory.common.domain.result.Response;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

/**
 * Created by ajkx
 * Date: 2017/8/29.
 * Time:11:38
 */
public class Oauth2Filter extends AuthenticatingFilter{

    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        String token = getRequestToken((HttpServletRequest) servletRequest);
        if (StringUtils.isBlank(token)) {
            return null;
        }
        return new OAuth2Token(token);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        String token = getRequestToken((HttpServletRequest) servletRequest);

        if (StringUtils.isBlank(token)) {
            Response response = Response.error(ExceptionMsg.UNAUTHORIZED);
            ObjectMapper mapper = new ObjectMapper();
            String result = mapper.writeValueAsString(response);
            servletResponse.getWriter().print(result);
            return false;
        }
        return executeLogin(servletRequest, servletResponse);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue){
        return false;
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setContentType("application/json;charset=utf-8");
        //处理登录失败的异常
        Throwable throwable = e.getCause() == null ? e : e.getCause();
        ObjectMapper mapper = new ObjectMapper();
        Response responseInfo = Response.error("401","登录失败");
        try {
            String result = mapper.writeValueAsString(responseInfo);
            httpServletResponse.getWriter().print(result);
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        return false;
    }

    private String getRequestToken(HttpServletRequest request) {
        Enumeration<String> a = request.getHeaderNames();
        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)) {
            token = request.getParameter("token");
        }
        return token;
    }
}
