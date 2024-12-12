package com.junkai.picture_enhancement_platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication
public class PictureEnhancementPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(PictureEnhancementPlatformApplication.class, args);
    }

}
