package cn.com.compass.autoconfig.global;

import cn.com.compass.autoconfig.security.Subject;

public class GlobalConstant {
	
	/**
	 * 当前系统环境，dev模式默认免登录
	 */
	public enum Mode {
		dev,pro,beta
	}
	
	/**
	 * root 用户账号
	 */
	public static final String ROOT = "root";
	/**
	 * root 用户 subject
	 */
	public static final Subject ROOT_SUBJECT = new Subject(-1L,"root");
	
	/**
	 * root 用户角色
	 */
	public static final String ROOT_ROLE = "root";
	
	/**
	 * root用户权限
	 */
	public static final String ROOT_PERMISSION = "all";

}
