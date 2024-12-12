package com.junkai.picture_enhancement_platform.POJO;

import lombok.Data;

@Data
public class RealESRGANParameterEntity implements ModelParameterEntity{
    private int scale=4;
    private String format="png";
    private String model;
}
