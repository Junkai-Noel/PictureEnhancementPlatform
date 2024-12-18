package com.junkai.picture_enhancement_platform.ultils.commandBuilder;

import com.junkai.picture_enhancement_platform.ultils.modelParameter.CondaParameters;
import org.springframework.stereotype.Component;

@Component
public class CondaEnvActivator {
    public String buildCondaEnv() {
        return CondaParameters.CONDA_ACTIVATE.getValue();
    }
}
