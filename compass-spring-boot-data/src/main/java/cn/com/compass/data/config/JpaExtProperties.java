/**
 * 
 */
package cn.com.compass.data.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 扩展jpa属性，添加逻辑删除控制开关
 * @date 2018年11月1日 上午11:12:50
 * 
 */
@Data
@EnableConfigurationProperties(JpaExtProperties.class)
@ConfigurationProperties(prefix = JpaExtProperties.prefix)
public class JpaExtProperties {
	
	public static final String prefix = "spring.jpa";
	/**
	 * 是否打开逻辑删除
	 */
	private boolean openLogicDelete = false;

}
