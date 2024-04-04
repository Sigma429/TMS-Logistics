package com.sigma429.sl.annotation;




import com.sigma429.sl.enums.PayChannelEnum;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented // 标记注解
public @interface PayChannel {

    PayChannelEnum type();

}