package cn.com.compass.base.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo api字段属性
 * @date 2018年11月1日 上午9:46:03
 *
 */
@Documented
@Retention(RUNTIME)
@Target(FIELD)
public @interface ApiModelProperty {

	/**
	 * 字段名
	 * @return
	 */
	String name() default "";

	/**
	 * 字段描述
	 * @return
	 */
	String description() default "";

	/**
	 * 取值范围；
	 * 支持两种：
	 * list  : ex 1,2,3,4
	 * range : ex range[0,10]
	 * @return
	 */
	String allowableValues() default "";

	/**
	 * 是否必填
	 * @return
	 */
	boolean required() default false;

	/**
	 * 是否隐藏
	 * @return
	 */
	boolean hidden() default false;

	/**
	 * 示例
	 * @return
	 */
	String example() default "";

}
