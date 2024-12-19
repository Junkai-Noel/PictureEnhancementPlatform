package com.junkai.picture_enhancement_platform.service.interfaces;

import com.junkai.picture_enhancement_platform.POJO.ModelParameterEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;


public interface ModelService {
    File processLocalImage(MultipartFile file, ModelParameterEntity data);
}
