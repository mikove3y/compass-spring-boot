package cn.com.compass.data.annotation;

import cn.com.compass.base.constant.BaseBizeStatusEnum;

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
	BaseBizeStatusEnum.YesOrNo deleteValue() default BaseBizeStatusEnum.YesOrNo.YES;
	/**
	 * 不删除值，默认0
	 * @return
	 */
	BaseBizeStatusEnum.YesOrNo notDeleteValue() default BaseBizeStatusEnum.YesOrNo.NO;
}
