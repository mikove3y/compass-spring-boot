package cn.com.compass.web.aop;

import cn.com.compass.autoconfig.constant.ConstantUtil;
import cn.com.compass.base.constant.BaseConstant;
import cn.com.compass.base.exception.BaseException;
import cn.com.compass.base.vo.BaseResponseVo;
import cn.com.compass.base.vo.BaseSubject;
import cn.com.compass.web.annotation.BasePermission;
import cn.com.compass.web.context.GlobalContext;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

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
public class BasePermissionAspect implements Ordered {

    @Autowired
    private GlobalContext context;
    @Autowired
    private ConstantUtil constantUtil;

    /**
     * 环绕通知
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around("@annotation(cn.com.compass.web.annotation.BasePermission)")
    public Object aroundLog(ProceedingJoinPoint pjp) throws Throwable {
        try{
            MethodSignature signature = (MethodSignature) pjp.getSignature();
            BasePermission permission = AnnotationUtils.findAnnotation(signature.getMethod(),BasePermission.class);
            if(permission.needAuth()){
                BaseSubject sub = context.getGlobalSubject();
                if(sub==null){
                    return new BaseResponseVo(BaseConstant.TOKEN_GET_ERRO,constantUtil.getValue(BaseConstant.TOKEN_GET_ERRO),"this api [" + context.getRequest().getRequestURI() + " " + context.getRequest().getMethod().toUpperCase() + "]  need request head param Authorization,but you can't set it or it's not access!");
                }
            }else{
                return pjp.proceed();
            }
        }catch (Exception e){
            log.error("aroundLog,error:{}",e);
        }
        return null;
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
        return HIGHEST_PRECEDENCE+2;
    }
}
