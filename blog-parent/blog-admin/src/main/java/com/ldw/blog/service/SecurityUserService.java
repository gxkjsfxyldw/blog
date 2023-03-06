package com.ldw.blog.service;

import com.ldw.blog.pojo.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayDeque;

@Component
public class SecurityUserService implements UserDetailsService {

    @Autowired
    private AdminService adminService;


    @Override
    public UserDetails loadUserByUsername(String usernmae) throws UsernameNotFoundException {
        //登录的时候，会把username传递到这里
        //通过username查询数据库admin表，如果 admin存在username 将密码告诉spring security
        //如果不存在 返回null 认证失败
        Admin admin=this.adminService.findAdminByUsername(usernmae);
        if(admin==null){
            return null;//登录认证失败
        }
        //密码的认证由security来做：
        // 第三个参数认证授权列  暂时为空
        UserDetails userDetails=new User(usernmae,admin.getPassword(),new ArrayDeque<>());
        return userDetails;
    }
}
