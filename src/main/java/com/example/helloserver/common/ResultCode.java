package com.example.helloserver.common;

public enum ResultCode {
    // 业务错误码
    USER_HAS_EXISTED(400, "用户已存在"),
    USER_NOT_EXIST(404, "用户不存在"),
    PASSWORD_ERROR(400, "密码错误"),
    PARAM_ERROR(400, "参数错误"),
    UPDATE_ERROR(500, "更新失败"),
    DELETE_ERROR(500, "删除失败");

    private final Integer code;
    private final String msg;

    ResultCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}