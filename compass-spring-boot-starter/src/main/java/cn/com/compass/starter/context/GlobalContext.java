package cn.com.compass.starter.context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.com.compass.base.constant.BaseConstant;
import cn.com.compass.base.vo.BaseSubject;
import cn.com.compass.util.JacksonUtil;
/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 全局上线文工具类，获取request、response、token、subject信息
 * @date 2018年6月6日 下午3:54:57
 *
 */
@Component
public class GlobalContext {


	public HttpServletRequest getRequest() {
		return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
	}
	
	public HttpServletResponse getResponse() {
		return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
	}

	public HttpSession getSession() {
		return this.getRequest().getSession();
	}
	
	public Object getSessionAttribute( String attr ) {
		HttpSession session = this.getSession();
		return session.getAttribute(attr);
	}
	
	public void removeSessionAttribute(String attr) {
		HttpSession session = this.getSession();
		session.removeAttribute( attr );
	}

	public void setSessionAttribute( String attr, Object value ) {
		HttpSession session = this.getSession();
		session.setAttribute(attr, value);
	}
	
	public void setRequestAttribute( String attr, Object value ) {
		this.getRequest().setAttribute(attr, value);
	}
	
	public Object getRequestAttribute( String attr ) {
		return this.getRequest().getAttribute(attr);
	}
	
	public void removeRequestAttribute( String attr ) {
		this.getRequest().removeAttribute(attr);
	}
	
	public BaseSubject getGlobalSubject() throws Exception {
		String suject = this.getRequest().getHeader(BaseConstant.REQUEST_SUBJECT_ATTRIBUTE_KEY);
		return JacksonUtil.json2pojo(suject, BaseSubject.class);
	}
}
