package cn.com.compass.web.feign;

import cn.com.compass.base.constant.BaseConstant;
import cn.com.compass.web.context.GlobalContext;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 参数拦截器
 * @date 2018/12/3 11:36
 */
@Component
@Slf4j
public class FeignRequestInterceptor implements RequestInterceptor {
    /**
     * 全局上线文
     */
    @Autowired
    private GlobalContext context;
    /**
     * Called for every request. Add data using methods on the supplied {@link RequestTemplate}.
     *
     * @param template
     */
    @Override
    public void apply(RequestTemplate template) {
        /**
         * 规则:
         * 1、服务间调用A->B B并不会把相应的api暴露出去的情况下，不要使用版本管理
         */
        // 拦截三个参数 MessageId Authorization SystemCode
        // 判断是否是当前线程请求
        /**
         *
         * java.lang.IllegalStateException: No thread-bound request found: Are you referring to request attributes outside of an actual web request,
         * or processing a request outside of the originally receiving thread?
         * If you are actually operating within a web request and still receive this message,
         * your code is probably running outside of DispatcherServlet/DispatcherPortlet: In this case, use RequestContextListener or RequestContextFilter to expose the current request.
         *
         */
        try{
            template.header(BaseConstant.SYSTEMCODE, context.getCurrentUserSystemCode());
            template.header(BaseConstant.MESSAGEID, context.getCurentUserMessageId());
            template.header(BaseConstant.AUTHORIZATION_KEY, context.getCurrentUserToken());
        }catch (Exception e){
            // do nothing
//            log.error(e.getMessage());
        }
    }
}
