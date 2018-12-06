package cn.com.compass.web.aop;

import cn.com.compass.base.vo.BaseLogVo;
import cn.com.compass.util.DateUtil;
import cn.com.compass.util.JacksonUtil;
import cn.com.compass.web.annotation.BaseLog;
import cn.com.compass.web.context.GlobalContext;
import cn.com.compass.web.util.WebUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 基础日志切面
 * @date 2018年6月6日 下午3:58:29
 */
//@Aspect
//@Component
@Slf4j
@Deprecated
public class BaseLogAspect implements Ordered {
	
	
	private ThreadLocal<BaseLogVo> logLocal = new ThreadLocal<>();

	/**
	 * 全局上线文
	 */
	@Autowired
	private GlobalContext context;
	
	/**
	 * 定义日志切入点
	 */
	@Pointcut("@annotation(cn.com.compass.web.annotation.BaseLog)")
	public void logPointCut() {
	}

	/**
	 * 前置通知 用于拦截controller层记录用户的操作
	 *
	 * @param joinPoint
	 *            切点
	 */
	@Before("logPointCut()")
	public void doBefore(JoinPoint joinPoint) {
		try {
			BaseLog baseLog = getMethodLogAnnotation(joinPoint);
			BaseLogVo logV = new BaseLogVo();
			// 请求开始时间
			logV.setOperateStartTime(DateUtil.getCurrentDateTime());
			// 请求方法全名
			logV.setFullMethodName(getFullMethodName(joinPoint));
			// 日志编码
			logV.setLogCode(baseLog.code());
			// 日志描述
			logV.setLogDes(baseLog.desc());
			// 请求参数
			logV.setRequestParams(joinPoint.getArgs());
			// 请求地址
			logV.setRemoteAddress(WebUtil.getIpAddr(context.getRequest()));
			// 用户信息
			logV.setSubject(context.getGlobalSubject());
			if(baseLog.printLog()){
				logLocal.set(logV);
			}
		} catch (Exception e) {
			log.error("日志前置通知异常:{}", e.getMessage());
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
		if(logV!=null){
			// 请求结束时间
			logV.setOperateEndTime(DateUtil.getCurrentDateTime());
			// 请求耗时
			logV.setUseTime(DateUtil.subtract(logV.getOperateStartTime(), logV.getOperateEndTime()) + "秒");
			// 请求响应结果
			logV.setResponseData(ret);
			// 请求状态
			logV.setStatus(BaseLogVo.ResponseSatus.success);
		}
		BaseLog baseLog = getMethodLogAnnotation(joinPoint);
		if(baseLog.printLog()){
			log.info(JacksonUtil.obj2json(logV));
		}
		// 移除缓存
		logLocal.remove();
	}

	/**
	 * 异常通知 用于拦截controller层记录异常日志
	 *
	 * @param joinPoint
	 * @param e
	 */
	@AfterThrowing(pointcut = "logPointCut()", throwing = "e")
	public void doAfterThrowing(JoinPoint joinPoint, Throwable e) throws Throwable {
		BaseLogVo logV = logLocal.get();
		if(logV!=null){
			logV.setOperateEndTime(DateUtil.getCurrentDateTime());
			logV.setUseTime(DateUtil.subtract(logV.getOperateStartTime(), logV.getOperateEndTime()) + "秒");
			logV.setErroMsg(e.getMessage());
			logV.setStatus(BaseLogVo.ResponseSatus.fail);
		}
		BaseLog baseLog = getMethodLogAnnotation(joinPoint);
		if(baseLog.printLog()){
			log.error(JacksonUtil.obj2json(logV));
		}
		// 移除缓存
		logLocal.remove();
	}

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
		return  AnnotationUtils.findAnnotation(signature.getMethod(), BaseLog.class);
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
