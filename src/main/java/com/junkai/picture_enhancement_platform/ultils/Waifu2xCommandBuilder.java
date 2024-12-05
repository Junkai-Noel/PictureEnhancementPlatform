package com.junkai.picture_enhancement_platform.ultils;


import com.junkai.picture_enhancement_platform.POJO.ModelParameterEntity;
import com.junkai.picture_enhancement_platform.POJO.Waifu2xParameterEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Waifu2xCommandBuilder {

    @Value("${waifu2x.inputPath}")
    private String inputPath;
    @Value("${waifu2x.outputPath}")
    private String outputPath;

    public String BuildWaifu2xCommand(String filename, ModelParameterEntity data) {
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
                outputPath +
                filename;
    }
    private String getModel(Waifu2xParameterEntity waifu2xParameterEntity){
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
