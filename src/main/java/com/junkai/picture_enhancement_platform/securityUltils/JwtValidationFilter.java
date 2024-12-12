package com.junkai.picture_enhancement_platform.securityUltils;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;

@Slf4j
@Component
public class JwtValidationFilter extends OncePerRequestFilter {


    private final JwtProperties jwtProperties;
    private final UserDetailsManagerImpl userDetailsManagerImpl;


    public JwtValidationFilter(JwtProperties jwtProperties, UserDetailsManagerImpl userDetailsManagerImpl) {
        this.jwtProperties = jwtProperties;
        this.userDetailsManagerImpl = userDetailsManagerImpl;
    }

    /**
     * 该方法执行:
     * <p>1、token的认证</p>
     * <p>2、将认证用户存入Spring Security上下文</p>
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param filterChain FilterChain-Spring Security的过滤器链
     */
    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {
        try{
            String token = request.getHeader("Authorization");

        // 如果请求头中没有Authorization信息，或者Authorization以Bearer开头，则认为是匿名用户
        if (StringUtils.isBlank(token) || !token.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        // 去除 Bearer 前缀
        token = token.substring(7);
        //获取token解析对象
        Claims claims = jwtProperties.parseToken(token);
        //验证token是否过期
        if(!claims.getExpiration().before(new Date(System.currentTimeMillis()))){
            log.error("token过期");
        }
        //根据token中的用户名，获取数据库中的用户信息，并加载到Spring Security上下文中
        //spring Security会自动将用户存入上下文，但前提是基于Session或是Basic Authentication。如果使用jwt，需要手动将用户存入上下文
        String username = claims.getSubject();
        UserDetails user = userDetailsManagerImpl.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        }catch (Exception e){
            e.printStackTrace();
            //log.error("token认证发生错误，错误信息：{}",e.getMessage());
            SecurityContextHolder.clearContext();
        }
        filterChain.doFilter(request, response);
    }
}
