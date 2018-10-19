package cn.com.compass.camel.config;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.apache.camel.CamelContext;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.spring.boot.CamelContextConfiguration;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import cn.com.compass.base.constant.BaseBizStatusEnumDeserializer;
import cn.com.compass.base.constant.BaseBizStatusEnumDeserializer2;
import cn.com.compass.base.constant.BaseBizStatusEnumSerializer;
import cn.com.compass.base.constant.BaseBizStatusEnumSerializer2;
import cn.com.compass.base.constant.IBaseBizStatusEnum;
import cn.com.compass.base.constant.IBaseBizStatusEnum2;
import cn.com.compass.camel.interceptor.CamelHeadInterceptor;
import cn.com.compass.util.JacksonObjectMapperWrapper;
import feign.RequestInterceptor;

@Configuration
public class CamelConfig implements CamelContextConfiguration {
	
//	private static final String CAMEL_URL_MAPPING = "/camel/*";
//    private static final String CAMEL_SERVLET_NAME = "CamelServlet";
//
//    @Bean
//    public ServletRegistrationBean servletRegistrationBean() {
//        ServletRegistrationBean registration = new ServletRegistrationBean(new CamelHttpTransportServlet(), CAMEL_URL_MAPPING);
//        registration.setName(CAMEL_SERVLET_NAME);
//        return registration;
//    }
//
//    @Bean
//    public SpringCamelContext camelContext(ApplicationContext applicationContext) throws Exception {
//        SpringCamelContext camelContext = new SpringCamelContext(applicationContext);
//        camelContext.addRoutes(restApiRouter());
//        camelContext.addRoutes(systemServiceRouter());
//        return camelContext;
//    }
	
	/**
	 * camel配置json转换器
	 * @return
	 */
	@Bean(name = "json-jackson")
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public JacksonDataFormat jacksonDataFormat() {
		JacksonObjectMapperWrapper wrapper = JacksonObjectMapperWrapper.getInstance();
		// 注册IBaseBizStatusEnum序列化和反序列化
		SimpleModule simpleModule = new SimpleModule();
		JsonDeserializer<IBaseBizStatusEnum> deserialize = new BaseBizStatusEnumDeserializer();
		simpleModule.addDeserializer(IBaseBizStatusEnum.class, deserialize);
		StdSerializer<IBaseBizStatusEnum> serialize = new BaseBizStatusEnumSerializer();
		simpleModule.addSerializer(IBaseBizStatusEnum.class, serialize);
		wrapper.registerModule(simpleModule);
		// 注册IBaseBizStatusEnum2序列化和反序列化
		SimpleModule simpleModule2 = new SimpleModule();
		JsonDeserializer<IBaseBizStatusEnum2> deserialize2 = new BaseBizStatusEnumDeserializer2();
		simpleModule.addDeserializer(IBaseBizStatusEnum2.class, deserialize2);
		StdSerializer<IBaseBizStatusEnum2> serialize2 = new BaseBizStatusEnumSerializer2();
		simpleModule.addSerializer(IBaseBizStatusEnum2.class, serialize2);
		wrapper.registerModule(simpleModule2);
		return new JacksonDataFormat(wrapper, HashMap.class);
	}
	
	/**
	 * Caffeine cache 非常好的方案用于塞入feign RequestTemplate头参，threadLocal不方便在多个线程间传递参数
	 * 默认2分钟失效
	 * @return
	 */
	@Bean
	public Cache<Object, Object> cache() {
		return Caffeine.newBuilder()
				.expireAfterAccess(2, TimeUnit.MINUTES).initialCapacity(5)
				.maximumSize(1_000).build();
	}
	
	/**
	 * camel请求头拦截器->头参塞入feign RequestTemplate
	 * @return
	 */
	@Bean
	public RequestInterceptor camelHeadInterceptor() {
		return new CamelHeadInterceptor();
	}

	@Override
	public void beforeApplicationStart(CamelContext camelContext) {
		System.out.println("-------------------- before CAMEL init-------------------");
	}

	@Override
	public void afterApplicationStart(CamelContext camelContext) {
		System.out.println("-------------------- after CAMEL init-------------------");
		jacksonDataFormat()
		.getObjectMapper()
		.findAndRegisterModules();
	}
}
