package com.ldw.blog.service;
import com.ldw.blog.dao.pojo.SysUser;
import com.ldw.blog.vo.Result;
import com.ldw.blog.vo.params.LoginParam;
import org.springframework.transaction.annotation.Transactional;

public interface LoginService {
    /**
     *  登录功能
     * @param loginParam
     * @return
     */
    Result login(LoginParam loginParam);

    SysUser chckenToken(String token);
    /**
     * 退出登录
     * @param token
     * @return
     */
    Result logout(String token);
    /**
     * 注册
     * @param loginParam
     * @return
     */
    Result register(LoginParam loginParam);
}
