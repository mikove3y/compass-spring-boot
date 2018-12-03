package cn.com.compass.web.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo
 * @date 2018/12/3 17:10
 */
@Documented
@Retention(RUNTIME)
@Target(METHOD)
public @interface BasePermission {
    /**
     * false 不需要token鉴权
     * true 需要token鉴权
     * @return
     */
    @AliasFor("value")
    boolean needAuth() default false;

    /**
     * false 不需要token鉴权
     * true 需要token鉴权
     * @return
     */
    @AliasFor("needAuth")
    boolean value() default false;

}
