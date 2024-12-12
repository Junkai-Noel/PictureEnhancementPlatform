package com.junkai.picture_enhancement_platform.service.impls;

import com.junkai.picture_enhancement_platform.POJO.ModelParameterEntity;
import com.junkai.picture_enhancement_platform.service.interfaces.ModelService;
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

    private final RealESRGANCommandBuilder realESRGANCommandBuilder;

    public RealESRGANServiceImpl(RealESRGANCommandBuilder realESRGANCommandBuilder) {
        this.realESRGANCommandBuilder = realESRGANCommandBuilder;
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
            String command = realESRGANCommandBuilder.buildCommand(data,filename);
            Process process = Runtime.getRuntime().exec(command);
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
            if (outputFile.delete())
                log.info("图片缓存已删除");

            // 如果进程返回非零退出码，抛出异常
            if (exitCode != 0) {
                log.error("Command execution failed with exit code: {}", exitCode);
                throw new RuntimeException("Image processing failed.");
            }

        }catch (IOException | InterruptedException e){
            log.error(e.getMessage());
        }
        return Paths.get(outputPath + filename).toFile();
    }
}