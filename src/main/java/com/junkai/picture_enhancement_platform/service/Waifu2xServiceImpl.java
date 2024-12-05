package com.junkai.picture_enhancement_platform.service;

import com.junkai.picture_enhancement_platform.POJO.ModelParameterEntity;
import com.junkai.picture_enhancement_platform.ultils.Waifu2xCommandBuilder;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Objects;

@Slf4j
@Service
public class Waifu2xServiceImpl implements ModelService{

    @Value("${waifu2x.inputPath}")
    private String inputPath;

    private final Waifu2xCommandBuilder waifu2xCommandBuilder;

    public Waifu2xServiceImpl(Waifu2xCommandBuilder waifu2xCommandBuilder) {
        this.waifu2xCommandBuilder = waifu2xCommandBuilder;
    }

    @Override
    public String processLocalImage(@NotNull MultipartFile file, ModelParameterEntity data) {
        String filename = Objects.requireNonNull(file.getOriginalFilename()).replaceAll(" ", "");
        File outputFile = new File(inputPath+ filename);
        try {
            // 将文件保存到本地
            file.transferTo(outputFile);

            // 构建命令
            String command = waifu2xCommandBuilder.BuildWaifu2xCommand(filename,data);

            // 执行命令
            Process process = Runtime.getRuntime().exec(command);

            // 获取输出流
            BufferedReader outputReader = new BufferedReader(new InputStreamReader(process.getInputStream(), "GBK"));
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream(), "GBK"));

            // 读取输出流
            String line;
            while ((line = outputReader.readLine()) != null) {
                log.info(line);
            }

            // 读取错误流
            while ((line = errorReader.readLine()) != null) {
                log.error(line);
            }

            outputReader.close();
            errorReader.close();

            // 等待进程结束并获取退出码
            int exitCode = process.waitFor();
            if(outputFile.delete())
                log.debug("图片缓存已删除");
            if (exitCode != 0) {
                log.error("Command execution failed with exit code: {}", exitCode);
                throw new RuntimeException("Image processing failed.");
            }
        } catch (Exception e) {
            log.error("Image processing failed", e);
            throw new RuntimeException("Image processing failed", e);
        }

        return "Success";
    }
}
