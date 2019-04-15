package cn.com.compass.activiti.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@ConfigurationProperties(prefix = ActivitiProperties.prefix)
public class ActivitiProperties {
	
	public static final String prefix = "activiti";
	/**
	 * 是否启用
	 */
	private boolean enabled = true;
	/**
	 * 数据库类型，默认mysql库
	 */
	private String database = "mysql";
	/**
	 * 流程字体，默认宋体
	 */
	private String font = "宋体";
	/**
	 * 是否生成库，默认生成
	 */
	private String databaseSchemaUpdate = "true";
	/**
	 * 是否执行定时任务，默认不执行
	 */
	private boolean jobExecutorActivate = false;
	/**
	 * 主键生成策略
	 */
	private String idGenerator;
	/**
	 * 事件监听器
	 */
	private List<String> eventListeners;
	/**
	 * 部署器
	 */
	private List<String> deployers;
	/**
	 * 流程图生成器
	 */
	private String processDiagramGenerator;
}
