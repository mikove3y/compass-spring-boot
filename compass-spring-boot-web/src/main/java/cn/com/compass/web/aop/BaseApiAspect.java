package cn.com.compass.web.aop;

import cn.com.compass.autoconfig.constant.ConstantUtil;
import cn.com.compass.base.constant.BaseConstant;
import cn.com.compass.base.exception.BaseException;
import cn.com.compass.base.vo.BaseLogVo;
import cn.com.compass.base.vo.BaseSubject;
import cn.com.compass.util.DateUtil;
import cn.com.compass.util.JacksonUtil;
import cn.com.compass.web.annotation.BaseApi;
import cn.com.compass.web.annotation.BaseLog;
import cn.com.compass.web.context.GlobalContext;
import cn.com.compass.web.util.WebUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo
 * @date 2018/12/3 17:11
 */
@Aspect
@Component
@Slf4j
public class BaseApiAspect implements Ordered {

    /**
     * 缓存
     */
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
     * 全局上线文
     */
    @Autowired
    private GlobalContext context;
    /**
     * 常量工具类
     */
    @Autowired
    private ConstantUtil constantUtil;
    /**
     * 定义日志切入点
     */
    @Pointcut("@annotation(cn.com.compass.web.annotation.BaseApi)")
    public void apiPointCut() {
    }


    /**
     * 前置通知 用于拦截controller层记录用户的操作
     *
     * @param joinPoint
     *            切点
     */
    @Before("apiPointCut()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        BaseApi api = this.getMethodBaseApiAnnotation(joinPoint);
        if(api.printLog()){
            BaseLogVo logV = new BaseLogVo();
            // 请求开始时间
            logV.setOperateStartTime(DateUtil.getCurrentDateTime());
            // 请求方法全名
            logV.setFullMethodName(getFullMethodName(joinPoint));
            // 日志编码
            logV.setLogCode(api.code());
            // 日志描述
            logV.setLogDes(api.desc());
            // 请求参数
            logV.setRequestParams(joinPoint.getArgs());
            // 请求地址
            logV.setRemoteAddress(WebUtil.getIpAddr(context.getRequest()));
            // 用户信息
            logV.setSubject(context.getGlobalSubject());
            // 应用名
            logV.setApplication(application);
            // 应用地址
            logV.setAppAddress(appAddress);
            // apiPath
            logV.setApiPath(context.getRequest().getRequestURI());
            // apiVersion
            logV.setApiVersion("v"+api.version());
            // apiVersionManage
            logV.setApiVersionManage(api.versionManage());
            logLocal.set(logV);
        }
        if (api!=null&&api.needAuth()) {
            BaseSubject sub = context.getGlobalSubject();
            if (sub == null) {
                throw new BaseException(BaseConstant.TOKEN_GET_ERRO, "this api [" + context.getRequest().getRequestURI() + " " + context.getRequest().getMethod().toUpperCase() + "]  need request head param Authorization,but you can't set it or it's not access!");
            }
        }
    }

    /**
     * 切入点return内容之后切入内容（可以用来对处理返回值做一些加工处理）
     *
     * @param ret
     * @throws Throwable
     */
    @AfterReturning(returning = "ret", pointcut = "apiPointCut()")
    public void doAfterReturning(JoinPoint joinPoint,Object ret) throws Throwable {
        BaseApi api = this.getMethodBaseApiAnnotation(joinPoint);
        if(api.printLog()){
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
                log.info(JacksonUtil.obj2json(logV));
                // 移除缓存
                logLocal.remove();
            }
        }
    }

    /**
     * 抛错后处理
     * @param joinPoint
     * @param e
     * @throws Throwable
     */
    @AfterThrowing(pointcut = "apiPointCut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e) throws Throwable {
        BaseApi api = this.getMethodBaseApiAnnotation(joinPoint);
        if(api.printLog()){
            BaseLogVo logV = logLocal.get();
            if(logV!=null){
                logV.setOperateEndTime(DateUtil.getCurrentDateTime());
                logV.setUseTime(DateUtil.subtract(logV.getOperateStartTime(), logV.getOperateEndTime()) + "秒");
                logV.setErroMsg(e.getMessage());
                logV.setStatus(BaseLogVo.ResponseSatus.fail);
                log.error(JacksonUtil.obj2json(logV));
                // 移除缓存
                logLocal.remove();
            }
        }
    }

    /**
     * 获取注解中对方法的描述信息
     *
     * @param joinPoint
     *            切点
     * @return 方法描述
     * @throws Exception
     */
    private  BaseApi getMethodBaseApiAnnotation(JoinPoint joinPoint) throws Exception {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        return  AnnotationUtils.findAnnotation(signature.getMethod(), BaseApi.class);
    }

    /**
     * 获取请求的方法名全路径
     *
     * @param joinPoint
     * @return
     */
    private String getFullMethodName(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // 请求的方法名全路径
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        return className + "." + methodName + "()";
    }

    /**
     * Get the order value of this object.
     * <p>Higher values are interpreted as lower priority. As a consequence,
     * the object with the lowest value has the highest priority (somewhat
     * analogous to Servlet {@code load-on-startup} values).
     * <p>Same order values will result in arbitrary sort positions for the
     * affected objects.
     *
     * @return the order value
     * @see #HIGHEST_PRECEDENCE
     * @see #LOWEST_PRECEDENCE
     */
    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE+1;
    }
}
