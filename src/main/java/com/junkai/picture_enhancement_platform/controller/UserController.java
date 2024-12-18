package com.junkai.picture_enhancement_platform.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.junkai.picture_enhancement_platform.entity.User;
import com.junkai.picture_enhancement_platform.service.impls.UserServiceImpl;
import com.junkai.picture_enhancement_platform.ultils.Result;
import com.junkai.picture_enhancement_platform.ultils.ResultCodeEnum;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {


    private final UserServiceImpl userServiceImpl;

    public UserController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    /**
     * 传入User对象，创建用户
     *
     * @param user User
     * @return 结果
     */
    @PostMapping
    public Result<?> register(@RequestBody User user) {
        return userServiceImpl.register(user);
    }

    @GetMapping
    public Result<?> getAllUsers() {
        return userServiceImpl.getAllUser();
    }

    @PutMapping
    public Result<?> updateUser(@RequestBody User user) {
        return userServiceImpl.updateUser(user);
    }

    @DeleteMapping
    public Result<?> deleteUser(@RequestBody User user) {
        return userServiceImpl.deleteUser(user);
    }

    @GetMapping("/{username}")
    public Result<?> getUser(@PathVariable String username) {
        return userServiceImpl.findByUsername(username);
    }

    /**
     * <p>使用参数：前端传入的user对象，其中的password为新password</p>
     * <p>假设在此方法执行前，系统已完成对用户旧密码的检查</p>
     *
     * @param username 用户名（用不到，但动态路径需要）
     * @param user     用户对象，包含新密码
     * @return 响应
     */
    @PutMapping("/{username}/password")
    public Result<?> updateUserPassword(@PathVariable String username, @RequestBody User user) {
        if (StringUtils.isBlank(user.getPassword()))
            return new Result.Builder<>().resultCodeEnum(ResultCodeEnum.PASSWORD_NULL).build();
        else
            return userServiceImpl.changePassword(user);
    }

    /**
     * 在用户修改密码前，需要用户输入旧密码，然后进行确认
     *
     * @param username 用户名（用不到但动态路径需要）
     * @param user     用户对象，包含用户输入的密码
     * @return 响应
     */
    @GetMapping("/{username}/password")
    public Result<?> checkPassword(@PathVariable String username, @RequestBody User user) {
        if (StringUtils.isBlank(user.getPassword()))
            return new Result.Builder<>().resultCodeEnum(ResultCodeEnum.PASSWORD_NULL).build();
        else
            return userServiceImpl.checkPassword(user);
    }
}