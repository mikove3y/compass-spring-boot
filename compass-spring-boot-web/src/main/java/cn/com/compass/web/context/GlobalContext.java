package cn.com.compass.web.context;

import cn.com.compass.base.constant.BaseConstant;
import cn.com.compass.base.context.BaseSubjectContext;
import cn.com.compass.base.vo.BaseSubject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 全局上线文工具类，获取request、response、token、subject信息
 * @date 2018年6月6日 下午3:54:57
 */
@Component
@Slf4j
public class GlobalContext {

    @Resource
    private HttpServletRequest request;

    @Resource
    private HttpServletResponse response;

    public HttpServletRequest getRequest() {
        return request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public HttpSession getSession() {
        return this.getRequest().getSession();
    }

    public Object getSessionAttribute(String attr) {
        HttpSession session = this.getSession();
        return session.getAttribute(attr);
    }

    public void removeSessionAttribute(String attr) {
        HttpSession session = this.getSession();
        session.removeAttribute(attr);
    }

    public void setSessionAttribute(String attr, Object value) {
        HttpSession session = this.getSession();
        session.setAttribute(attr, value);
    }

    public void setRequestAttribute(String attr, Object value) {
        this.getRequest().setAttribute(attr, value);
    }

    public Object getRequestAttribute(String attr) {
        return this.getRequest().getAttribute(attr);
    }

    public void removeRequestAttribute(String attr) {
        this.getRequest().removeAttribute(attr);
    }

    /**
     * 获取当前消息Id
     *
     * @return
     */
    public String getCurrentMessageId() {
        return this.getRequest().getHeader(BaseConstant.MESSAGE_ID);
    }

    /**
     * 获取当前token
     *
     * @return
     */
    public String getCurrentToken() {
        return this.getRequest().getHeader(BaseConstant.AUTHORIZATION_KEY);
    }

    /**
     * 获取subject
     *
     * @return
     */
    public BaseSubject getGlobalSubject() {
        return BaseSubjectContext.getBaseSubject();
    }

    /**
     * 获取当前用户信息
     * @return
     */
    public Serializable getCurrentUserId(){
        return this.getGlobalSubject()!=null?this.getGlobalSubject().getUserId():null;
    }

}