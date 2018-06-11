package cn.com.compass.base.vo;

import java.io.Serializable;

import cn.com.compass.base.constant.BaseConstant;
import lombok.Getter;

@Getter
public class BaseResponseVo implements Serializable {

	private static final long serialVersionUID = -4308759780635946860L;
	
	private static final String SUCCESS = "success";
	
	private String code;

	private String msg;

	private Object data;
	
	public BaseResponseVo() {
		
	}
	
	public BaseResponseVo(String code) {
		this.code = code;
	}
	
	public BaseResponseVo(String code,String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	public BaseResponseVo(String code,String msg,Object data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public BaseResponseVo setCode(String code) {
		this.code = code;
		return this;
	}

	public BaseResponseVo setMsg(String msg) {
		this.msg = msg;
		return this;
	}

	public BaseResponseVo setData(Object data) {
		this.data = data;
		return this;
	}
	
	public static BaseResponseVo success() {
		return new BaseResponseVo(BaseConstant.SUCCESS).setMsg(SUCCESS);
	}
	
}
