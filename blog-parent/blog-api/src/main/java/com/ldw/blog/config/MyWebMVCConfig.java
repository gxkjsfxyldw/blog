package com.ldw.blog.config;

import com.ldw.blog.handler.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebMVCConfig implements WebMvcConfigurer {
    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override  //跨域配置
    public void addCorsMappings(CorsRegistry registry) {
        //允许浏览器的8080端口访问后端的8888端口
        registry.addMapping("/**").allowedOrigins("http://localhost:8080");
//        registry.addMapping("/**").allowedOrigins("http://101.34.23.203");//服务器
    }

    //只有经过登录拦截器才可获取当前登录用户的信息
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //拦截test接口，后续遇到实际需要拦截的接口时，在进行添加拦截  未登录不给它进入首页即可
       registry.addInterceptor(loginInterceptor)
               .addPathPatterns("/test")
               //添加评论功能拦截，未登录不可评论
               .addPathPatterns("/comments/create/change")
               //未登录不可以发布文章
               .addPathPatterns("/articles/publish");
    }
}
