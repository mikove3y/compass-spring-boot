package cn.com.compass.web.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

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
@Target({METHOD})
public @interface BaseApi {
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

    /**
     * 日志持久化服务别名 @see cn.com.compass.web.logback.BaseLogPersistence
     * @return
     */
    String persistenceQualifier() default "";

}
