package com.sigma429.sl.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented // 标记注解
public @interface NoAuthorization {

}