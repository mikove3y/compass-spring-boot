package cn.com.compass.web.context;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import cn.com.compass.base.constant.BaseConstant;
import cn.com.compass.base.vo.BaseSubject;
import cn.com.compass.base.vo.BaseSubject.ClientType;
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
	
	/**
	 * 获取当前用户消息Id
	 * @return
	 */
	public String getCurentUserMessageId() {
		return this.getRequest().getHeader(BaseConstant.MESSAGEID);
	}
	
	/**
	 * 获取当前用户数据权限
	 * @return
	 */
	public String getCurrentUserDataScop() {
		return this.getRequest().getHeader(BaseConstant.REQUEST_DATA_PERMISSION);
	}
	
	/**
	 * 获取用户token
	 * @return
	 */
	public String getCurrentUserToken() {
		return this.getRequest().getHeader(BaseConstant.AUTHORIZATION_KEY);
	}
	
	/**
	 * 获取当前用户power
	 * @return
	 */
	public String getCurrentUserPower() {
		return this.getRequest().getHeader(BaseConstant.POWER_KEY);
	}
	
	/**
	 * 获取子系统开发者账号
	 * @return
	 */
	public String getCurrentSysDeveloper() {
		return this.getRequest().getHeader(BaseConstant.SYSDEVELOPER_KEY);
	}
	
	/**
	 * 获取subject
	 * @return
	 */
	public BaseSubject getGlobalSubject() {
		try {
			String suject = this.getRequest().getHeader(BaseConstant.REQUEST_SUBJECT_ATTRIBUTE_KEY);
			if(StringUtils.isNotEmpty(suject)/*&&JacksonUtil.isJSONValid(suject)*/) {
				return JacksonUtil.json2pojo(suject, BaseSubject.class);
			}
		} catch (Exception e) {
			log.error("get BaseSubject from request header erro:{}", e);
		}
		return null;
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
	 * 获取用户账号
	 * @return
	 */
	public String getCurrentUserAccount() {
		BaseSubject sub = this.getGlobalSubject();
		return sub!=null?sub.getAccount():null;
	}
	
	/**
	 * 获取用户名
	 * @return
	 */
	public String getCurrentUserName() {
		BaseSubject sub = this.getGlobalSubject();
		return sub!=null?sub.getUserName():null;
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
	
	/**
	 * 获取客户端类型
	 * @return
	 */
	public ClientType getCurrentUserClientType() {
		BaseSubject sub = this.getGlobalSubject();
		return sub!=null?sub.getClientType():null;
	}
	
	/**
	 * 获取授权类型
	 * @return
	 */
	public String getCurrentUserGrantType() {
		BaseSubject sub = this.getGlobalSubject();
		return sub!=null?sub.getGrantType():null;
	}
	
}
