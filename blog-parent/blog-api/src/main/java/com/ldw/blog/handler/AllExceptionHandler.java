package com.ldw.blog.handler;

import com.ldw.blog.vo.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.bind.annotation.ResponseBody;
//主动抛出异常
//对加了controller注解的方法进行拦截处理 AOP的实现
@ControllerAdvice
public class AllExceptionHandler {
    //进行异常处理， 处理Exception.class的异常
    @ExceptionHandler (Exception.class)
    @ResponseBody //加它返回json数据，不加返回界面
    public Result doException(Exception ex){
        ex.printStackTrace();
        return Result.fail(-999,"系统异常");
    }


}
