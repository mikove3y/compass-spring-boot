package cn.com.compass.data.annotation;

import cn.com.compass.data.entity.BaseEntity;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 逻辑删除开关，作用在BaseEntity实体的子类上
 * @date 2018年11月1日 上午9:46:03
 *
 */
@Documented
@Retention(RUNTIME)
@Target(TYPE)
public @interface EnableLogicDelete {

	/**
	 * 逻辑删除策略
	 * @return
	 */
	LogicDeleteStrategy strategy() default LogicDeleteStrategy.STATUS;

	/**
	 * 备份表domainClass
	 * @return
	 */
	Class<? extends BaseEntity> backupDomainClass() default BaseEntity.class;

	/**
	 * 逻辑删除策略
	 * BACKUP 备份到备份表
	 * STATUS 改变逻辑删除字段状态
	 */
	public enum  LogicDeleteStrategy {
		BACKUP,STATUS
	}

}
