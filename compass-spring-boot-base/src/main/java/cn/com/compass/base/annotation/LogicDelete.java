package cn.com.compass.base.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 逻辑删除枚举
 * @date 2018年11月1日 上午9:46:03
 *
 */
@Documented
@Retention(RUNTIME)
@Target(FIELD)
public @interface LogicDelete {
	
	/**
	 * 删除值，默认1
	 * @return
	 */
	int deleteValue() default 1;
	/**
	 * 不删除值，默认0
	 * @return
	 */
	int notDeleteValue() default 0;
}
