package com.ldw.blog.controller;

import com.ldw.blog.dao.pojo.SysUser;
import com.ldw.blog.service.LoginService;
import com.ldw.blog.utils.UserThreadLocal;
import com.ldw.blog.vo.Result;
import com.ldw.blog.vo.params.LoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    public Result login(@RequestBody LoginParam loginParam){
        //登录，验证用户，访问用户表，
        SysUser sysUser= UserThreadLocal.get();//直接获得当前线程登录的用户的信息
        System.out.println(sysUser);
        return loginService.login(loginParam);
    }


}
