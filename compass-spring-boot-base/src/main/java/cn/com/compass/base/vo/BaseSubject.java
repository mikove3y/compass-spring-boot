package cn.com.compass.base.vo;

import java.io.Serializable;
import java.util.List;

import cn.com.compass.base.constant.IBaseBizStatusEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo subject->jwt
 * @date 2018年6月27日 下午2:34:00
 *
 */
@Getter
@Setter
public class BaseSubject implements Serializable {

	private static final long serialVersionUID = -5471963320229357457L;
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
	 * 用户角色
	 */
	private List<String> authorities;
	/**
	 * 客户端类型
	 */
	private Integer clientType;
	/**
	 * 授权类型
	 */
	private String grantType;
	/**
	 * 数据权限对象,在相应的模块自行定义
	 */
	private Object dataFilter;
	
	public BaseSubject() {
		
	}
	
	public BaseSubject(Long userId,Long orgId,String account,Integer clientType,List<String> authorities,Object dataFilter) {
		this.userId = userId;
		this.orgId = orgId;
		this.account = account;
		this.clientType = clientType;
		this.authorities = authorities;
		this.dataFilter = dataFilter;
	}
	
	/**
	 * 客户端类型枚举
	 */
	public enum ClientType implements IBaseBizStatusEnum{
		APP(1, "APP"), PC(2, "PC");
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
	
	
	public static final String GREANT_ACCESS_TOKEN = "access_token";
	public static final String GRANT_REFRESH_TOKEN = "refresh_token";
}
