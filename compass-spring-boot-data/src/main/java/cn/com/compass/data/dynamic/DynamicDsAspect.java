package cn.com.compass.data.dynamic;

import cn.com.compass.data.annotation.DynamicDs;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 动态数据源切换切面
 * @date 2018年6月6日 下午3:50:58
 */
@Aspect
@Component
public class DynamicDsAspect {

	@Pointcut("@annotation(cn.com.compass.data.annotation.DynamicDs)")
	public void DynamicDsPointCut() {
	}

	@Before("DynamicDsPointCut()")
	public void before(JoinPoint call) {
		// 设置目标数据源
		DynamicDsHodler.setDataSourceType(getTargetDsName(call));
	}

	@AfterReturning(returning = "ret", pointcut = "DynamicDsPointCut()")
	public void afterReturn(Object ret) {
		// 还原主数据源
		DynamicDsHodler.clearDataSourceType();
	}

	@AfterThrowing(pointcut = "DynamicDsPointCut()", throwing = "e")
	public void afterThrowing(JoinPoint call, Throwable e) {
		// 还原主数据源
		DynamicDsHodler.clearDataSourceType();
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
