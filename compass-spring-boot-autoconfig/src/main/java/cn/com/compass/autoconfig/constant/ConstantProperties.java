package cn.com.compass.autoconfig.constant;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;
/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 常量属性
 * @date 2018年6月6日 下午2:51:56
 *
 */
@Data
@ConfigurationProperties(prefix = ConstantProperties.prefix)
public class ConstantProperties {

	public static final String prefix = "constant";
	/**
	 * 是否启用
	 */
	private boolean enabled = true;
	/**
	 * 激活语言
	 */
	private Lang lang = Lang.CN;
	/**
	 * 配置
	 */
	private Map<Lang,Map<String,String>> enums = new HashMap<>();

}
