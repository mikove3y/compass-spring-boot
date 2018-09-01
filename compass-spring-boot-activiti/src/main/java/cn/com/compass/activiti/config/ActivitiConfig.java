package cn.com.compass.activiti.config;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.activiti.engine.DynamicBpmnService;
import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.impl.cfg.IdGenerator;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.persistence.deploy.Deployer;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import cn.com.compass.activiti.org.activiti.image.HMProcessDiagramGenerator;
import cn.com.compass.base.constant.BaseConstant;
import cn.com.compass.base.exception.BaseException;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo activiti工作流配置
 * @date 2018年8月21日 上午11:26:50
 *
 */
@Configuration
@EnableConfigurationProperties(ActivitiProperties.class)
@ConditionalOnProperty(name = "activiti.enabled", matchIfMissing = true)
@Slf4j
public class ActivitiConfig {
	
	@Autowired
	private ActivitiProperties properties;
	
	//流程配置，与spring整合采用SpringProcessEngineConfiguration这个实现
	@Bean
    public ProcessEngineConfiguration processEngineConfiguration(DataSource dataSource, PlatformTransactionManager transactionManager){
        try {
        	SpringProcessEngineConfiguration processEngineConfiguration = new SpringProcessEngineConfiguration();
            processEngineConfiguration.setDataSource(dataSource);// 数据源
            processEngineConfiguration.setDatabaseSchemaUpdate(properties.getDatabaseSchemaUpdate());// 是否自定生成脚本
            processEngineConfiguration.setDatabaseType(properties.getDatabase());// 数据库类型
            processEngineConfiguration.setJobExecutorActivate(properties.isJobExecutorActivate());// 任务执行关闭
            processEngineConfiguration.setTransactionManager(transactionManager);// 是否控制器
            // 流程字体
            processEngineConfiguration.setActivityFontName(properties.getFont());
            processEngineConfiguration.setAnnotationFontName(properties.getFont());
            processEngineConfiguration.setLabelFontName(properties.getFont());
            
            // 主键生成策略
            if(properties.getIdGenerator()!=null) {
            	processEngineConfiguration.setIdGenerator((IdGenerator) Class.forName(properties.getIdGenerator()).newInstance());
            }
            
            // 监听器
            if(CollectionUtils.isNotEmpty(properties.getEventListeners())) {
            	List<ActivitiEventListener> listeners = new ArrayList<>();
            	for(String el : properties.getEventListeners()) {
            		listeners.add((ActivitiEventListener) Class.forName(el).newInstance());
            	}
            	processEngineConfiguration.setEventListeners(listeners);
            }
            
            // 流程图生成器
            if(properties.getProcessDiagramGenerator()!=null) {
            	processEngineConfiguration.setProcessDiagramGenerator((HMProcessDiagramGenerator) Class.forName(properties.getProcessDiagramGenerator()).newInstance());
            }
            
            // 自定义部署器
            if(CollectionUtils.isNotEmpty(properties.getDeployers())) {
            	List<Deployer> deployers = new ArrayList<>();
            	for(String d : properties.getDeployers()) {
            		deployers.add((Deployer) Class.forName(d).newInstance());
            	}
            	processEngineConfiguration.setCustomPostDeployers(deployers);
            }
            return processEngineConfiguration;
		} catch (Exception e) {
			log.error("init ProcessEngineConfiguration erro:{}",e);
			throw new BaseException(BaseConstant.INNER_ERRO, e);
		}
    }
	
    //流程引擎，与spring整合使用factoryBean
    @Bean
    public ProcessEngineFactoryBean processEngine(ProcessEngineConfiguration processEngineConfiguration){
        ProcessEngineFactoryBean processEngineFactoryBean = new ProcessEngineFactoryBean();
        processEngineFactoryBean.setProcessEngineConfiguration((ProcessEngineConfigurationImpl) processEngineConfiguration);
        return processEngineFactoryBean;
    }

    // activiti 八大接口 配置
    @Bean
    public RepositoryService repositoryService(ProcessEngine processEngine){
        return processEngine.getRepositoryService();
    }

    @Bean
    public RuntimeService runtimeService(ProcessEngine processEngine){
        return processEngine.getRuntimeService();
    }

    @Bean
    public TaskService taskService(ProcessEngine processEngine){
        return processEngine.getTaskService();
    }

    @Bean
    public HistoryService historyService(ProcessEngine processEngine){
        return processEngine.getHistoryService();
    }

    @Bean
    public FormService formService(ProcessEngine processEngine){
        return processEngine.getFormService();
    }

    @Bean
    public IdentityService identityService(ProcessEngine processEngine){
        return processEngine.getIdentityService();
    }

    @Bean
    public ManagementService managementService(ProcessEngine processEngine){
        return processEngine.getManagementService();
    }

    @Bean
    public DynamicBpmnService dynamicBpmnService(ProcessEngine processEngine){
        return processEngine.getDynamicBpmnService();
    }
    
    // activiti rest 配置
//    @Bean
//    public RestResponseFactory restResponseFactory() {
//      RestResponseFactory restResponseFactory = new RestResponseFactory();
//      return restResponseFactory;
//    }
//
//    @Bean
//    public ContentTypeResolver contentTypeResolver() {
//      ContentTypeResolver resolver = new DefaultContentTypeResolver();
//      return resolver;
//    }
}
