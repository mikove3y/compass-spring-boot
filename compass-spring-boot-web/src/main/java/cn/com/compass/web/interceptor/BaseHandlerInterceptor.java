package cn.com.compass.web.interceptor;

import cn.com.compass.web.context.AppContext;
import org.apache.commons.collections.MapUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo
 * @date 2019/4/15 9:58
 */
public class BaseHandlerInterceptor extends HandlerInterceptorAdapter {


    /**
     *
     * @param request
     * @param response
     * @param handler
     * @throws Exception
     */
    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        super.afterConcurrentHandlingStarted(request, response, handler);
        Map<String, IBaseAfterConcurrentHandlingStartedInterceptor> beans = AppContext.getInstance().getBeansOfType(IBaseAfterConcurrentHandlingStartedInterceptor.class);
        if(MapUtils.isNotEmpty(beans)){
            for(Map.Entry<String, IBaseAfterConcurrentHandlingStartedInterceptor> b : beans.entrySet()){
                b.getValue().afterConcurrentHandlingStarted(request,response,handler);
            }
        }
    }

    /**
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map<String, IBasePreHandleInterceptor> beans = AppContext.getInstance().getBeansOfType(IBasePreHandleInterceptor.class);
        boolean result = false;
        if(MapUtils.isNotEmpty(beans)){
            for(Map.Entry<String, IBasePreHandleInterceptor> b : beans.entrySet()){
                result = b.getValue().preHandle(request,response,handler);
            }
        }else{
            result = super.preHandle(request, response, handler);
        }
        return result;
    }

    /**
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
        Map<String, IBasePostHandleInterceptor> beans = AppContext.getInstance().getBeansOfType(IBasePostHandleInterceptor.class);
        if(MapUtils.isNotEmpty(beans)){
            for(Map.Entry<String, IBasePostHandleInterceptor> b : beans.entrySet()){
                b.getValue().postHandle(request,response,handler,modelAndView);
            }
        }
    }

    /**
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
        Map<String, IBaseAfterCompletionInterceptor> beans = AppContext.getInstance().getBeansOfType(IBaseAfterCompletionInterceptor.class);
        if(MapUtils.isNotEmpty(beans)){
            for(Map.Entry<String, IBaseAfterCompletionInterceptor> b : beans.entrySet()){
                b.getValue().afterCompletion(request,response,handler,ex);
            }
        }
    }
}
