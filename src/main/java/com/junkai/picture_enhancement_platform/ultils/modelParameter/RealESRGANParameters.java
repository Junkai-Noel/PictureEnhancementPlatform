package com.junkai.picture_enhancement_platform.ultils.modelParameter;


import lombok.Getter;

@Getter
public enum RealESRGANParameters {
    HEAD("python "),
    INPUT_HEAD("-i "),
    OUTPUT_HEAD("-o "),
    SCALE("-s "),
    MODEL_HEAD("-n "),
    FORMAT_HEAD("--ext "),
    DENOISE_HEAD("-d "),
    FACE_ENHANCE_HEAD("--face_enhance "),
    MODEL_DEFAULT("RealESRGAN_x4plus "),
    MODEL_REALESRNET("RealESRNet_x4plus "),
    MODEL_ANIME("RealESRGAN_x4plus_anime_6B "),
    MODEL_TINY("realesr-general-x4v3 ");


    private final String value;

    RealESRGANParameters(String value) {
        this.value = value;
    }
}
