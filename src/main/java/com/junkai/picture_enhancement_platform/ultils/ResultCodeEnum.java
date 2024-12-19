package com.junkai.picture_enhancement_platform.ultils;


import lombok.Getter;

@Getter
public enum ResultCodeEnum {

    SUCCESS(200, "成功"),
    BAD_REQUEST(400,"请求参数错误"),
    UNAUTHORIZED(401,"请求未授权"),
    FORBIDDEN(403,"禁止访问"),
    NOT_FOUND(404,"资源未找到"),
    INTERNAL_SERVER_ERROR(500,"服务器内部错误"),
    USERNAME_ERROR(501, "用户名错误"),
    PASSWORD_ERROR(503, "密码错误"),
    NOT_LOGIN(504, "未登录"),
    TOKEN_NOT_FOUND(505, "找不到token"),
    USERNAME_USED(506, "用户名已使用"),
    PASSWORD_NULL(507, "输入的密码为空"),
    IMAGE_PROCESS_FAIL(600,"图片处理出错"),
    IO_FAIL(508,"文件读写出错");

    private final int code;
    private final String message;


    ResultCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}