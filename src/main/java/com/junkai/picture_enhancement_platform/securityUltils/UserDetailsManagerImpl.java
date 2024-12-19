package com.junkai.picture_enhancement_platform.securityUltils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.junkai.picture_enhancement_platform.entity.User;
import com.junkai.picture_enhancement_platform.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserDetailsManagerImpl implements UserDetailsManager {


    private final UserMapper userMapper;
    private final PasswordUtils passwordUtils;


    public UserDetailsManagerImpl(UserMapper userMapper, PasswordUtils passwordUtils) {
        this.userMapper = userMapper;
        this.passwordUtils = passwordUtils;
    }

    @Override
    public void createUser(@NotNull UserDetails user) {
        User finalUser = (User) user;
        userMapper.insert(finalUser);
    }

    public int customDeleteUser(@NotNull User user) {
        return userMapper.deleteByUsername(user.getUsername());
    }


    public int customUpdateUser(@NotNull UserDetails user) {
        User finalUser = (User) user;
        return userMapper.updateByUsername(finalUser);
    }

    public int customChangePassword(@NotNull User user, String newPassword) {
        String username = user.getUsername();
        newPassword = passwordUtils.encodePassword(newPassword);
        return userMapper.updatePassword(username, newPassword);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        newPassword = passwordUtils.encodePassword(newPassword);
        userMapper.updatePassword(username, newPassword);
    }

    @Override
    public boolean userExists(String username) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, username);
        return userMapper.selectCount(queryWrapper) > 0;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, username);
        User loginUser = userMapper.selectOne(queryWrapper);
        if (loginUser == null) {
            throw new UsernameNotFoundException("未查询到用户：" + username);
        }
        return loginUser;
    }

    /**
     * 重写的方法，不符合需求不使用
     */
    @Override
    public void updateUser(UserDetails user) {

    }

    @Override
    public void deleteUser(String username) {

    }
}