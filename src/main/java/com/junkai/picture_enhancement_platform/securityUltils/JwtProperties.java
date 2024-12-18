package com.junkai.picture_enhancement_platform.securityUltils;

import com.junkai.picture_enhancement_platform.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Data
@Component
@ConfigurationProperties(prefix = "jwt.token")
public class JwtProperties {
    private long tokenExpiration;
    private String tokenSignKey;
    private static SecretKey key;


    /**
     * 将读取的字符串转换成密钥
     */
    @PostConstruct
    public void init() {
        key = Keys.hmacShaKeyFor(tokenSignKey.getBytes());
    }

    /**
     * 为每个token生成一个唯一的UUID作为KeyId
     *
     * @return UUID
     */
    public static @NotNull String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 生成token
     *
     * @return token String
     */
    public String generateToken(@NotNull User user) {
        String username = user.getUsername();
        return Jwts.builder()
                .header()
                .keyId(getUUID()) //token的唯一id
                .and()
                .subject(username)
                .expiration(new Date(System.currentTimeMillis() + tokenExpiration))
                .issuedAt(new Date(System.currentTimeMillis() + 604800000))
                .signWith(key)
                .compact();
    }

    /**
     * 解析token
     *
     * @param token String
     * @return Claims，token的解析对象
     */
    public Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}