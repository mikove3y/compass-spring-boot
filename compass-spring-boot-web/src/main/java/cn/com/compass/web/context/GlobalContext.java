package cn.com.compass.web.context;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import cn.com.compass.base.constant.BaseConstant;
import cn.com.compass.base.exception.BaseException;
import cn.com.compass.base.vo.BaseSubject;
import cn.com.compass.util.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
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
	
	public BaseSubject getGlobalSubject() {
		try {
			String suject = this.getRequest().getHeader(BaseConstant.REQUEST_SUBJECT_ATTRIBUTE_KEY);
			if(StringUtils.isEmpty(suject)) {
				return null;
			}else
			return JacksonUtil.json2pojo(suject, BaseSubject.class);
		} catch (Exception e) {
			log.error("get BaseSubject from request header erro:{}", e);
			throw new BaseException(BaseConstant.TOKEN_GET_ERRO, e);
		}
	}
	
	/**
	 * 获取用户Id
	 * @return
	 */
	public Long getCurrentUserId() {
		BaseSubject sub = this.getGlobalSubject();
		return sub!=null?sub.getUserId():null;
	}
	
	/**
	 * 获取公司Id
	 * @return
	 */
	public Long getCurrentUserOrgId() {
		BaseSubject sub = this.getGlobalSubject();
		return sub!=null?sub.getOrgId():null;
	}
	
	/**
	 * 获取角色
	 * @return
	 */
	public List<String> getCurrentUserAuthorities() {
		BaseSubject sub = this.getGlobalSubject();
		return sub!=null?sub.getAuthorities():null;
	}
}
