package com.ldw.blog.controller;

import com.ldw.blog.service.LoginService;
import com.ldw.blog.vo.Result;
import com.ldw.blog.vo.params.LoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("logout")
public class LogoutController {

    @Autowired
    private LoginService loginService;

    @GetMapping
    public Result login(@RequestHeader("Authorization")String token){

        return loginService.logout(token);
    }


}
