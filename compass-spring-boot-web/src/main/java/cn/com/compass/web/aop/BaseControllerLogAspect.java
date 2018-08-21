package cn.com.compass.web.aop;

import java.lang.reflect.Method;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import cn.com.compass.base.constant.BaseConstant;
import cn.com.compass.base.vo.BaseLogVo;
import cn.com.compass.util.DateUtil;
import cn.com.compass.util.ExceptionUtil;
import cn.com.compass.util.JacksonUtil;
import cn.com.compass.web.annotation.BaseControllerLog;
import lombok.extern.slf4j.Slf4j;
/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo controller日志切面
 * @date 2018年6月6日 下午3:58:29
 *
 */
@Aspect
@Component
@Slf4j
public class BaseControllerLogAspect {
	
	
	private ThreadLocal<BaseLogVo> logLocal = new ThreadLocal<>();
	
	@Resource
	private HttpServletRequest request;
	
	/**
	 * 定义日志切入点
	 */
	@Pointcut("@annotation(cn.com.compass.web.annotation.BaseControllerLog)")
	public void controllerLogPointCut() {
	}

	/**
	 * 后置通知 用于拦截service层记录用户的操作
	 *
	 * @param joinPoint
	 *            切点
	 */
	@Before("controllerLogPointCut()")
	public void doBefore(JoinPoint joinPoint) {
		try {
			BaseLogVo logV = new BaseLogVo();
			logV.setLogId(System.currentTimeMillis()+"-"+"应用码");
			logV.setOperateStartTime(DateUtil.getCurrentDateTime());
			logV.setFullMethodName(getFullMethodName(joinPoint));
			BaseControllerLog anno = getMethodLogAnnotation(joinPoint);
			logV.setLogCode(anno.code());
			logV.setLogDes(anno.desc());
			logV.setRequestParams(JacksonUtil.obj2json(joinPoint.getArgs()));
			logV.setRemoteAddress(request.getRemoteAddr());
			Object subject = request.getAttribute(BaseConstant.REQUEST_SUBJECT_ATTRIBUTE_KEY);
			if(subject!=null) {
				logV.setSubject(subject);
			}
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
	@AfterReturning(returning = "ret", pointcut = "controllerLogPointCut()")
	public void doAfterReturning(JoinPoint joinPoint,Object ret) throws Throwable {
		BaseLogVo logV = logLocal.get();
		logV.setOperateEndTime(DateUtil.getCurrentDateTime());
		logV.setUseTime(DateUtil.subtract(logV.getOperateStartTime(), logV.getOperateEndTime()) + "秒");
		logV.setResponseData(JacksonUtil.obj2json(ret));
		logV.setStatus(BaseLogVo.ResponseSatus.success.name());
		log.info(JacksonUtil.obj2json(logV));
		logLocal.remove();
	}

	/**
	 * 异常通知 用于拦截service层记录异常日志
	 *
	 * @param joinPoint
	 * @param e
	 */
	@AfterThrowing(pointcut = "controllerLogPointCut()", throwing = "e")
	public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
		try {
			BaseLogVo logV = logLocal.get();
			logV.setOperateEndTime(DateUtil.getCurrentDateTime());
			logV.setUseTime(DateUtil.subtract(logV.getOperateStartTime(), logV.getOperateEndTime()) + "秒");
			logV.setErroMsg(ExceptionUtil.stackTraceToString(e));
			logV.setStatus(BaseLogVo.ResponseSatus.fail.name());
			log.error(JacksonUtil.obj2json(logV));
		} catch (Exception ex) {
			log.error("==日志后置通知异常==");
			log.error("异常信息:{}", e.getMessage());
		} finally {
			logLocal.remove();
		}
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
	private static BaseControllerLog getMethodLogAnnotation(JoinPoint joinPoint) throws Exception {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		return  method.getAnnotation(BaseControllerLog.class);
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

}
