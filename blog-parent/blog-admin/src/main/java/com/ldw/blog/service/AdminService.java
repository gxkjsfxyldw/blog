package com.ldw.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ldw.blog.mapper.AdminMapper;
import com.ldw.blog.pojo.Admin;
import com.ldw.blog.pojo.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    private AdminMapper adminMapper;

    public Admin findAdminByUsername(String username){

        LambdaQueryWrapper<Admin> AdminLambdaQueryWrapper = new LambdaQueryWrapper<>();
        AdminLambdaQueryWrapper.eq(Admin::getUsername,username);
        AdminLambdaQueryWrapper.last("limit 1");
        Admin admin = adminMapper.selectOne(AdminLambdaQueryWrapper);
        return admin;
    }

    public List<Permission> findPermissionByAdminId(Long AdminId) {
        return adminMapper.findPermissionByAdminId(AdminId);


    }
}
