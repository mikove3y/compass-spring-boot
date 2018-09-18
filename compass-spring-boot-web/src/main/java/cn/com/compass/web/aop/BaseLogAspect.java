package cn.com.compass.web.aop;

import java.lang.reflect.Method;

import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import cn.com.compass.base.vo.BaseLogVo;
import cn.com.compass.util.DateUtil;
import cn.com.compass.util.JacksonUtil;
import cn.com.compass.web.annotation.BaseLog;
import lombok.extern.slf4j.Slf4j;
/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 基础日志切面
 * @date 2018年6月6日 下午3:58:29
 *
 */
@Aspect
@Component
@Slf4j
public class BaseLogAspect implements Ordered {
	
	
	private ThreadLocal<BaseLogVo> logLocal = new ThreadLocal<>();
	/**
	 * 应用名
	 */
	@Value("${spring.application.name}")
	private String application;
	/**
	 * 应用地址
	 */
	@Value("${spring.cloud.client.ipAddress}:${server.port}")
	private String appAddress;
	
	/**
	 * 定义日志切入点
	 */
	@Pointcut("@annotation(cn.com.compass.web.annotation.BaseLog)")
	public void logPointCut() {
	}

	/**
	 * 后置通知 用于拦截service层记录用户的操作
	 *
	 * @param joinPoint
	 *            切点
	 */
	@Before("logPointCut()")
	public void doBefore(JoinPoint joinPoint) {
		try {
			BaseLogVo logV = new BaseLogVo();
			// 请求开始时间
			logV.setOperateStartTime(DateUtil.getCurrentDateTime());
			// 请求方法全名
			logV.setFullMethodName(getFullMethodName(joinPoint));
			BaseLog anno = getMethodLogAnnotation(joinPoint);
			// 日志编码
			logV.setLogCode(anno.code());
			// 日志描述
			logV.setLogDes(anno.desc());
			Object[] args = joinPoint.getArgs();
			// 请求参数
			logV.setRequestParams(JacksonUtil.obj2json(args));
			// 请求日志Id
			logV.setLogId("" + (ArrayUtils.isNotEmpty(args) && args.length >= 2 ? args[args.length - 1].toString()
					: System.currentTimeMillis()));
			// 应用名
			logV.setApplication(application);
			// 应用地址
			logV.setAppAddress(appAddress);
			logLocal.set(logV);
		} catch (Exception e) {
			log.error("==日志前置通知异常==");
			log.error("异常信息:{}", e.getMessage());
		}
	}

	/**
	 * 切入点return内容之后切入内容（可以用来对处理返回值做一些加工处理）
	 * 
	 * @param ret
	 * @throws Throwable
	 */
	@AfterReturning(returning = "ret", pointcut = "logPointCut()")
	public void doAfterReturning(JoinPoint joinPoint,Object ret) throws Throwable {
		BaseLogVo logV = logLocal.get();
		// 移除缓存
		logLocal.remove();
		// 请求结束时间
		logV.setOperateEndTime(DateUtil.getCurrentDateTime());
		// 请求耗时
		logV.setUseTime(DateUtil.subtract(logV.getOperateStartTime(), logV.getOperateEndTime()) + "秒");
		// 请求响应结果
		if(ret!=null)
		logV.setResponseData(JacksonUtil.obj2json(ret));
		// 请求状态
		logV.setStatus(BaseLogVo.ResponseSatus.success.name());
		if(log.isInfoEnabled())
			log.info(JacksonUtil.obj2json(logV));
		if(log.isDebugEnabled())
			log.debug(JacksonUtil.obj2json(logV));
	}

	/**
	 * 异常通知 用于拦截service层记录异常日志
	 *
	 * @param joinPoint
	 * @param e
	 */
	@AfterThrowing(pointcut = "logPointCut()", throwing = "e")
	public void doAfterThrowing(JoinPoint joinPoint, Throwable e) throws Throwable {
		BaseLogVo logV = logLocal.get();
		// 移除缓存
		logLocal.remove();
		logV.setOperateEndTime(DateUtil.getCurrentDateTime());
		logV.setUseTime(DateUtil.subtract(logV.getOperateStartTime(), logV.getOperateEndTime()) + "秒");
		logV.setErroMsg(e.getMessage());
		logV.setStatus(BaseLogVo.ResponseSatus.fail.name());
		if (log.isErrorEnabled())
			log.error(JacksonUtil.obj2json(logV));
		if (log.isDebugEnabled())
			log.debug(JacksonUtil.obj2json(logV));
	}

	/**
	 * 环绕通知实现
	 * 
	 * @param pjp
	 * @return
	 * @throws Throwable
	 */
//	@Around("@annotation(cn.com.compass.web.annotation.BaseControllerLog)")
//	public Object aroundLog(ProceedingJoinPoint pjp) throws Throwable {
//		
//		return null;
//	}

	/**
	 * 获取注解中对方法的描述信息
	 *
	 * @param joinPoint
	 *            切点
	 * @return 方法描述
	 * @throws Exception
	 */
	private static BaseLog getMethodLogAnnotation(JoinPoint joinPoint) throws Exception {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		return  method.getAnnotation(BaseLog.class);
	}

	/**
	 * 获取请求的方法名全路径
	 * 
	 * @param joinPoint
	 * @return
	 */
	private static String getFullMethodName(JoinPoint joinPoint) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		// 请求的方法名全路径
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = signature.getName();
		return className + "." + methodName + "()";
	}

	@Override
	public int getOrder() {
		return HIGHEST_PRECEDENCE+1;
	}

}
