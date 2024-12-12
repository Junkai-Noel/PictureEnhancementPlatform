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
    public String buildCommand(ModelParameterEntity data,String filename ) {
        RealESRGANParameterEntity realESRGANParameterEntity = (RealESRGANParameterEntity) data;
        return RealESRGANParameters.HEAD.getValue()+
                RealESRGANParameters.INPUT_HEAD.getValue()+
                inputPath+ filename+" "+
                RealESRGANParameters.OUTPUT_HEAD.getValue()+
                realESRGANOutputPath+ filename+" "+
                RealESRGANParameters.SCALE.getValue()+
                realESRGANParameterEntity.getScale()+" "+
                RealESRGANParameters.MODEL_HEAD.getValue()+
                getModel(data)+
                RealESRGANParameters.FORMAT_HEAD.getValue()+
                realESRGANParameterEntity.getFormat();
    }

    @Override
    String getModel(@NotNull ModelParameterEntity modelParameterEntity) {
        RealESRGANParameterEntity realESRGANParameterEntity = (RealESRGANParameterEntity) modelParameterEntity;
        if(StringUtils.isEmpty(realESRGANParameterEntity.getModel()))
            return RealESRGANParameters.MODEL_DEFAULT.getValue();
        return switch (realESRGANParameterEntity.getModel()){
            case "realESRNET" -> RealESRGANParameters.MODEL_REALESRNET.getValue();
            case "anime" -> RealESRGANParameters.MODEL_ANIME.getValue();
            default -> RealESRGANParameters.MODEL_DEFAULT.getValue();
        };
    }
}
