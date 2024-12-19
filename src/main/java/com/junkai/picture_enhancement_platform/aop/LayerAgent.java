package com.junkai.picture_enhancement_platform.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class LayerAgent {


    @Pointcut(" within(com.junkai.picture_enhancement_platform.controller.*) " +
            "|| within(com.junkai.picture_enhancement_platform.service.impls.*) " +
            "|| within(com.junkai.picture_enhancement_platform.mapper.*) ")
    public void LayerPointcut() {
    }

    /**
     * 代理类，代理Controller、Service、Mapper三层内的所有方法，并统一进行异常处理
     *
     * @param joinPoint ProceedingJoinPoint
     * @return TargetClass return value
     */

    @Around("LayerPointcut()")
    public Object around(@NotNull ProceedingJoinPoint joinPoint) throws Throwable {

        Signature signature = joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();
        String packageOnly = joinPoint.getTarget().getClass().getPackage().getName();
        long startTime = System.currentTimeMillis();
        Object result;

        log.info("方法所在包{}", packageOnly);
        log.info("方法{}执行", signature.getName());
        log.info("{}:方法参数：{}", signature.getName(), Arrays.toString(args));
        result = joinPoint.proceed();
        log.info("{}:用时{}ms", signature.getName(), System.currentTimeMillis() - startTime);
        return result;
    }
}