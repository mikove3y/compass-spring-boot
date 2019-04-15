package cn.com.compass.web.api;

import cn.com.compass.web.annotation.BaseApi;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo api版本请求处理映射
 * @date 2018/12/4 10:24
 */
public class ApiVersionRequestHandlerMapping extends RequestMappingHandlerMapping {

    /**
     * 获取类上的BaseApi RequestCondition
     * @param handlerType
     * @return
     */
    @Override
    protected RequestCondition<?> getCustomTypeCondition(Class<?> handlerType) {
        // BaseApi不注解在方法上
//        BaseApi apiVersion = AnnotationUtils.findAnnotation(handlerType, BaseApi.class);
//        if(apiVersion!=null&&apiVersion.versionManage()){
//            return new ApiVersionCondition(apiVersion.version());
//        }
        return super.getCustomTypeCondition(handlerType);
    }

    /**
     * 获取方法上的BaseApi RequestCondition
     * @param method
     * @return
     */
    @Override
    protected RequestCondition<?> getCustomMethodCondition(Method method) {
        BaseApi apiVersion = AnnotationUtils.findAnnotation(method, BaseApi.class);
        if(apiVersion!=null&&apiVersion.versionManage()){
            return new ApiVersionCondition(apiVersion.version());
        }
        return super.getCustomMethodCondition(method);
    }

}
