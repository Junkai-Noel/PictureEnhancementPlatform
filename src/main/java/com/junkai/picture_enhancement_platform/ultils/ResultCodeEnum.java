package com.junkai.picture_enhancement_platform.ultils;


import lombok.Getter;

@Getter
public enum ResultCodeEnum {

    SUCCESS(200, "success"),
    USERNAME_ERROR(501, "usernameError"),
    PASSWORD_ERROR(503, "passwordError"),
    NOT_LOGIN(504, "notLogin"),
    TOKEN_NOT_FOUND(505, "tokenNotFound"),
    USERNAME_USED(506, "userNameUsed"),
    USERNAME_NOTFOUND(507, "usernameNotFound"),
    PASSWORD_NULL(508, "passwordNull"),;



    private final Integer code;
    private final String message;


    ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}