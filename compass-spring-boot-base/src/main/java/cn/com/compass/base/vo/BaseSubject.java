package cn.com.compass.base.vo;

import java.io.Serializable;
import java.util.List;

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
	 * 用户账号
	 */
	private String account;
	/**
	 * 用户角色
	 */
	private List<String> authorities;
	
	public BaseSubject() {
		
	}
	
	public BaseSubject(Long userId,String account,List<String> authorities) {
		this.userId = userId;
		this.account = account;
		this.authorities = authorities;
	}

}
