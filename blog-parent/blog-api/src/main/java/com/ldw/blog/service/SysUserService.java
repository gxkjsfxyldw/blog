package com.ldw.blog.service;

import com.ldw.blog.dao.pojo.SysUser;
import com.ldw.blog.vo.Result;
import com.ldw.blog.vo.UserVo;
import org.springframework.stereotype.Service;

@Service
public interface SysUserService {
    //查询某作者的id
    SysUser findUserById(Long id);
    SysUser findUser(String account, String password);

    /**
     * 根据token查询用户信息
     * @param token
     * @return
     */
    Result findUserByToken(String token);
    //查询数据库是否已存在用户
    SysUser findUserByaccount(String account);
    //保存注册成功的用户
    void sava(SysUser sysUser);

    UserVo findUserVoById(Long id);


}
