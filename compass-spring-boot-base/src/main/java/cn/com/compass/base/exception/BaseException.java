package cn.com.compass.base.exception;

import lombok.Getter;
/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 基类错误封装，errorCode结合ConstantUtil使用，做到通赔
 * @date 2018年6月6日 下午2:48:29
 *
 */
@Getter
public class BaseException extends RuntimeException{
	
	private static final long serialVersionUID = 7880900519448193208L;
	
	private String errorCode;
	
	public BaseException(String errorCode,String message) {
		super( message );
		this.errorCode = errorCode;
	}
	
	public BaseException(String errorCode,Throwable cause) {
		super( cause );
		this.errorCode = errorCode;
	}
	
	public BaseException(String errorCode,String message,Throwable cause) {
		super( message, cause );
		this.errorCode = errorCode;
	}

}
