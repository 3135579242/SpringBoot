package com.along.springboot.controller;

import com.along.springboot.entity.User;
import com.along.springboot.entity.dto.AuthenticationRequest;
import com.along.springboot.entity.dto.RegisterRequest;
import com.along.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/auth")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 注册新用户
     *
     * @param registerRequest 用户表单
     * @return 新用户的信息
     */
    @PostMapping("/register")
    public User register(@RequestBody RegisterRequest registerRequest) {
        return userService.register(registerRequest);
    }

    /**
     * 登陆接口
     *
     * @param authenticationRequest 用户表单
     * @return 返回Token
     */
    @PostMapping("/login")
    public String login(@RequestBody AuthenticationRequest authenticationRequest) {
        return userService.login(authenticationRequest);
    }

}
