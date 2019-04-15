package cn.com.compass.web.interceptor.default_;

import cn.com.compass.web.interceptor.IBaseAfterConcurrentHandlingStartedInterceptor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo
 * @date 2019/4/15 10:42
 */
@Component
public class BaseAfterConcurrentHandlingStartedInterceptorImpl implements IBaseAfterConcurrentHandlingStartedInterceptor {
    /**
     * @param request
     * @param response
     * @param handler
     * @throws Exception
     */
    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

    }
}
