package com.junkai.picture_enhancement_platform.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.jetbrains.annotations.NotNull;

public interface LayerAgent {
    void LayerPointcut() ;
    Object around(@NotNull ProceedingJoinPoint joinPoint) throws Throwable;
}
