package cn.com.compass.web.interceptor.default_;

import cn.com.compass.base.context.BaseSubjectContext;
import cn.com.compass.web.interceptor.IBaseAfterCompletionInterceptor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo
 * @date 2019/4/15 10:43
 */
@Component
public class BaseAfterCompletionInterceptorImpl implements IBaseAfterCompletionInterceptor {
    /**
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 请求BaseSubjectContext
        BaseSubjectContext.clearBaseSubject();
    }
}
