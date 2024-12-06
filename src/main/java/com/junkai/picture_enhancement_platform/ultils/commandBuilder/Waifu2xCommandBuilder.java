package com.junkai.picture_enhancement_platform.ultils.commandBuilder;


import com.junkai.picture_enhancement_platform.POJO.ModelParameterEntity;
import com.junkai.picture_enhancement_platform.POJO.Waifu2xParameterEntity;
import com.junkai.picture_enhancement_platform.ultils.modelParameter.Waifu2xParameters;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class Waifu2xCommandBuilder extends CommandBuilder {

    @Override
    public String buildCommand(ModelParameterEntity data,String filename) {
        Waifu2xParameterEntity waifu2xParameterEntity = (Waifu2xParameterEntity) data;
        return Waifu2xParameters.HEAD.getValue() +
                Waifu2xParameters.INPUT_HEADER.getValue() +
                inputPath +
                filename + " " +
                Waifu2xParameters.MODEL_HEADER.getValue() +
                Waifu2xParameters.MODEL_NOISE_SCALE.getValue() +
                Waifu2xParameters.MODEL_SCALE_RADIO.getValue() +
                waifu2xParameterEntity.getScale_radio() +" "+
                Waifu2xParameters.MODEL_NOISE_LEVEL.getValue() +
                waifu2xParameterEntity.getNoise_level()  +" "+
                Waifu2xParameters.MODEL_DIRECTION_HEADER.getValue() +
                getModel(waifu2xParameterEntity) +
                Waifu2xParameters.OUTPUT_HEADER.getValue() +
                waifu2xOutputPath +
                filename;
    }

    @Override
    public String getModel(@NotNull ModelParameterEntity modelParameterEntity){
        Waifu2xParameterEntity waifu2xParameterEntity = (Waifu2xParameterEntity)modelParameterEntity;
        return switch (waifu2xParameterEntity.getModel()){
            case "photo" -> Waifu2xParameters.MODEL_PHOTO.getValue();
            case "anime_rgb" -> Waifu2xParameters.MODEL_ANIME_STYLE_ART_RGB.getValue();
            case "cunet" -> Waifu2xParameters.MODEL_CUNET.getValue();
            case "ukbench" -> Waifu2xParameters.MODEL_UKBENCH.getValue();
            case "upconv_7_anime" -> Waifu2xParameters.MODEL_UPCONV_7_ANIME_STYLE_ART_RGB.getValue();
            case "upconv_7_photo" -> Waifu2xParameters.MODEL_UPCONV_7_PHOTO.getValue();
            case "upresnet10" -> Waifu2xParameters.MODEL_UPRESNET10.getValue();
            default -> Waifu2xParameters.MODEL_ANIME_STYLE_ART.getValue();
        };
    }
}