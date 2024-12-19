package com.junkai.picture_enhancement_platform.exception;

import com.junkai.picture_enhancement_platform.ultils.ResultCodeEnum;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
public class PlatformException extends RuntimeException {

    private final int errorCode;

    public PlatformException(@NotNull ResultCodeEnum codeEnum) {
        super(codeEnum.getMessage());
        this.errorCode = codeEnum.getCode();
    }

    public PlatformException(@NotNull ResultCodeEnum codeEnum,Throwable cause){
        super(codeEnum.getMessage(),cause);
        this.errorCode = codeEnum.getCode();
    }
}