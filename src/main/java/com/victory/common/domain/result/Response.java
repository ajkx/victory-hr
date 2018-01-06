package com.victory.common.domain.result;



import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * Created by ajkx
 * Date: 2017/8/21.
 * Time:16:30
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response implements Serializable{
    private static final long serialVersionUID = 1L;

    //自定义的错误识别码
    private String code;

    //自定义的错误信息
    private String message;

    private Object data;

    public Response() {
        this.code = ExceptionMsg.SUCCESS.getCode();
        this.message = ExceptionMsg.SUCCESS.getMsg();
    }

    public Response(ExceptionMsg e) {
        this.code = e.getCode();
        this.message = e.getMsg();
    }

//    public Response(String code) {
//        this.code = code;
//        this.message = "";
//    }

    public Response(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public Response(Object data) {
        this.code = ExceptionMsg.SUCCESS.getCode();
        this.message = ExceptionMsg.SUCCESS.getMsg();
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public void setExceptionMsg(ExceptionMsg exceptionMsg) {
        this.code = exceptionMsg.getCode();
        this.message = exceptionMsg.getMsg();
    }

    @Override
    public String toString() {
        return "Response{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public static Response ok() {
        return new Response(ExceptionMsg.SUCCESS);
    }
    public static Response ok(Object data) {
        return new Response(data);
    }

    public static Response error(ExceptionMsg e) {
        return new Response(e);
    }

    public static Response error(String code) {
        return new Response(code);
    }

    public static Response error(String code, String message) {
        return new Response(code, message);
    }
}
