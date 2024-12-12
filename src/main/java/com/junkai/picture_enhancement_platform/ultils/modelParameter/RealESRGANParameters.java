package com.junkai.picture_enhancement_platform.ultils.modelParameter;


import lombok.Getter;

@Getter
public enum RealESRGANParameters {
    HEAD("realesrgan-ncnn-vulkan.exe "),
    INPUT_HEAD("-i "),
    OUTPUT_HEAD("-o "),
    SCALE("-s "),
    MODEL_HEAD("-n "),
    FORMAT_HEAD("-f "),
    MODEL_DEFAULT("realesrgan-x4plus "),
    MODEL_REALESRNET("realesrnet-x4plus"),
    MODEL_ANIME("realesrgan-x4plus-anime ");


    private final String value;

    RealESRGANParameters(String value) {
        this.value=value;
    }
}
