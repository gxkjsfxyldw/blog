package com.ldw.blog.common.cache;
//定义redis缓存优化，统一缓存处理优化

import com.alibaba.fastjson.JSON;
import com.ldw.blog.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import java.lang.reflect.Method;
import java.time.Duration;

//定义一个切面，切面定义了切点和通知的关系
@Aspect
@Component
@Slf4j
public class CacheAspect {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    //切点  只要加了它的注解，就可以使用到通知的操作
    @Pointcut("@annotation(com.ldw.blog.common.cache.Cache)")
    public void pt(){}

    //环绕通知，即在方法的前后均可以增强
    @Around("pt()")//关联此切点
    public Object around(ProceedingJoinPoint pjp){
        try {
            Signature signature = pjp.getSignature();
            //类名 ArticleController
            String className = pjp.getTarget().getClass().getSimpleName();
            //调用的方法名  hotArticle
            String methodName = signature.getName();

            //获得参数的类
            Class[] parameterTypes = new Class[pjp.getArgs().length];
            //获得参数的值
            Object[] args = pjp.getArgs();
            //参数
            String params = "";
            for(int i=0; i<args.length; i++) {
                if(args[i]!=null){//判断参数是否有值
                    //将参数转换为json
                    params += JSON.toJSONString(args[i]);
                    //保存参数对应的类型
                    parameterTypes[i] = args[i].getClass();
                }else {
                    parameterTypes[i] = null;
                }
            }
            if (StringUtils.isNotEmpty(params)) {
                //加密，以防出现key过长以及字符转义取不到的情况
                params = DigestUtils.md5Hex(params);
            }
            //拿到controller中对应的方法
            Method method = pjp.getSignature().getDeclaringType().getMethod(methodName, parameterTypes);
            //获取Cache注解
            Cache annotation = method.getAnnotation(Cache.class);
            //设置缓存过期时间
            long expire = annotation.expire();
            //设置缓存名称
            String name = annotation.name();
            //先从redis里面获取key
            String redisKey = name + "::" + className+"::"+methodName+"::"+params;
            //获取key对应的value
            String redisValue = redisTemplate.opsForValue().get(redisKey);
            if (StringUtils.isNotEmpty(redisValue)){//如果不为空，则直接返还
                log.info("《《《走了缓存》》》,{},{}",className,methodName);
                return JSON.parseObject(redisValue, Result.class);
            }

            //如果为空，表示则要访问此方法，这个就代表要调用这里的方法
            Object proceed = pjp.proceed();
            //然后将方法返回的结果转换为json然后存入redis
            redisTemplate.opsForValue().set(redisKey,JSON.toJSONString(proceed), Duration.ofMillis(expire));
            log.info("《《《存入了缓存》》》,{},{}",className,methodName);
            return proceed;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return Result.fail(-999,"缓存_系统错误");
    }

}
