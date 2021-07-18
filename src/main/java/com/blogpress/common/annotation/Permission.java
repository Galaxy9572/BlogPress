package com.blogpress.common.annotation;

import com.blogpress.common.enums.role.RoleEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 权限认证
 * @author JY
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Permission {

    boolean needLogin() default true;

    RoleEnum[] roles() default RoleEnum.USER;

}
