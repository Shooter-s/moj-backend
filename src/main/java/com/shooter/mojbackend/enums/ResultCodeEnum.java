package com.shooter.mojbackend.enums;

/**
 * ClassName: ResultCodeEnum
 * Package: com.shooter.mojbackend.enums
 * Description:
 *
 * @Author:Shooter
 * @Create 2024/2/22 17:00
 * @Version 1.0
 */
public enum ResultCodeEnum {
    SUCCESS(0,"操作成功"),
    PARAMS_ERROR(40000, "请求参数错误"),
    NOT_LOGIN_ERROR(40100, "未登录"),
    NO_AUTH_ERROR(40101, "无权限"),
    NOT_FOUND_ERROR(40400, "请求数据不存在"),
    FORBIDDEN_ERROR(40300, "禁止访问"),
    SYSTEM_ERROR(50000, "系统内部异常"),
    OPERATION_ERROR(50001, "操作失败"),
    API_REQUEST_ERROR(50010, "接口调用失败");

    private Integer code;
    private String message;
    //枚举类要提供全参构造器和对应属性的get方法
    ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}