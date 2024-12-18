package com.junkai.picture_enhancement_platform.service.impls;

import com.junkai.picture_enhancement_platform.ultils.commandBuilder.CommandBuilder;
import com.junkai.picture_enhancement_platform.ultils.commandBuilder.CondaEnvActivator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class RealESRGANServiceImplTest {


    @Autowired
    private CommandBuilder realESRGANCommandBuilder;
    @Autowired
    private CondaEnvActivator condaEnvActivator;


    @Test
    void processLocalImage() throws InterruptedException, IOException {
        String command = "conda activate Real-ESRGAN && python E:/PycharmProjects/PythonProject/Real-ESRGAN/inference_realesrgan.py -n RealESRGAN_x4plus -i E:/images/testImage/photo/4.jpg -o E:/images/real-ESRGAN";
        Process process = Runtime.getRuntime().exec(command);
        process.waitFor();
    }
}