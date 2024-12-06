package com.junkai.picture_enhancement_platform.aop;

import com.junkai.picture_enhancement_platform.POJO.ModelParameterEntity;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Objects;

@Slf4j
@Aspect
@Component
public class PreCommandHandler{

    @Value("${inputPath}")
    private String inputPath;


    @Pointcut("execution(public String com.junkai.picture_enhancement_platform.service.ModelService.processLocalImage(..))")
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
        Object[] args = joinPoint.getArgs();
        MultipartFile file = (MultipartFile) args[0];
        ModelParameterEntity data = (ModelParameterEntity) args[1];
        String filename = Objects.requireNonNull(file.getOriginalFilename()).replaceAll(" ", "");
        File outputFile = new File(inputPath + filename);
        try {
            // 将文件保存到本地
            file.transferTo(outputFile);

            // 获取方法的目标对象
            String command = (String) joinPoint.proceed();  // 执行目标方法并获取命令字符串

            // 执行命令
            Process process = Runtime.getRuntime().exec(command);

            // 获取输出流
            BufferedReader outputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            // 读取输出流
            String line;
            while ((line = outputReader.readLine()) != null) {
                log.info(line);
            }

            // 读取错误流
            while ((line = errorReader.readLine()) != null) {
                log.info(line);
            }


            outputReader.close();
            errorReader.close();

            // 等待进程结束并获取退出码
            int exitCode = process.waitFor();
            if (outputFile.delete())
                log.info("图片缓存已删除");

            // 如果进程返回非零退出码，抛出异常
            if (exitCode != 0) {
                log.error("Command execution failed with exit code: {}", exitCode);
                throw new RuntimeException("Image processing failed.");
            }

        } catch (Exception e) {
            log.error("Image processing failed", e);
            throw new RuntimeException("Image processing failed", e);
        }
        return "Success";  // 返回成功状态
    }
}