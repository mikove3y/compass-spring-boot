package cn.com.compass.base.vo;

import java.io.Serializable;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseLogVo implements Serializable{

	private static final long serialVersionUID = 1L;
	/**
	 * logId 系统编号+时间戳
	 */
	private String logId;
	/**
	 * 日志编码
	 */
	private String logCode;
	/**
	 * 日志描述
	 */
	private String logDes;
	/**
	 * 操作人信息subject
	 */
	private Object subject;
	/**
	 * 处理请求的handler类.方法
	 */
	private String fullMethodName;
	/**
	 * 请求地址
	 */
	private String remoteAddress;
	/**
	 * 请求参数
	 */
	private String requestParams;
	/**
	 * 操作开始时间
	 */
	private String operateStartTime;
	/**
	 * 操作结束时间
	 */
	private String operateEndTime;
	/**
	 * 耗时
	 */
	private String useTime;
	/**
	 * 响应参数
	 */
	private String responseData;
	/**
	 * 错误详情
	 */
	private String erroMsg;
	/**
	 * 响应状态{@link cn.com.compass.core.vo.BaseLogVo.ResponseSatus}
	 */
	private String status;
	
	/**
	 * 响应状态码 0 失败 1成功
	 */
	public enum ResponseSatus {
		success,fail
	}
	
}
