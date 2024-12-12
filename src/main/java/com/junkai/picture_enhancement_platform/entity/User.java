package com.junkai.picture_enhancement_platform.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.junkai.picture_enhancement_platform.ultils.AuthoritiesTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class User implements UserDetails, Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private final String username;

    private final String password;

    private final String nickname;

    @TableField(value = "authorities", typeHandler = AuthoritiesTypeHandler.class)
    private final Set<GrantedAuthority> authorities;

    @TableField(exist = false)
    private final String role;

    @Version
    private Integer version;

    @TableLogic
    private Integer isDeleted;


    @Contract(pure = true)
    private User(@NotNull Builder builder) {
        this.username = builder.username;
        this.password = builder.password;
        this.nickname = builder.nickname;
        this.authorities = builder.authorities;
        this.role = builder.role;
    }

    public static class Builder {
        private String username;
        private String password;
        private String nickname;
        private Set<GrantedAuthority> authorities;
        private String role;

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder nickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public Builder authorities(Set<GrantedAuthority> authorities) {
            this.authorities = authorities;
            return this;
        }

        /**
         * 根据传入的role，构造用户的角色信息。先是创建一个新的Set<GrantedAuthority>实例，调用实例的add方法添加角色，然后调用Builder的authorities方法构造authorities
         * @param role 角色
         * @return Builder 实例
         */
        public Builder role(String role) {
            Set<GrantedAuthority> authorities = new HashSet<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
            return this.authorities(authorities);
        }

        public User build() {
            return new User(this);
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}