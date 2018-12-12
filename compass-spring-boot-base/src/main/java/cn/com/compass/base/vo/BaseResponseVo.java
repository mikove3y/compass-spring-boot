package cn.com.compass.base.vo;

import java.io.Serializable;

import cn.com.compass.base.constant.BaseConstant;
import lombok.Getter;

@Getter
public class BaseResponseVo<T> implements Serializable {

	private static final long serialVersionUID = -4308759780635946860L;
	
	private static final String SUCCESS = "success";
	
	private String status;

	private String msg;

	private T data;
	
	public BaseResponseVo() {
		
	}
	
	public BaseResponseVo(String status) {
		this.status = status;
	}
	
	public BaseResponseVo(String status,String msg) {
		this.status = status;
		this.msg = msg;
	}
	
	public BaseResponseVo(String status,String msg,T data) {
		this.status = status;
		this.msg = msg;
		this.data = data;
	}

	public BaseResponseVo setStatus(String status) {
		this.status = status;
		return this;
	}

	public BaseResponseVo setMsg(String msg) {
		this.msg = msg;
		return this;
	}

	public BaseResponseVo setData(T data) {
		this.data = data;
		return this;
	}
	
	public static BaseResponseVo success() {
		return new BaseResponseVo(BaseConstant.SUCCESS).setMsg(SUCCESS);
	}
	
}
