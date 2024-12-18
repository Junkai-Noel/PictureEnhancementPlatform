package com.junkai.picture_enhancement_platform.ultils.commandBuilder;

import com.junkai.picture_enhancement_platform.POJO.ModelParameterEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;

public abstract class CommandBuilder {
    @Value("${inputPath}")
    protected String inputPath;
    @Value("${waifu2x.paths.outputPath}")
    protected String waifu2xOutputPath;
    @Value("${realESRGAN.paths.outputPath}")
    protected String realESRGANOutputPath;
    @Value("${inferencePythonFile.path}")
    String inferencePythonFilePath;

    abstract public String buildCommand( ModelParameterEntity data,String filename);

    abstract String getModel(@NotNull ModelParameterEntity modelParameterEntity);
}
