package cn.com.compass.base.vo;

import java.io.Serializable;

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
	
	public BaseSubject() {
		
	}
	
	public BaseSubject(Long userId,Long orgId,String account) {
		this.userId = userId;
		this.orgId = orgId;
		this.account = account;
	}

}
