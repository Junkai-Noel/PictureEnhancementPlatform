package com.junkai.picture_enhancement_platform.service.impls;

import com.junkai.picture_enhancement_platform.POJO.ModelParameterEntity;
import com.junkai.picture_enhancement_platform.service.interfaces.ModelService;
import com.junkai.picture_enhancement_platform.ultils.commandBuilder.CondaEnvActivator;
import com.junkai.picture_enhancement_platform.ultils.commandBuilder.RealESRGANCommandBuilder;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.Objects;

@Slf4j
@Service
public class RealESRGANServiceImpl implements ModelService {


    @Value("${inputPath}")
    private String inputPath;
    @Value("${realESRGAN.paths.outputPath}")
    private String outputPath;
    @Value("${conda.env}")
    private String condaEnv;

    private final RealESRGANCommandBuilder realESRGANCommandBuilder;
    private final CondaEnvActivator condaEnvActivator;

    public RealESRGANServiceImpl(RealESRGANCommandBuilder realESRGANCommandBuilder, CondaEnvActivator condaEnvActivator) {
        this.realESRGANCommandBuilder = realESRGANCommandBuilder;
        this.condaEnvActivator = condaEnvActivator;
    }


    /**
     * 图片处理服务逻辑
     * <p>调用代理方法来统一处理</p>
     *
     * @param file 图片文件
     * @param data 执行cmd构建所需的模型参数
     * @return 构建好的command
     */
    @Override
    public File processLocalImage(@NotNull MultipartFile file, ModelParameterEntity data) {
        String filename = Objects.requireNonNull(file.getOriginalFilename()).replaceAll(" ", "");
        File outputFile = new File(inputPath + filename);
        try {
            file.transferTo(outputFile);

            String command = condaEnvActivator.buildCondaEnv() +
                    condaEnv +
                    " && " +
                    realESRGANCommandBuilder.buildCommand(data, filename);
            log.info("执行命令{}", command);
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command("cmd.exe", "/c", command);
            Process process = processBuilder.start();
            // 获取输出流
            BufferedReader outputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            // 读取输出流
            String line;
            while ((line = outputReader.readLine()) != null) {
                log.info(line);
            }
            outputReader.close();
            // 等待进程结束并获取退出码
            int exitCode = process.waitFor();
            // 退出码非零，抛出异常
            if (exitCode != 0) {
                log.error("Command execution failed with exit code: {}", exitCode);
                throw new RuntimeException("Image processing failed.");
            }
        } catch (IOException | InterruptedException | RuntimeException e) {
            log.error(e.getMessage());
        } finally {
            if (outputFile.delete())
                log.info("图片缓存已删除");
        }
        String filenameHead = filename.substring(0, filename.lastIndexOf("."));
        return Paths.get(outputPath + filenameHead + "_out.jpg").toFile();
    }
}