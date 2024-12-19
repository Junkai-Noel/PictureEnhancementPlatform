package com.junkai.picture_enhancement_platform.aop;


import com.junkai.picture_enhancement_platform.exception.PlatformException;
import com.junkai.picture_enhancement_platform.ultils.ErrorResponse;
import com.junkai.picture_enhancement_platform.ultils.Result;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

/**
 * 全局异常处理
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final String detail = "以下为异常信息,详细错误原因请查看控制台或本地日志";


    /**
     * 处理自抛异常
     *
     * @param ex      PlatformException自定义异常类
     * @param request WebRequest
     * @return Result
     */
    @ExceptionHandler(PlatformException.class)
    public Result<?> handleImageException(@NotNull PlatformException ex, @NotNull WebRequest request) {

        Throwable cause = ex.getCause();

        ErrorResponse errorResponse = new ErrorResponse.Builder()
                .withExceptionName(ex.getClass().getSimpleName())
                .withMessage(ex.getMessage())
                .withPath(request.getDescription(false))
                .build();
        String causeMessage = cause != null ? cause.getMessage() : "没有原因";

        log.error("异常“{}”的详细堆栈信息：\n{}", ex.getClass().getSimpleName(), causeMessage);
        return new Result.Builder<>().code(ex.getErrorCode())
                .msg(detail)
                .data(errorResponse)
                .build();
    }

    /**
     * 处理意料外异常
     *
     * @param ex      Exception
     * @param request WebRequest
     * @return Result
     */
    @ExceptionHandler(Exception.class)
    public Result<?> handleException(@NotNull Exception ex, @NotNull WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse.Builder()
                .withExceptionName(ex.getClass().getSimpleName())
                .withMessage(ex.getMessage())
                .withPath(request.getDescription(false))
                .build();
        StringBuilder stackTrace = new StringBuilder();
        for (StackTraceElement element : ex.getStackTrace()) {
            stackTrace.append(element.toString()).append("\n");
        }
        log.error("异常“{}”堆栈信息：{}", ex.getMessage(), stackTrace);
        return new Result.Builder<>()
                .msg(detail)
                .code(500)
                .data(errorResponse)
                .build();
    }
}