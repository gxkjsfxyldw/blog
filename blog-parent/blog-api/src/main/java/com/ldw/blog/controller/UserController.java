package com.ldw.blog.controller;

import com.ldw.blog.dao.pojo.SysUser;
import com.ldw.blog.service.SysUserService;
import com.ldw.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {
    //获取登录用户信息
    @Autowired
    private SysUserService sysUserService;
    // /users/currentUser
    @GetMapping("currentUser")
    public Result currentUser(@RequestHeader("Authorization")String token){

        return sysUserService.findUserByToken(token);
    }

}
