package cn.com.compass.autoconfig.global;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 全局变量属性
 * @date 2018年6月6日 下午2:53:37
 *
 */
@Data
@ConfigurationProperties(prefix = GlobalProperties.prefix)
public class GlobalProperties {
	
	public static final String prefix = "global";
	
	private boolean enabled = true;
	
	/**
	 * {@link cn.com.compass.autoconfig.global.GlobalConstant.Mode}
	 * dev 模式 免登录
	 */
	private GlobalConstant.Mode mode = GlobalConstant.Mode.dev;
	
	/**
	 * root密码加密方式des，deskey
	 */
	private String desKey;
	
	/**
	 * root最高权限账号密码，des加密{@link cn.com.compass.util.DESUtil}
	 */
	private String rootPassword;
	
}
