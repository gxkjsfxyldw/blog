package com.ldw.blog.common.aop;
import java.lang.annotation.*;
//Type 代表可以放在类 上面 method 代码可以放在方法上面
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {
    String module() default "";
    String opertion() default "";
}
