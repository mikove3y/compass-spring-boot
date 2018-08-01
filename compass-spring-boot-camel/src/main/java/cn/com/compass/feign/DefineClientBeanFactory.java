package cn.com.compass.feign;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Set;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.cglib.core.SpringNamingPolicy;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import cn.com.compass.web.annotation.TargetFeignService;

/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo eip + fegin hardCodeTarget
 * @date 2018年6月20日 下午12:50:12
 *
 */
public class DefineClientBeanFactory<T> implements InitializingBean, FactoryBean<T>{
	
	private String innerClassName;

	public void setInnerClassName(String innerClassName) {
		this.innerClassName = innerClassName;
	}
	
	public String getInnerClassName() {
		return innerClassName;
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public T getObject() throws Exception {
		String clazz = this.getInnerClassName();
		if(clazz!=null) {
			Class innerClass = Class.forName(clazz);
			if (innerClass.isInterface()) {
				return (T) InterfaceProxy.getInstance().newInstance(innerClass);
			} else {
				Enhancer enhancer = new Enhancer();
				enhancer.setSuperclass(innerClass);
				enhancer.setNamingPolicy(SpringNamingPolicy.INSTANCE);
				enhancer.setCallback(new MethodInterceptorImpl());
				return (T) enhancer.create();
			}
		}
		return null;
	}
	
	@Override
	public Class<?> getObjectType() {
		try {
			String clazz = this.getInnerClassName();
			if(clazz!=null)
			return Class.forName(clazz);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public boolean isSingleton() {
		return true;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {

	}
	
	/**
	 * 扫描包
	 */
	public static final String[] BaseFeignPackage = {"cn.com.bgy.iot.sass.eip.router"};
	
	/**
	 * 扫描配置
	 */
//	@Component
	public static class BeanScannerConfigurer implements BeanFactoryPostProcessor, ApplicationContextAware {

		private ApplicationContext applicationContext;

		public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
			this.applicationContext = applicationContext;
		}

		public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
			Scanner scanner = new Scanner((BeanDefinitionRegistry) beanFactory);
			scanner.setResourceLoader(this.applicationContext);
			scanner.scan(BaseFeignPackage);
		}
	}
	
	/**
	 * 扫描器
	 */
	public final static class Scanner extends ClassPathBeanDefinitionScanner {

		public Scanner(BeanDefinitionRegistry registry) {
			super(registry);
		}

		public void registerDefaultFilters() {
			this.addIncludeFilter(new AnnotationTypeFilter(TargetFeignService.class));
		}

		public Set<BeanDefinitionHolder> doScan(String... basePackages) {
			Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);
			for (BeanDefinitionHolder holder : beanDefinitions) {
				GenericBeanDefinition definition = (GenericBeanDefinition) holder.getBeanDefinition();
				definition.getPropertyValues().add("innerClassName", definition.getBeanClassName());
				definition.setBeanClass(DefineClientBeanFactory.class);
			}
			return beanDefinitions;
		}

		public boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
			return super.isCandidateComponent(beanDefinition) && beanDefinition.getMetadata()
					.hasAnnotation(TargetFeignService.class.getName());
		}

	}
	
	/**
	 * 接口实例化代理
	 */
	public static class InterfaceProxy implements InvocationHandler {
		
		@Autowired
	    private LoadBalancerClient loadBalancerClient;
//		@Autowired
//		private Feign.Builder builder;
		
		private static final InterfaceProxy instance = new InterfaceProxy();
		
		public static InterfaceProxy getInstance() {
			return instance;
		}
		
		/**
		 * 单独实例化apiType feginClient </br>
		 * @param apiType 
		 * @return
		 */
		private  <T> T invokeTargetFeginClient(Class<T> apiType) {
			try {
//				TargetFeignService feginService = apiType.getAnnotation(TargetFeignService.class);
//				// feginService is null
//				String feginServiceName = feginService.value();
//				ServiceInstance serviceInstance = loadBalancerClient.choose(feginServiceName);
//				// serviceInstance is not exists
//				Target<T> target = new HardCodedTarget<T>(apiType, serviceInstance.getUri().toString());
//				return builder.target(target);
			} catch (Exception e) {
				
			}
			return null;
		}

		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			return method.invoke(proxy, args);
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		public <T> T newInstance(Class<T> innerInterface) {
			T t = invokeTargetFeginClient(innerInterface);
			if (t == null) {
				ClassLoader classLoader = innerInterface.getClassLoader();
				Class[] interfaces = new Class[] { innerInterface };
				InterfaceProxy proxy = new InterfaceProxy();
				t = (T) Proxy.newProxyInstance(classLoader, interfaces, proxy);
			}
			return t;
		}
	}
	
	/**
	 * 方法实现代理接口
	 */
	public static class MethodInterceptorImpl implements MethodInterceptor {

		public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
			return methodProxy.invokeSuper(o, objects);
		}
	}
	
}
