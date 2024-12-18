package com.junkai.picture_enhancement_platform.ultils.commandBuilder;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.junkai.picture_enhancement_platform.POJO.ModelParameterEntity;
import com.junkai.picture_enhancement_platform.POJO.RealESRGANParameterEntity;
import com.junkai.picture_enhancement_platform.ultils.modelParameter.RealESRGANParameters;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class RealESRGANCommandBuilder extends CommandBuilder {


    @Override
    public String buildCommand(ModelParameterEntity data, String filename) {
        RealESRGANParameterEntity realESRGANParameterEntity = (RealESRGANParameterEntity) data;

        String command = RealESRGANParameters.HEAD.getValue() +
                inferencePythonFilePath +
                RealESRGANParameters.MODEL_HEAD.getValue() +
                getModel(realESRGANParameterEntity) +
                RealESRGANParameters.INPUT_HEAD.getValue() +
                inputPath +
                RealESRGANParameters.OUTPUT_HEAD.getValue() +
                realESRGANOutputPath +
                RealESRGANParameters.SCALE.getValue() +
                realESRGANParameterEntity.getScale() +
                " " +
                RealESRGANParameters.FORMAT_HEAD.getValue() +
                realESRGANParameterEntity.getFormat() +
                " ";
        if (realESRGANParameterEntity.isFaceEnhance())
            command += RealESRGANParameters.FACE_ENHANCE_HEAD.getValue();
        if (StringUtils.equals(getModel(realESRGANParameterEntity), RealESRGANParameters.MODEL_TINY.getValue()))
            command += RealESRGANParameters.DENOISE_HEAD.getValue() +
                    realESRGANParameterEntity.getDenoise() +
                    " ";
        return command;
    }

    @Override
    String getModel(@NotNull ModelParameterEntity modelParameterEntity) {
        RealESRGANParameterEntity realESRGANParameterEntity = (RealESRGANParameterEntity) modelParameterEntity;
        if (StringUtils.isEmpty(realESRGANParameterEntity.getModel()))
            return RealESRGANParameters.MODEL_DEFAULT.getValue();
        return switch (realESRGANParameterEntity.getModel()) {
            case "realESRNET" -> RealESRGANParameters.MODEL_REALESRNET.getValue();
            case "anime" -> RealESRGANParameters.MODEL_ANIME.getValue();
            case "tiny" -> RealESRGANParameters.MODEL_TINY.getValue();
            default -> RealESRGANParameters.MODEL_DEFAULT.getValue();
        };
    }
}
