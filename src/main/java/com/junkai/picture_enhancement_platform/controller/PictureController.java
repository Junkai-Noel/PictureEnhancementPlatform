package com.junkai.picture_enhancement_platform.controller;


import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/image")
public class PictureController {
    @PostMapping
    public String getImage(@RequestBody MultipartFile image) {
        String filename = image.getOriginalFilename();
        long size = image.getSize();
        try {
            image.transferTo(new java.io.File("E:/基于SSM的图片高清化平台/Picture_enhancement_platform/src/main/resources/images/" + filename));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "路径"+filename+"大小"+size+"KB";
    }
}