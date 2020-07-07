package com.evostar.model;

public enum MsgCodeEnum {
    PASSWORD_ERROR(300, "密码错误"),
    ACCOUNT_ERROR(301, "账号不存在"),
    ACCOUNT_EMPTY(302, "账号不能为空"),
    PASSWORD_EMPTY(303, "密码不能为空"),
    ACCOUNT_REGISTERED(304, "账号已被注册"),
    REGISTERED_FAILED(305, "注册失败"),
    DATA_NONE(306, "数据不存在"),
    OPERATION_FAILED(307, "操作失败")
    ;

    private int code;
    private String msg;

    private MsgCodeEnum(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}