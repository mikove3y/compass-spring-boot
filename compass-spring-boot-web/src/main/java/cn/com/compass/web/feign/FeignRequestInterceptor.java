package cn.com.compass.web.feign;

import cn.com.compass.base.constant.BaseConstant;
import cn.com.compass.web.context.GlobalContext;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 参数拦截器
 * @date 2018/12/3 11:36
 */
@Component
public class FeignRequestInterceptor implements RequestInterceptor {

    @Autowired
    private GlobalContext context;
    /**
     * Called for every request. Add data using methods on the supplied {@link RequestTemplate}.
     *
     * @param template
     */
    @Override
    public void apply(RequestTemplate template) {
        // 拦截三个参数 MessageId Authorization SystemCode
        template.header(BaseConstant.SYSTEMCODE, context.getCurrentUserSystemCode());
        template.header(BaseConstant.MESSAGEID, context.getCurentUserMessageId());
        template.header(BaseConstant.AUTHORIZATION_KEY, context.getCurrentUserToken());
    }
}
