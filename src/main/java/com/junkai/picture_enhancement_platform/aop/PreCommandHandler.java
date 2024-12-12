package com.junkai.picture_enhancement_platform.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class PreCommandHandler{

    @Value("${inputPath}")
    private String inputPath;
    @Value("${waifu2x.paths.outputPath}")
    private String waifu2xOutputPath;
    @Value("${realESRGAN.paths.outputPath}")
    private String realESRGANOutputPath;

    @Pointcut("execution(public String com.junkai.picture_enhancement_platform.service.interfaces.ModelService.processLocalImage(..))")
    public void commandBuilder(){}

    /**
     * 构建模型参数命令的代理方法：
     * <p>1、去除图片文件名中可能存在的空格，避免干扰命令执行</p>
     * <p>2、将图片存入临时文件夹temp中</p>
     * <p>3、执行方法，获取方法的返回值，即command指令</p>
     * <p>4、执行command指令，并完成后续处理</p>
     * @param joinPoint ProceedingJoinPoint
     * @return TargetClass return value
     */
    @Around("commandBuilder()")
    public Object around(@NotNull ProceedingJoinPoint joinPoint) throws Throwable {
        return null;
    }
}