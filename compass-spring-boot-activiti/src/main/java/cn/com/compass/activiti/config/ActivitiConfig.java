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
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.persistence.deploy.Deployer;
import org.activiti.engine.impl.rules.RulesDeployer;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import cn.com.compass.activiti.id.UUIDGenerator;
import cn.com.compass.activiti.listener.AutoCompleteFirstTaskEventListener;
import cn.com.compass.activiti.org.activiti.image.HMProcessDiagramGenerator;
import cn.com.compass.activiti.org.activiti.image.impl.DefaultProcessDiagramGenerator;

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
public class ActivitiConfig {
	
	//流程配置，与spring整合采用SpringProcessEngineConfiguration这个实现
	@Bean
    public ProcessEngineConfiguration processEngineConfiguration(DataSource dataSource, PlatformTransactionManager transactionManager){
        SpringProcessEngineConfiguration processEngineConfiguration = new SpringProcessEngineConfiguration();
        processEngineConfiguration.setDataSource(dataSource);// 数据源
        processEngineConfiguration.setDatabaseSchemaUpdate("true");// 是否自定生成脚本
        processEngineConfiguration.setDatabaseType("mysql");// 数据库类型
        processEngineConfiguration.setJobExecutorActivate(false);// 任务执行关闭
        processEngineConfiguration.setTransactionManager(transactionManager);// 是否控制器
        // 流程字体
        processEngineConfiguration.setActivityFontName("宋体");
        processEngineConfiguration.setAnnotationFontName("宋体");
        processEngineConfiguration.setLabelFontName("宋体");
        // 主键生成策略
        processEngineConfiguration.setIdGenerator(new UUIDGenerator());
        // 监听器
        List<ActivitiEventListener> listener = new ArrayList<>();
		AutoCompleteFirstTaskEventListener acfte = new AutoCompleteFirstTaskEventListener();// 自动完成第一个节点监听器
		listener.add(acfte);
        processEngineConfiguration.setEventListeners(listener);
        // 流程图生成器
        HMProcessDiagramGenerator diagramGenerator = new DefaultProcessDiagramGenerator();
        processEngineConfiguration.setProcessDiagramGenerator(diagramGenerator);
        // 自定义部署器
        List<Deployer> deployers = new ArrayList<>();
        deployers.add(new RulesDeployer());// 规则引擎drools
        processEngineConfiguration.setCustomPostDeployers(deployers);
        return processEngineConfiguration;
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
