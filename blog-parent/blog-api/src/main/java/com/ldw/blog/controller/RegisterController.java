package com.ldw.blog.controller;

import com.ldw.blog.service.LoginService;
import com.ldw.blog.vo.Result;
import com.ldw.blog.vo.params.LoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("register")
public class RegisterController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    public Result register(@RequestBody LoginParam loginParam){
        //sso 单点登录，后期如果把登录注册功能提取出去，（单独的服务，可以独立提供）
        return loginService.register(loginParam);
    }


}
