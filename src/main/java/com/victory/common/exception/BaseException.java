package com.victory.common.exception;

import com.victory.common.domain.result.ExceptionMsg;

/**
 * Created by ajkx
 * Date: 2017/8/30.
 * Time:14:08
 */
public class BaseException extends RuntimeException{

    private String code = "1";

    private String msg;

    public BaseException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public BaseException(ExceptionMsg e) {
        this.code = e.getCode();
        this.msg = e.getMsg();
    }

    public BaseException(Throwable cause, String msg) {
        super(msg,cause);
        this.msg = msg;
    }

    public BaseException(String code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public BaseException(Throwable cause, String code, String msg) {
        super(msg,cause);
        this.code = code;
        this.msg = msg;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
