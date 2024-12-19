package com.junkai.picture_enhancement_platform.ultils;

import lombok.Data;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import java.text.SimpleDateFormat;
import java.util.Date;


@Data
public class ErrorResponse {
    private String exceptionName;
    private String message;
    private String path;
    private String timestamp;

    @Contract(pure = true)
    private ErrorResponse(@NotNull Builder builder) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());

        this.message = builder.message;
        this.path = builder.path;
        this.timestamp = sdf.format(date);
        this.exceptionName = builder.exceptionName;
    }

    public static class Builder {
        private String message;
        private String path;
        private String exceptionName;

        public Builder withMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder withPath(String path) {
            this.path = path;
            return this;
        }
        public Builder withExceptionName(String exceptionName) {
            this.exceptionName = exceptionName;
            return this;
        }

        public ErrorResponse build() {
            return new ErrorResponse(this);
        }
    }
}
