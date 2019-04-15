package cn.com.compass.base.vo;

import java.io.Serializable;

import lombok.*;

/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 基类错误vo
 * @date 2018年8月7日 下午7:48:21
 *
 */
@Data
public class BaseErroVo implements Serializable {

	private static final long serialVersionUID = -1250873387694075563L;
	
	private String status;

	private String error;
	
	public BaseErroVo(String status,String error) {
		this.status = status;
		this.error = error;
	}
	
	public BaseErroVo() {
		
	}

}
