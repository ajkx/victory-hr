
package com.victory.common.domain.result;

public enum ExceptionMsg {
	SUCCESS("0", "操作成功"),
	FAILED("1","操作失败"),
    ParamError("2", "参数错误"),
    RefuseEdit("3", "数据不可修改"),

    // 常用HTTP状态码
    BAD_REQUEST("400","Bad Request"),
    UNAUTHORIZED("401", "用户未认证"),
    NOT_FOUND("404","Not Found"),
    METHOD_NOT_ALLOWED("405","Request Method Not Supported"),
    NOT_ACCEPTABLE("406","Not Acceptable"),
    INTERNAL_SERVER_ERROR("500","服务器内部错误"),

    // 登录，注册相关
    NotLogin("1000","用户未登录"),
    LoginNameOrPassWordError("1001", "用户名或者密码错误！"),
    UserNameUsed("1001","用户已注册"),
    AccountExpiredOrFreezed("1002","用户账号失效或冻结"),
    TokenGenerateFailed("1003","token生成失败！"),

    // 员工相关
    resourceNotExist("h100", "员工不存在"),

    // 班次相关
    ClassesNameUsed("C100","名称已存在！"),

    // 考勤组相关
    GroupNameUsed("G100", "名称已存在！"),

    // 签卡相关
    recordRepeat("R100","签卡时间重复"),

    // 日期合法相关
    timeSequenceError("D100", "时间顺序错误")
    ;
   private ExceptionMsg(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    private String code;
    private String msg;
    
	public String getCode() {
		return code;
	}
	public String getMsg() {
		return msg;
	}

    
}

