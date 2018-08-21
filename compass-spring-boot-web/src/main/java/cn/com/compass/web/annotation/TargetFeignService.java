package cn.com.compass.web.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo eip + feginClient方式进行boot服务远程调用
 * @date 2018年6月20日 上午10:10:11
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Deprecated
public @interface TargetFeignService {
	/**
	 * feginClient 远程调用的应用名
	 * @return
	 */
	String value();
}
