package cn.com.compass.web.interceptor.default_;

import cn.com.compass.autoconfig.jwt.JwtUtil;
import cn.com.compass.base.constant.BaseConstant;
import cn.com.compass.base.context.BaseSubjectContext;
import cn.com.compass.base.util.JacksonUtil;
import cn.com.compass.base.vo.BaseSubject;
import cn.com.compass.web.interceptor.IBasePreHandleInterceptor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo
 * @date 2019/4/15 10:41
 */
@Component
public class BasePreHandleInterceptorImpl implements IBasePreHandleInterceptor {


    @Autowired
    private JwtUtil jwtUtil;
    /**
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 设置BaseSubjectContext
        String token = request.getHeader(BaseConstant.AUTHORIZATION_KEY);
        if (StringUtils.isNotEmpty(token)) {
            String sub = jwtUtil.parseSubject(token);
            BaseSubject sub_ = JacksonUtil.json2pojo(sub, BaseSubject.class);
            BaseSubjectContext.setBaseSubject(sub_);
        }
        return true;
    }
}
