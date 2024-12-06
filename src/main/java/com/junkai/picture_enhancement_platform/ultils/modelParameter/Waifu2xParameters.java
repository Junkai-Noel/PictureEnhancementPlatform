package com.junkai.picture_enhancement_platform.ultils.modelParameter;

import lombok.Getter;


@Getter
public enum Waifu2xParameters {
    HEAD("waifu2x-caffe-cui.exe "),
    INPUT_HEADER("-i "),
    OUTPUT_HEADER("-o "),
    MODEL_HEADER("-m "),
    MODEL_NOISE_SCALE("noise_scale "),
    MODEL_SCALE_RADIO("--scale_radio "),
    MODEL_NOISE_LEVEL("--noise_level "),
    MODEL_DIRECTION_HEADER("--model_dir "),
    MODEL_PHOTO("E:/super-resolution/waifu2x-caffe/models/photo "),
    MODEL_ANIME_STYLE_ART("E:/super-resolution/waifu2x-caffe/models/anime_style_art "),
    MODEL_ANIME_STYLE_ART_RGB("E:/super-resolution/waifu2x-caffe/models/anime_style_art_rgb "),
    MODEL_CUNET("E:/super-resolution/waifu2x-caffe/models/cunet "),
    MODEL_UKBENCH("E:/super-resolution/waifu2x-caffe/models/ukbench "),
    MODEL_UPCONV_7_ANIME_STYLE_ART_RGB("E:/super-resolution/waifu2x-caffe/models/upconv_7_anime_style_art_rgb "),
    MODEL_UPCONV_7_PHOTO("E:/super-resolution/waifu2x-caffe/models/upconv_7_photo "),
    MODEL_UPRESNET10("E:/super-resolution/waifu2x-caffe/models/upresnet10 ");

    private final String value;

    Waifu2xParameters(String value) {
        this.value= value;
    }
}