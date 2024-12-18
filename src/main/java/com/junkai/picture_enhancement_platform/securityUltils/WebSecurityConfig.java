package com.junkai.picture_enhancement_platform.securityUltils;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@Configuration
public class WebSecurityConfig {


    private final JwtProperties jwtProperties;
    private final UserDetailsManagerImpl userDetailsManagerImpl;
    private final PasswordUtils passwordUtils;


    public WebSecurityConfig(JwtProperties jwtProperties, UserDetailsManagerImpl userDetailsManagerImpl, PasswordUtils passwordUtils) {
        this.jwtProperties = jwtProperties;
        this.userDetailsManagerImpl = userDetailsManagerImpl;
        this.passwordUtils = passwordUtils;
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/**")
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                )
                .formLogin(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                //在过滤链中，于UsernamePasswordAuthenticationFilter过滤器前添加jwt过滤器
                .addFilterBefore(new JwtValidationFilter(jwtProperties, userDetailsManagerImpl), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}