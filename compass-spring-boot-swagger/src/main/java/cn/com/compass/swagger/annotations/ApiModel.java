package cn.com.compass.swagger.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo apiModel 属性
 * @date 2019/3/27 15:19
 */
@Documented
@Retention(RUNTIME)
@Target(TYPE)
public @interface ApiModel {
    String value() default "";

    String description() default "";

    Class<?> parent() default Void.class;

    String discriminator() default "";

    Class<?>[] subTypes() default {};

    String reference() default "";
}