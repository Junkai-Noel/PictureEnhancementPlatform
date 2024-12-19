package com.junkai.picture_enhancement_platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableAspectJAutoProxy
@SpringBootApplication
@EnableWebMvc
public class PictureEnhancementPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(PictureEnhancementPlatformApplication.class, args);
    }

}
