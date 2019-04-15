package cn.com.compass.base.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseLogVo implements Serializable{

	private static final long serialVersionUID = 1L;
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
	private Object requestParams;
	/**
	 * 请求api路径
	 */
	private String apiPath;
	/**
	 * 请求api版本
	 */
	private String apiVersion;
	/**
	 * 是否api版本管理
	 */
	private boolean apiVersionManage;
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
	private Object responseData;
	/**
	 * 错误详情
	 */
	private String erroMsg;
	/**
	 * 响应状态{@link ResponseSatus}
	 */
	private ResponseSatus status;
	
	/**
	 * 响应状态码 0 失败 1成功
	 */
	public enum ResponseSatus {
		success,fail
	}
	
}
