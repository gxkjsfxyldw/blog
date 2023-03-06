package com.ldw.blog.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//安全认证配置文件
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //密码的配置 密码策略：BCrypt
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
    public static void main(String [] args){
        //加密策略 MD5 不安全 彩虹表  MD5 加盐
        String ldw=new BCryptPasswordEncoder().encode("ldw");
        System.out.println(ldw);
    }
    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()//开启登录认证
            //.antMatchers("/user/findAll").hasRole("admin") //访问接口需要admin的角色
            //静态资源过滤
            .antMatchers("/css/**").permitAll()
            .antMatchers("/img/**").permitAll()
            .antMatchers("/js/**").permitAll()
            .antMatchers("/plugins/**").permitAll()
            .antMatchers("/admin/**").access("@authService.auth(request,authentication)") //自定义service 来去实现实时的权限认证 拦截
            .antMatchers("/pages/**").authenticated()//表示认证通过才可访问，即登录成功后才可以访问 拦截
            .and()
             //自定义登录页面
            .formLogin()//因为它有个原生的，所以需要声明自定义
            .loginPage("/login.html")//页面名称
            .loginProcessingUrl("/login")//登录处理接口
            .usernameParameter("username")//定义登录时，用户名的key，默认是username
            .passwordParameter("password")//定义登录时，密码的key，默认是password
            .defaultSuccessUrl("/pages/main.html")//定义登录成功后默认跳转的页面
            .failureUrl("/login.html")//定义登录失败后跳转的页面
            .permitAll()//通过，不拦截，更加前面配的路径决定，这是指和登录表相关的接口，都通过
            .and()
            //自定义退出登录界面
            .logout()
            .logoutUrl("/logout") //退出登录接口
             .logoutSuccessUrl("/login.html")
            .permitAll()//退出登录的接口放行 不拦截
            .and()
            .httpBasic()
            .and()
            .csrf().disable() //csrf关闭 如果自定义登录 需要关闭
            .headers().frameOptions().sameOrigin();//支持iframe页面嵌套
    }
}
