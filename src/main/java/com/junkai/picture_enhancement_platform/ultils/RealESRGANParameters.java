package com.junkai.picture_enhancement_platform.ultils;


import lombok.Getter;

@Getter
public enum RealESRGANParameters {
    HEAD("realesrgan-ncnn-vulkan.exe ");

    private final String value;

    RealESRGANParameters(String value) {
        this.value=value;
    }
}
