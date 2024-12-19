package com.junkai.picture_enhancement_platform.controller;


import com.junkai.picture_enhancement_platform.POJO.RealESRGANParameterEntity;
import com.junkai.picture_enhancement_platform.exception.PlatformException;
import com.junkai.picture_enhancement_platform.service.interfaces.ModelService;
import com.junkai.picture_enhancement_platform.ultils.Result;
import com.junkai.picture_enhancement_platform.ultils.ResultCodeEnum;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
@RequestMapping("/image")
public class PictureController {


    private final ModelService realESRGANServiceImpl;

    public PictureController(ModelService realESRGANServiceImpl) {
        this.realESRGANServiceImpl = realESRGANServiceImpl;
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

    @PostMapping("/test")
    public Result<?> a() {
        throw new PlatformException(ResultCodeEnum.USERNAME_ERROR);
    }
}