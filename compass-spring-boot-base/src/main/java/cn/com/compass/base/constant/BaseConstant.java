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
	 * 放在request头中的权限key
	 */
	public static final String AUTHORIZATION_KEY = "Authorization";
	/**
	 * 针对本平台系统
	 * auth2.0方式鉴权，匹配jwt-token
	 */
	public static final String AUTHORIZATION_VALUE_BEARER_PREFIX = "Bearer ";
	/**
	 * 全局消息Id
	 */
	public static final String MESSAGE_ID = "MessageId";

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
	/**
	 * 系统内部错误
	 */
	public static final String INNER_ERRO = "base-0005";
	/**
	 * token信息获取失败
	 */
	public static final String TOKEN_GET_ERRO = "base-0006";
	/**
	 * 请求 404
	 */
	public static final String API_NOT_FOUND = "base-0007";
	/**
	 * 服务不可达
	 */
	public static final String SERVER_NOT_AVAILABLE= "base-0008";
	/**
	 * 未知错误
	 */
	public static final String UNKONW_ERROR= "base-0009";
}
