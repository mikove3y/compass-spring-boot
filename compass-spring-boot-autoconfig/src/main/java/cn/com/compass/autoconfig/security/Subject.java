package cn.com.compass.autoconfig.security;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 系统token subject
 * @date 2018年6月6日 下午3:40:57
 *
 */
@Getter
@Setter
public class Subject implements Serializable{

	private static final long serialVersionUID = -3226588652212231875L;
	
	private Long userId;
	
	private String account;
	
	public Subject() {
		
	}
	
	public Subject(Long userId,String account) {
		this.userId = userId;
		this.account = account;
	}

}
