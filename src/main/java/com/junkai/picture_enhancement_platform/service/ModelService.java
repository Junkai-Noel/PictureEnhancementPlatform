package com.junkai.picture_enhancement_platform.service;

import com.junkai.picture_enhancement_platform.POJO.ModelParameterEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ModelService {
    String processLocalImage(MultipartFile file, ModelParameterEntity data);
}
