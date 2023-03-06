package com.ldw.blog.controller;

import com.ldw.blog.model.params.PageParam;
import com.ldw.blog.pojo.Permission;
import com.ldw.blog.service.PermissionService;
import com.ldw.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private PermissionService permissionservice;

    /**
     * 查询所有用户权限列表
     * @param pageParam
     * @return
     */
    @PostMapping("permission/permissionList")
    public Result listPermission(@RequestBody PageParam pageParam){

        return permissionservice.listPermission(pageParam);
    }

    /**
     *增加用户权限
     * @param permission
     * @return
     */

    @PostMapping("permission/add")
    public Result add(@RequestBody Permission permission){
        return permissionservice.add(permission);
    }

    /**
     *更新用户权限
     * @param permission
     * @return
     */
    @PostMapping("permission/update")
    public Result update(@RequestBody Permission permission){
        return permissionservice.update(permission);
    }

    /**
     *删除用户权限
     * @param id
     * @return
     */
    @GetMapping("permission/delete/{id}")
    public Result delete(@PathVariable("id") Long id){
        return permissionservice.delete(id);
    }

}
