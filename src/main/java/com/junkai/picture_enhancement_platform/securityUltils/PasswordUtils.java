package com.junkai.picture_enhancement_platform.securityUltils;

import lombok.Getter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Getter
@Component
public class PasswordUtils {
    //使用BCrypt算法进行密码加密，工作因子默认
    private final PasswordEncoder encoder = new BCryptPasswordEncoder();


    //加密方法
    public String encodePassword(String rawPassword) {
        return encoder.encode(rawPassword);
    }

    //验证方法
    public  boolean matches(String rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }
}