package com.junkai.picture_enhancement_platform.controller;


import com.junkai.picture_enhancement_platform.POJO.RealESRGANParameterEntity;
import com.junkai.picture_enhancement_platform.POJO.Waifu2xParameterEntity;
import com.junkai.picture_enhancement_platform.service.impls.Waifu2xServiceImpl;
import com.junkai.picture_enhancement_platform.service.interfaces.ModelService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
@RequestMapping("/image")
public class PictureController {


    private final Waifu2xServiceImpl waifu2XServiceImpl;
    private final ModelService realESRGANServiceImpl;


    public PictureController(Waifu2xServiceImpl waifu2XServiceImpl, ModelService realESRGANServiceImpl) {
        this.waifu2XServiceImpl = waifu2XServiceImpl;
        this.realESRGANServiceImpl = realESRGANServiceImpl;
    }

    @PostMapping("/waifu2x")
    public ResponseEntity<FileSystemResource> getImageForWaifu2x(@RequestPart("image") MultipartFile image,
                                                                 @RequestPart("data") Waifu2xParameterEntity data) {
        File file = waifu2XServiceImpl.processLocalImage(image, data);
        if (file.exists()) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename");
            return new ResponseEntity<>(new FileSystemResource(file), headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/Real_ESRGAN")
    public ResponseEntity<FileSystemResource> getImageForRealESRGAN(
            @RequestPart("image") MultipartFile image,
            @RequestPart("data") RealESRGANParameterEntity data) {
        File file = realESRGANServiceImpl.processLocalImage(image, data);
        String path = file.getAbsolutePath();
        String suffix = path.substring(path.lastIndexOf(".") + 1);//获取文件后缀
        if (file.exists()) {
            HttpHeaders headers = new HttpHeaders();
            switch (suffix) {
                case "jpg", "jpeg" -> headers.setContentType(MediaType.IMAGE_JPEG);
                case "png" -> headers.setContentType(MediaType.IMAGE_PNG);
            }
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename");
            return new ResponseEntity<>(new FileSystemResource(file), headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}