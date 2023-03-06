package com.ldw.blog.controller;

import com.ldw.blog.dao.pojo.SysUser;
import com.ldw.blog.utils.UserThreadLocal;
import com.ldw.blog.vo.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//测试登录拦截器
@RestController //返回数据用这个，返回页面就用controller即可
@RequestMapping("test")
public class TestController {

    @RequestMapping
    public Result test(){
        SysUser sysUser= UserThreadLocal.get();//直接获得当前线程登录的用户的信息
        System.out.println(sysUser);
        return Result.success(null);
    }
}
