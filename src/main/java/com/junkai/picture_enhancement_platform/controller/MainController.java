package com.junkai.picture_enhancement_platform.controller;

import com.junkai.picture_enhancement_platform.entity.User;
import com.junkai.picture_enhancement_platform.service.impls.UserServiceImpl;
import com.junkai.picture_enhancement_platform.ultils.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {


    private final UserServiceImpl userServiceImpl;

    public MainController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping
    public String index() {
        return "/index.html";
    }

    @PostMapping("/login")
    public Result<?> login(@RequestBody User user) {
        return userServiceImpl.login(user);
    }
}
