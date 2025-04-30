package com.moujitx.myblog.server.common;

import java.lang.annotation.*;

/**
 * 拦截器放行注解
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthAccess {
}
