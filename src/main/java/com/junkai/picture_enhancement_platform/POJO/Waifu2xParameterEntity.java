package com.junkai.picture_enhancement_platform.POJO;


import lombok.Data;

@Data
public class Waifu2xParameterEntity implements ModelParameterEntity {
    private float scale_radio=2;
    private int noise_level=0;
    private String model;
}