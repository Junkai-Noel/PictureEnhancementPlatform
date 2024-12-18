package com.junkai.picture_enhancement_platform.service.impls;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.junkai.picture_enhancement_platform.entity.User;
import com.junkai.picture_enhancement_platform.mapper.UserMapper;
import com.junkai.picture_enhancement_platform.securityUltils.JwtProperties;
import com.junkai.picture_enhancement_platform.securityUltils.PasswordUtils;
import com.junkai.picture_enhancement_platform.securityUltils.UserDetailsManagerImpl;
import com.junkai.picture_enhancement_platform.service.interfaces.UserService;
import com.junkai.picture_enhancement_platform.ultils.Result;
import com.junkai.picture_enhancement_platform.ultils.ResultCodeEnum;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    private final UserDetailsManagerImpl userDetailsManagerImpl;
    private final JwtProperties jwtProperties;
    private final PasswordUtils passwordUtils;
    private final UserMapper userMapper;


    public UserServiceImpl(UserDetailsManagerImpl userDetailsManagerImpl, JwtProperties jwtProperties, PasswordUtils passwordUtils, UserMapper userMapper) {
        this.userDetailsManagerImpl = userDetailsManagerImpl;
        this.jwtProperties = jwtProperties;
        this.passwordUtils = passwordUtils;
        this.userMapper = userMapper;
    }

    /**
     * 用户创建业务逻辑：
     * <p>1、首先判断用户名是否已经存在于数据库（本来放在userDetailsManagerImpl中判断，但可能会导致service方法里的创建的新user对象没有被使用，浪费资源，所以放在创建新的user对象之前判断</p>
     * <p>2、根据传入的user对象中的参数，构造数据库需要的user对象（新user对象）</p>
     * <p>3、调用userDetailsManager中重写的createUser方法执行插入操作</p>
     *
     * @param user 前端传入的user对象
     * @return 响应信息
     */
    public Result<?> register(@NotNull User user) {
        if (userDetailsManagerImpl.userExists(user.getUsername())) {
            return new Result.Builder<>()
                    .resultCodeEnum(ResultCodeEnum.USERNAME_USED)
                    .build();
        }
        //如果没有指定用户角色，默认分配USER
        User finalUser;
        finalUser = new User.Builder()
                .username(user.getUsername())
                .password(passwordUtils.encodePassword(user.getPassword()))
                .nickname(user.getNickname())
                .role(StringUtils.isBlank(user.getRole()) ? "USER" : user.getRole())
                .build();
        userDetailsManagerImpl.createUser(finalUser);
        return Result.ok();
    }

    public Result<?> getAllUser() {
        List<User> users;
        try {
            users = userMapper.selectAll();
        } catch (NullPointerException e) {
            return new Result.Builder<>()
                    .msg("用户记录为空")
                    .build();
        }
        return Result.ok(users);
    }

    public Result<?> updateUser(User user) {
        int rows = userDetailsManagerImpl.customUpdateUser(user);
        return rows > 0 ?
                Result.ok(rows) :
                new Result.Builder<>().resultCodeEnum(ResultCodeEnum.USERNAME_NOTFOUND).build();
    }

    public Result<?> deleteUser(User user) {
        int rows = userDetailsManagerImpl.customDeleteUser(user);
        return rows > 0 ?
                Result.ok(rows) :
                new Result.Builder<>().resultCodeEnum(ResultCodeEnum.USERNAME_NOTFOUND).build();
    }

    public Result<?> findByUsername(String username) {
        User user = userMapper.selectByUsername(username);
        return user == null
                ? new Result.Builder<>().resultCodeEnum(ResultCodeEnum.USERNAME_NOTFOUND).build()
                : Result.ok(user);
    }

    public Result<?> changePassword(@NotNull User user) {
        User DBUser = (User) userDetailsManagerImpl.loadUserByUsername(user.getUsername());
        int rows = userDetailsManagerImpl.customChangePassword(DBUser, user.getPassword());
        return rows > 0
                ? Result.ok()
                : new Result.Builder<>().resultCodeEnum(ResultCodeEnum.USERNAME_NOTFOUND).build();
    }

    public Result<?> checkPassword(@NotNull User user) {
        User DBUser = (User) userDetailsManagerImpl.loadUserByUsername(user.getUsername());
        return passwordUtils.matches(user.getPassword(), DBUser.getPassword())
                ? Result.ok()
                : new Result.Builder<>().resultCodeEnum(ResultCodeEnum.PASSWORD_ERROR).build();
    }

    public Result<?> login(@NotNull User user) {
        if (!userDetailsManagerImpl.userExists(user.getUsername())) {
            return new Result.Builder<>().resultCodeEnum(ResultCodeEnum.USERNAME_NOTFOUND).build();
        }
        User DBUser = (User) userDetailsManagerImpl.loadUserByUsername(user.getUsername());
        if (passwordUtils.matches(user.getPassword(), DBUser.getPassword())) {
            String token = jwtProperties.generateToken(DBUser);
            return Result.ok(token);
        } else
            return new Result.Builder<>().resultCodeEnum(ResultCodeEnum.PASSWORD_ERROR).build();
    }
}