package com.victory.common.web.controller;

import com.victory.common.domain.result.ExceptionMsg;
import com.victory.common.domain.result.Response;
import com.victory.common.exception.BaseException;
import com.victory.common.exception.ResourceNotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.persistence.EntityNotFoundException;

import static com.victory.common.domain.result.Response.error;
/**
 * Created by ajkx
 * Date: 2017/8/21.
 * Time:17:30
 */
@RestControllerAdvice
public class RestExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    //// TODO: 2017/11/22 可以使用ResponseEntity返回有状态码的响应，如github api
    @ExceptionHandler(BaseException.class)
    public Response handleBaseException(BaseException e) {
        logger.error(e.getMsg(),e);
        if(StringUtils.isNotBlank(e.getMsg())){
            return error(e.getCode(), e.getMsg());
        }else{
            return error(e.getCode());
        }
    }

    //未认证错误
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Response handleUnauthorized(UnauthorizedException e) {
        logger.error(e.getMessage(),e);
        return error(ExceptionMsg.UNAUTHORIZED);
    }

    @ExceptionHandler(LockedAccountException.class)
    public Response handleLockedAccount(LockedAccountException e) {
        logger.error(e.getMessage(), e);
        return error(ExceptionMsg.AccountExpiredOrFreezed);
    }

    //资源没找到404
    @ExceptionHandler({ResourceNotFoundException.class, EntityNotFoundException.class,NoHandlerFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response handleNotFound(RuntimeException e) {
        return error(ExceptionMsg.NOT_FOUND);
    }

    //登录错误
    @ExceptionHandler({UnknownAccountException.class, IncorrectCredentialsException.class})
    public Response handleFailLogin(RuntimeException e) {
        return error(ExceptionMsg.LoginNameOrPassWordError);
    }

    //url的传参错误,类型不符合,参数为空,媒体类型不支持
    @ExceptionHandler({TypeMismatchException.class, MissingServletRequestParameterException.class,HttpMediaTypeNotSupportedException.class,HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response handleTypeMisMatch(Exception e) {
        return error(ExceptionMsg.BAD_REQUEST);
    }

    //url传参错误,列入sort的排序字段
    @ExceptionHandler(PropertyReferenceException.class)
    public Response handlePropertyException(PropertyReferenceException e) {
        return error(ExceptionMsg.ParamError);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public Response handlerHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e) {
        return error(ExceptionMsg.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response handleException(Exception e) {
        logger.error(e.getMessage(), e);
        return error(ExceptionMsg.INTERNAL_SERVER_ERROR);
    }
}
