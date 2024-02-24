package com.shooter.mojbackend.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ClassName: AuthCheck
 * Package: com.shooter.mojbackend.annotation
 * Description:
 *
 * @Author:Shooter
 * @Create 2024/2/22 19:58
 * @Version 1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthCheck {

    /**
     * 必须有某个角色
     *
     * @return
     */
    String mustRole() default "";

}
