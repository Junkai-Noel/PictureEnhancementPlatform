package com.junkai.picture_enhancement_platform.service;

import com.junkai.picture_enhancement_platform.POJO.ModelParameterEntity;
import com.junkai.picture_enhancement_platform.ultils.commandBuilder.Waifu2xCommandBuilder;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Slf4j
@Service
public class Waifu2xServiceImpl implements ModelService{


    private final Waifu2xCommandBuilder waifu2xCommandBuilder;

    public Waifu2xServiceImpl(Waifu2xCommandBuilder waifu2xCommandBuilder) {
        this.waifu2xCommandBuilder = waifu2xCommandBuilder;
    }

    /**
     * 图片处理服务逻辑
     * <p>调用代理方法来统一处理</p>
     * @param file 图片文件
     * @param data 执行cmd构建所需的模型参数
     * @return 构建好的command
     */
    @Override
    public String processLocalImage(@NotNull MultipartFile file, ModelParameterEntity data) {
        String filename = Objects.requireNonNull(file.getOriginalFilename()).replaceAll(" ", "");
        return waifu2xCommandBuilder.buildCommand(data,filename) ;
    }
}
