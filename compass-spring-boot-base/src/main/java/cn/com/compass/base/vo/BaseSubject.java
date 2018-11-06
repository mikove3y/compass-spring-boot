package cn.com.compass.base.vo;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import cn.com.compass.base.constant.IBaseBizStatusEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 用户subject
 * @date 2018年6月27日 下午2:34:00
 *
 */
@Getter
@Setter
public class BaseSubject implements Serializable {

	private static final long serialVersionUID = -5471963320229357457L;
	/**
	 * 授权码key
	 */
	public static final String GREANT_ACCESS_TOKEN = "accessToken";
	/**
	 * 刷新码key
	 */
	public static final String GRANT_REFRESH_TOKEN = "refreshToken";
	/**
	 * 用户Id
	 */
	private Long userId;
	/**
	 * 组织Id
	 */
	private Long orgId;
	/**
	 * 用户账号
	 */
	private String account;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 用户角色
	 */
	private List<Long> authorities;
	/**
	 * 客户端类型
	 */
	private ClientType clientType;
	/**
	 * 授权类型
	 */
	private String grantType;
	
	
	public BaseSubject() {
		
	}
	
	public BaseSubject(Long userId,Long orgId,String account,String userName,ClientType clientType,List<Long> authorities,String grantType) {
		this.userId = userId;
		this.orgId = orgId;
		this.account = account;
		this.userName = userName;
		this.clientType = clientType;
		this.authorities = authorities;
		this.grantType = grantType;
	}
	
	/**
	 * 客户端类型枚举
	 */
	public enum ClientType implements IBaseBizStatusEnum{
		APP(1, "APP"), PC(2, "PC"),ALL(3,"ALL");
		private final Integer code;

		private final String des;
		
		private ClientType(Integer code,String des) {
			this.code= code;
			this.des = des;
		}
		@Override
		public Integer getCode() {
			return code;
		}
		
		@Override
		public String getDes() {
			return des;
		}
		
	}
	
	/**
	 * 获取AccessToken 缓存key
	 * @return
	 */
	@JsonIgnore
	public String getAccessTokenCacheKey() {
		return this.getAccount()+":"+this.getClientType()+":"+GREANT_ACCESS_TOKEN;
	}
	
	/**
	 * 获取RefreshToken 缓存key
	 * @return
	 */
	@JsonIgnore
	public String getRefreshTokenCacheKey() {
		return this.getAccount()+":"+this.getClientType()+":"+GRANT_REFRESH_TOKEN;
	}
	
}
