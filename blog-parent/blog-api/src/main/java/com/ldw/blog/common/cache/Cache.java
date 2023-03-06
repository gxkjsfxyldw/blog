package com.ldw.blog.common.cache;


import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Cache {
    //设置过期时间
    long expire() default 1*60*1000;
    //缓存名称/标识
    String name() default "";



}
