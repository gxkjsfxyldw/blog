package com.ldw.blog.service.Impl;

import com.alibaba.fastjson.JSON;
import com.ldw.blog.dao.pojo.SysUser;
import com.ldw.blog.service.LoginService;
import com.ldw.blog.service.SysUserService;
import com.ldw.blog.utils.JWTUtils;
import com.ldw.blog.vo.ErrorCode;
import com.ldw.blog.vo.Result;
import com.ldw.blog.vo.params.LoginParam;
import io.netty.util.internal.StringUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Transactional //当插入数据出现错误时，事务进行回滚，不插入数据库
public class LoginServiceImpl implements LoginService {

    @Autowired
    private SysUserService sysUserService;//获取用户
    @Autowired
    private  RedisTemplate<String,String> redisTemplate;//获取redis用户信息
    //加密盐（言）
    private static final String slat="ldw!@#";
    @Override
    public Result login(LoginParam loginParam) {
        /*
          1.检查参数是否合法
          2.根据用户名和密码去user表中查询，是否合法
          3.如果不存在，登录失败
          4.如果存在，使用jwt 生成token，返回给前端
          5.token放入redis中，使用redis token：user信息，设置过期时间
          （登录认证的时候，先认证token的字符串是否合法，去redis人认证是否存在）
         */
        String account =loginParam.getAccount();
        String password=loginParam.getPassword();
        if(StringUtils.isAllBlank(account)||StringUtils.isAllBlank(password)){
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(),ErrorCode.PARAMS_ERROR.getMsg());
        }

        //把密码做加密之后再传入数据库中查询 要不然直接查前端传入的是无法查到的
        password= DigestUtils.md5Hex(password+slat);
        SysUser sysUser=sysUserService.findUser(account,password);
        if(sysUser==null){
            return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(),ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }
        String token= JWTUtils.createToken(sysUser.getId());
        //使用redis
        redisTemplate.opsForValue().set("TOKEN_"+token,JSON.toJSONString(sysUser),1, TimeUnit.DAYS);
        return Result.success(token);
    }
    //校验登录 token的合法，返回当前登录用户信息
    @Override
    public SysUser chckenToken(String token) {
        if(StringUtils.isAllBlank(token)){
            return null;
        }
        Map<String, Object> stringObjectMap = JWTUtils.checkToken(token);
        if(stringObjectMap==null){//检查token合法性
            return null;
        }
        String userJson = redisTemplate.opsForValue().get("TOKEN_" + token);
        if(StringUtils.isAllBlank(userJson)){//token可能会过期
            return null;
        }

        SysUser sysUser = JSON.parseObject(userJson, SysUser.class);//将json数据转换成java对象
        return sysUser;
    }
    //退出登录
    @Override
    public Result logout(String token) {
        //只需要删除redis中存储的值即可
        redisTemplate.delete("TOKEN_" + token);
        return Result.success(null);
    }
    //注册
    @Override
    public Result register(LoginParam loginParam) {
        /**
         * 1.判断参数是否合法
         * 2.判断账户是否已存在，存在，则此账户已被注册
         * 3.如果账户不存在，注册用户
         * 4.生成token
         * 5.存入redis
         * 6.注意，加上事务，一旦中间的任何过程问题，注册的用户，需要回滚
         */
        String account=loginParam.getAccount();
        String password=loginParam.getPassword();
        String nickname=loginParam.getNickname();
        if(StringUtils.isAllBlank(account) //判断是否其中一项为空
            || StringUtils.isAllBlank(password)
            || StringUtils.isAllBlank(nickname)
        ){
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }
        SysUser sysUser=sysUserService.findUserByaccount(account);
        if(sysUser!=null){
            return Result.fail(ErrorCode.ACCOUNT_EXIST.getCode(), "账号已经被注册了");
        }
        sysUser=new SysUser();
        sysUser.setNickname(nickname);
        sysUser.setAccount(account);
        sysUser.setPassword(DigestUtils.md5Hex(password+slat));
        sysUser.setCreateDate(System.currentTimeMillis());
        sysUser.setLastLogin(System.currentTimeMillis());
        sysUser.setAvatar("/static/img/logo.jpg");
        sysUser.setAdmin(1); //1 表示 true
        sysUser.setDeleted(0);//表示false
        sysUser.setSalt("");
        sysUser.setStatus("");
        sysUser.setEmail("2632711107@qq.com");
        this.sysUserService.sava(sysUser);

        String token=JWTUtils.createToken(sysUser.getId());
        redisTemplate.opsForValue().set("TOKEN_"+token,JSON.toJSONString(sysUser),1,TimeUnit.DAYS);
        return Result.success(token);
    }
}
