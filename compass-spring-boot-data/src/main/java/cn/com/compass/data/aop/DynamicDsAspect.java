package cn.com.compass.data.aop;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import cn.com.compass.data.annotation.DynamicDs;
import cn.com.compass.data.dynamic.DynamicDsSwitch;

/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 动态数据源切换切面
 * @date 2018年6月6日 下午3:50:58
 * @since v1.1.2 sharding-jdbc自动实现了读写分离 master-slave分离，不在使用自定义方式切换数据源
 */
@Aspect
@Component
@Deprecated 
public class DynamicDsAspect {

	private ThreadLocal<String> hodler = new ThreadLocal<>();

	@Pointcut("@annotation(cn.com.compass.data.annotation.DynamicDs)")
	public void DynamicDsPointCut() {
	}

	@Before("DynamicDsPointCut()")
	public void before(JoinPoint call) {
		String oldDs = DynamicDsSwitch.getDataSourceType();
		hodler.set(oldDs);
		String currDsName = getTargetDsName(call);
		DynamicDsSwitch.setDataSourceType(currDsName);
	}

	@AfterReturning(returning = "ret", pointcut = "DynamicDsPointCut()")
	public void afterReturn(Object ret) {
		DynamicDsSwitch.setDataSourceType(hodler.get());
		hodler.set(null);
		hodler.remove();
	}

	@AfterThrowing(pointcut = "DynamicDsPointCut()", throwing = "e")
	public void afterThrowing(JoinPoint call, Throwable e) {
		DynamicDsSwitch.setDataSourceType(hodler.get());
		hodler.set(null);
		hodler.remove();
	}

	/**
	 * 获取数据源名称
	 * 
	 * @param joinPoint
	 * @return
	 */
	private static String getTargetDsName(JoinPoint joinPoint) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		DynamicDs dynamicDs = method.getAnnotation(DynamicDs.class);
		return dynamicDs.target();
	}

}
