package com.ldw.blog.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ldw.blog.dao.mapper.SysUserMapper;
import com.ldw.blog.dao.pojo.SysUser;
import com.ldw.blog.service.LoginService;
import com.ldw.blog.service.SysUserService;
import com.ldw.blog.vo.ErrorCode;
import com.ldw.blog.vo.LoginUserVo;
import com.ldw.blog.vo.Result;
import com.ldw.blog.vo.UserVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Autowired
    private LoginService loginService;
    //查询某作者id
    @Override
    public UserVo findUserVoById(Long id) {
        SysUser sysUser=sysUserMapper.selectById(id);
        if(sysUser==null){ //判断查询出来的数据为空值，设置一个默认的
            sysUser=new SysUser();
            sysUser.setAvatar("");
            sysUser.setId(1L);
            sysUser.setNickname("LDW");
        }
        UserVo vo =new UserVo();
//      BeanUtils.copyProperties(vo,sysUser);//将vo和pojo里面的相同属性进行拷贝,与下面的操作一致
        //缓存新改 用pojo里面的转换为string再存储
        vo.setId(String.valueOf(sysUser.getId()));

        vo.setNickname(sysUser.getNickname());
        vo.setAvatar(sysUser.getAvatar());
        return vo;
    }
    //查询某作者id
    @Override
    public SysUser findUserById(Long id) {
        SysUser sysUser=sysUserMapper.selectById(id);
        if(sysUser==null){ //判断查询出来的数据为空值，设置一个默认的
            sysUser=new SysUser();
            sysUser.setAvatar("");
            sysUser.setId(1L);
            sysUser.setNickname("LDW的个人博客");
        }
        return sysUser;
    }
    //登录功能
    @Override
    public SysUser findUser(String account, String password) {
        LambdaQueryWrapper<SysUser> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount,account);//对比两者是否一致
        queryWrapper.eq(SysUser::getPassword,password);//对比两者是否一致
        //只需要提取id 头像，名称
        queryWrapper.select(SysUser::getId,SysUser::getAccount, SysUser::getAvatar,SysUser::getNickname);
        queryWrapper.last("limit 1"); //只查询一条
        return sysUserMapper.selectOne(queryWrapper);
    }
    //根据token查询用户信息：redis
    @Override
    public Result findUserByToken(String token) {
        /*
          1.token合法性校验：是否为空，解析是否成功，redis是否存在
          2.校验失败：返回错误
          3.校验成功：返回对应结果 LoginUserVo
         */
        SysUser sysUser = loginService.chckenToken(token);//校验成功后将用户信息返回
        if(sysUser==null){
            return  Result.fail(ErrorCode.TOKEN_ERROR.getCode(), ErrorCode.TOKEN_ERROR.getMsg());
        }
        //将数据中的字段和Vo对象中的字段数据进行传递
        LoginUserVo loginUserVo=new LoginUserVo();
        //缓存新改 用pojo里面的转换为string再存储
        loginUserVo.setId(String.valueOf(sysUser.getId()) );
        loginUserVo.setNickname(sysUser.getNickname());
        loginUserVo.setAccount(sysUser.getAccount());
        loginUserVo.setAvatar(sysUser.getAvatar());
        return Result.success(loginUserVo);//返回登录用户信息
    }
    //根据account查询是否存在用户
    @Override
    public SysUser findUserByaccount(String account) {
        LambdaQueryWrapper<SysUser> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount,account);
        queryWrapper.last("limit 1");//注意空格
        return this.sysUserMapper.selectOne(queryWrapper);
    }
    //保存注册成功的用户
    @Override
    public void sava(SysUser sysUser) {
        //保存用户这 id会自动生成
        //默认生成的id是 分布式id，雪花算法
        //由于这里使用 mybatis-plus
        this.sysUserMapper.insert(sysUser);
    }
}
