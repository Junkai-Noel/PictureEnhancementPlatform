package com.junkai.picture_enhancement_platform.controller;


import com.junkai.picture_enhancement_platform.POJO.ModelParameterEntity;
import com.junkai.picture_enhancement_platform.POJO.Waifu2xParameterEntity;
import com.junkai.picture_enhancement_platform.service.Waifu2xServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/image")
public class PictureController {


    private final Waifu2xServiceImpl waifu2XServiceImpl;

    public PictureController(Waifu2xServiceImpl waifu2XServiceImpl) {
        this.waifu2XServiceImpl = waifu2XServiceImpl;
    }

    @PostMapping("/waifu2x")
    public String getImageForWaifu2x(@RequestPart("image") MultipartFile image,
                                     @RequestPart("data")Waifu2xParameterEntity data) {
        return waifu2XServiceImpl.processLocalImage(image,data);
    }

    @PostMapping("Real_ESRGAN")
    public String getImageForRealESRGAN(@RequestBody MultipartFile image, ModelParameterEntity data) {
        return waifu2XServiceImpl.processLocalImage(image,data);
    }
}