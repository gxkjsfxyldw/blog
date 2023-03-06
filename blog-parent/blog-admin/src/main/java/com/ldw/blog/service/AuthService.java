package com.ldw.blog.service;

import com.ldw.blog.pojo.Admin;
import com.ldw.blog.pojo.Permission;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class AuthService {

    @Autowired
    private AdminService adminService;

    public boolean auth(HttpServletRequest request, Authentication authentication){
        //权限认证
        //请求路径
        String requestURI=request.getRequestURI();
        //当前目录的信息
        Object principal=authentication.getPrincipal();
        //为空，或者是否为匿名用户
        if (principal == null || "anonymousUser".equals(principal)){
            //未登录
            return false;   //true代表放行 false 代表拦截
        }
        UserDetails userDetails= (UserDetails) principal;
        String username = userDetails.getUsername();
        Admin adminByUsername = adminService.findAdminByUsername(username);
        //用户名为空
        if(adminByUsername==null){
            return false;
        }
        if (adminByUsername.getId() == 1){
            //认为是超级管理员
            return true;//表示放行
        }
        Long id=adminByUsername.getId();
        List<Permission>permissions=this.adminService.findPermissionByAdminId(id);
        requestURI= StringUtils.split(requestURI,'?')[0];
        for (Permission permission : permissions) {
            if(requestURI.equals(permission.getPath())){//相等时
                    return true;//表示放行
            }
        }
        return false;
    }
}
