package cn.com.compass.data.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 动态数据源注解
 * @date 2018年6月6日 下午3:50:42
 *
 */
@Documented
@Retention(RUNTIME)
@Target(METHOD)
public @interface DynamicDs {
	String target();
}
