package com.junkai.picture_enhancement_platform.POJO;

import lombok.Data;

@Data
public class RealESRGANParameterEntity implements ModelParameterEntity {
    private int scale = 4;
    private String format = "auto";
    private String model;
    private boolean faceEnhance = false;
    private int denoise = 0;
}