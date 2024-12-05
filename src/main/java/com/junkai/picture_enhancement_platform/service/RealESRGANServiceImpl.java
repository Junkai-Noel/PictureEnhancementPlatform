package com.junkai.picture_enhancement_platform.service;

import com.junkai.picture_enhancement_platform.POJO.ModelParameterEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class RealESRGANServiceImpl implements ModelService{

    @Override
    public String processLocalImage(MultipartFile file, ModelParameterEntity data) {
        return "";
    }
}
