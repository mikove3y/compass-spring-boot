package cn.com.compass.web.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
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
@Target({METHOD})
public @interface BaseApi {
    /**
     * false 不需要token鉴权
     * true 需要token鉴权
     * @return
     */
    @AliasFor("value")
    boolean needAuth() default true;

    /**
     * false 不需要token鉴权
     * true 需要token鉴权
     * @return
     */
    @AliasFor("needAuth")
    boolean value() default true;

    /**
     * 是否api版本管理
     * @return
     */
    boolean versionManage() default false;
    /**
     *  0-99 最大值99
     * api 版本
     * @return
     */
    int version() default 1;
    /**
     * 接口编码
     * @return
     */
    String code() default "";

    /**
     * 接口描述
     * @return
     */
    String desc() default "";

    /**
     * 是否打印日志
     * @return
     */
    boolean printLog() default true;

}
