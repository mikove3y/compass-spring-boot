package cn.com.compass.drools.config;

import java.io.IOException;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.KieRepository;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.kie.spring.KModuleBeanFactoryPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo drools规则引擎配置
 * @date 2018年8月23日 下午8:41:58
 *
 */
@Configuration
public class DroolsConfig {
	/**
	 * 规则路径,src/main/resource/rules 
	 */
	public static final String RULES_PATH = "rules/";

	@Bean
	@ConditionalOnMissingBean(KieFileSystem.class)
	public KieFileSystem kieFileSystem() throws IOException {
		KieFileSystem kieFileSystem = getKieServices().newKieFileSystem();
		for (Resource file : getRuleFiles()) {
			kieFileSystem.write(ResourceFactory.newClassPathResource(RULES_PATH + file.getFilename(), "UTF-8"));
		}
		return kieFileSystem;
	}

	private Resource[] getRuleFiles() throws IOException {
		ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
		return resourcePatternResolver.getResources("classpath*:" + RULES_PATH + "**/*.*");
	}

	/**
	 * 构建KieServices
	 * <p>
	 * 该接口提供了很多方法，可以通过这些方法访问KIE关于构建和运行的相关对象。<br/>
	 * 如：可以通过KieServices获取KieContainer，利用KieContainer来访问KBase和KSession等信息；<br/>
	 * 可以获取KieRepository对象，利用KieRepository来管理KieModule等。<br/>
	 * KieServices就是一个中心，通过它来获取的各种对象来完成规则构建、管理和执行等操作。
	 *
	 * @return KieServices
	 */
	private KieServices getKieServices() {
		return KieServices.Factory.get();
	}

	/**
	 * 构建KieContainer
	 * <p>
	 * KieContainer就是一个KieBase的容器。<br/>
	 * 提供了获取KieBase的方法和创建KieSession的方法。<br/>
	 * 其中获取KieSession的方法内部依旧通过KieBase来创建KieSession。
	 *
	 * @return KieContainer
	 * @throws IOException
	 *             IO异常
	 */
	@Bean
	@ConditionalOnMissingBean(KieContainer.class)
	public KieContainer getKieContainer() throws IOException {
		// KieRepository是一个单例对象，它是存放KieModule的仓库
		final KieRepository kieRepository = getKieServices().getRepository();

		kieRepository.addKieModule(new KieModule() {
			public ReleaseId getReleaseId() {
				return kieRepository.getDefaultReleaseId();
			}
		});

		KieBuilder kieBuilder = getKieServices().newKieBuilder(kieFileSystem());
		kieBuilder.buildAll();

		KieContainer kieContainer = getKieServices().newKieContainer(kieRepository.getDefaultReleaseId());

		return kieContainer;
	}

	/**
	 * 构建KieBase
	 * <p>
	 * KieBase就是一个知识仓库，包含了若干的规则、流程、方法等。<br/>
	 * 在Drools中主要就是规则和方法，KieBase本身并不包含运行时的数据之类的。<br/>
	 * 如果需要执行KieBase中的规则的话，就需要根据KieBase创建KieSession。
	 *
	 * @return KieBase
	 * @throws IOException
	 *             IO异常
	 */
	@Bean
	@ConditionalOnMissingBean(KieBase.class)
	public KieBase getKieBase() throws IOException {
		return getKieContainer().getKieBase();
	}

	/**
	 * 构建KieSession
	 * <p>
	 * KieSession就是一个跟Drools引擎打交道的会话，其基于KieBase创建，它会包含运行时数据，包含“事实Fact”，并对运行时数据实时进行规则运算。<br/>
	 * 通过KieContainer创建KieSession是一种较为方便的做法，其本质上是从KieBase中创建出来的。<br/>
	 * KieSession就是应用程序跟规则引擎进行交互的会话通道。 <br>
	 * 创建KieBase是一个成本非常高的事情，KieBase会建立知识（规则、流程）仓库，<br>
	 * 而创建KieSession则是一个成本非常低的事情，所以KieBase会建立缓存，而KieSession则不必。
	 *
	 * @return KieSession
	 * @throws IOException
	 *             IO异常
	 */
	@Bean
	@ConditionalOnMissingBean(KieSession.class)
	public KieSession getKieSession() throws IOException {
		return getKieContainer().newKieSession();
	}

	@Bean
	@ConditionalOnMissingBean(KModuleBeanFactoryPostProcessor.class)
	public KModuleBeanFactoryPostProcessor getKModuleBeanFactoryPostProcessor() {
		return new KModuleBeanFactoryPostProcessor();
	}
}
