package cn.com.compass.web.annotation;

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
 * @todo controller日志注解
 * @date 2018年6月6日 下午3:57:54
 *
 */
@Documented
@Retention(RUNTIME)
@Target(METHOD)
public @interface BaseLog {
	/**
	 * 接口编码
	 * @return
	 */
	String code();

	/**
	 * 接口描述
	 * @return
	 */
	String desc();
}
