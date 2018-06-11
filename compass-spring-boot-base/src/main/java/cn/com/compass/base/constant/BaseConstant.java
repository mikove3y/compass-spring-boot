package cn.com.compass.base.constant;

/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 基类常量类
 * @date 2018年6月6日 下午2:51:11
 *
 */
public interface BaseConstant {
	
	/**
	 * 放在request Attribute中传递参数用，用于记录日志操作人，用于处理jpa公共字段操作人等信息
	 */
	public static final String REQUEST_SUBJECT_ATTRIBUTE_KEY = "SUBJECT";
	
	/**
	 * 放在request头中的权限key
	 */
	public static final String AUTHORIZATION_KEY = "Authorization";
	
	// yml constant 配置
	
	/**
	 * 请求成功
	 */
	public static final String SUCCESS = "base-0001";
	/**
	 * 请求参数校验不通过
	 */
	public static final String REQUEST_PARAMS_VALID_ERRO = "base-0002";
	/**
	 * 响应数据转json失败
	 */
	public static final String RESPONSE_DATA_TO_JSON_ERRO = "base-0003";
	/**
	 * 系统参数不合法
	 */
	public static final String ILLEGAL_ARGUMENT = "base-0004";
}
