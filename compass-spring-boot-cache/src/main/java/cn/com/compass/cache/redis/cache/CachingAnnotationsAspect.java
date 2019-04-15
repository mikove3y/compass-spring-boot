package cn.com.compass.cache.redis.cache;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.BridgeMethodResolver;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.*;


/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo Cacheable注解拦截，用于注册方法信息
 * @date 2018年8月5日 上午12:21:50
 *
 */
@Aspect
@Component
public class CachingAnnotationsAspect {

    @Autowired
    private CacheSupport cacheSupport;
    
    /**
     * 获取方法注解
     * @param ae
     * @param annotationType
     * @return
     */
    private <T extends Annotation> List<T> getMethodAnnotations(AnnotatedElement ae, Class<T> annotationType) {
        List<T> anns = new ArrayList<T>(2);
        // look for raw annotation
        T ann = ae.getAnnotation(annotationType);
        if (ann != null) {
            anns.add(ann);
        }
        // look for meta-annotations
        for (Annotation metaAnn : ae.getAnnotations()) {
            ann = metaAnn.annotationType().getAnnotation(annotationType);
            if (ann != null) {
                anns.add(ann);
            }
        }
        return (anns.isEmpty() ? null : anns);
    }
    
    /**
     * 获取方法
     * @param pjp
     * @return
     */
    private Method getSpecificmethod(ProceedingJoinPoint pjp) {
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method method = methodSignature.getMethod();
        // The method may be on an interface, but we need attributes from the
        // target class. If the target class is null, the method will be
        // unchanged.
        Class<?> targetClass = AopProxyUtils.ultimateTargetClass(pjp.getTarget());
        if (targetClass == null && pjp.getTarget() != null) {
            targetClass = pjp.getTarget().getClass();
        }
        Method specificMethod = ClassUtils.getMostSpecificMethod(method, targetClass);
        // If we are dealing with method with generic parameters, find the
        // original method.
        specificMethod = BridgeMethodResolver.findBridgedMethod(specificMethod);
        return specificMethod;
    }
    
    /**
     * 切点
     */
    @Pointcut("@annotation(org.springframework.cache.annotation.Cacheable)")
    public void pointcut() {
    }
    
    /**
     * 注册缓存
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("pointcut()")
    public Object registerInvocation(ProceedingJoinPoint joinPoint) throws Throwable {

        Method method = this.getSpecificmethod(joinPoint);

        List<Cacheable> annotations = this.getMethodAnnotations(method, Cacheable.class);

        Set<String> cacheSet = new HashSet<String>();
        String cacheKey = null;
        for (Cacheable cacheables : annotations) {
            cacheSet.addAll(Arrays.asList(cacheables.value()));
            cacheKey = cacheables.key();
        }

        if (joinPoint.getSignature() instanceof MethodSignature) {
            Class<?>[] parameterTypes = ((MethodSignature) joinPoint.getSignature()).getParameterTypes();
            cacheSupport.registerInvocation(joinPoint.getTarget(), method, parameterTypes,
                    joinPoint.getArgs(), cacheSet, cacheKey);
        }

        return joinPoint.proceed();

    }
}