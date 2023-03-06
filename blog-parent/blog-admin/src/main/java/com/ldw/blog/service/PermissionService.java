package com.ldw.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ldw.blog.mapper.PermisionMapper;
import com.ldw.blog.model.params.PageParam;
import com.ldw.blog.pojo.Permission;
import com.ldw.blog.vo.PageResult;
import com.ldw.blog.vo.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissionService {

    @Autowired
    private PermisionMapper permissionmapper;
    public Result listPermission(PageParam pageParam){

        /**
         * 要的数据，观礼台，表的所有字段 Permission
         * 分页查询
         */
        //当前页，最大页
        Page<Permission> page=new Page<>(pageParam.getCurrentPage(),pageParam.getPageSize());
        LambdaQueryWrapper<Permission> queryWrapper=new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(pageParam.getQueryString())){
            queryWrapper.eq(Permission::getName,pageParam.getQueryString());
        }
        Page<Permission> Permission=  permissionmapper.selectPage(page,queryWrapper);
        PageResult<Permission> pageResult=new PageResult<>();
        pageResult.setList(Permission.getRecords());
        pageResult.setTotal(Permission.getTotal());
        return Result.success(pageResult);
    }

    public Result add(Permission permission) {
        this.permissionmapper.insert(permission);
        return Result.success(null);
    }

    public Result update(Permission permission) {
        this.permissionmapper.updateById(permission);
        return Result.success(null);
    }

    public Result delete(Long id) {
        this.permissionmapper.deleteById(id);
        return Result.success(null);
    }
}
