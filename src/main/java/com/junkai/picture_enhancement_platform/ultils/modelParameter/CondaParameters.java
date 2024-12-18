package com.junkai.picture_enhancement_platform.ultils.modelParameter;

import lombok.Getter;

@Getter
public enum CondaParameters {
    CONDA_ACTIVATE("conda activate ");
    private final String value;

    CondaParameters(String value) {
        this.value = value;
    }
}