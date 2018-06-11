package cn.com.compass.autoconfig.constant;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;
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

	private boolean enabled = true;
	
	private Map<String,String> enmus = new HashMap<>();
}
