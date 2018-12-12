package cn.com.compass.web.config;

import cn.com.compass.base.constant.*;
import cn.com.compass.util.JacksonObjectMapperWrapper;
import cn.com.compass.web.api.ApiVersionRequestHandlerMapping;
import cn.com.compass.web.convert.UniversalEnumConverterFactory;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.filter.HttpPutFormContentFilter;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.List;

@Configuration
public class BaseMvcConfig extends WebMvcConfigurationSupport {


	/**
	 * 注册枚举转换器
	 */
	@Override
	protected void addFormatters(FormatterRegistry registry) {
		registry.addConverterFactory(new UniversalEnumConverterFactory());
	}

	/**
	 * form表单过滤器
	 */
	@Bean
	public HttpPutFormContentFilter httpPutFormContentFilter() {
		return new HttpPutFormContentFilter();
	}

	/**
	 * 配置消息转换器
	 */
	@Override
	protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		super.configureMessageConverters(converters);
		// ... mapper
		JacksonObjectMapperWrapper objectMapper = JacksonObjectMapperWrapper.getInstance();
		// 注册IBaseBizStatusEnum序列化和反序列化
		SimpleModule simpleModule = new SimpleModule();
		JsonDeserializer<IBaseBizStatusEnum> deserialize = new BaseBizStatusEnumDeserializer();
		simpleModule.addDeserializer(IBaseBizStatusEnum.class, deserialize);
		StdSerializer<IBaseBizStatusEnum> serialize = new BaseBizStatusEnumSerializer();
		simpleModule.addSerializer(IBaseBizStatusEnum.class, serialize);
		objectMapper.registerModule(simpleModule);
		// 注册IBaseBizStatusEnum2序列化和反序列化
		SimpleModule simpleModule2 = new SimpleModule();
		JsonDeserializer<IBaseBizStatusEnum2> deserialize2 = new BaseBizStatusEnumDeserializer2();
		simpleModule.addDeserializer(IBaseBizStatusEnum2.class, deserialize2);
		StdSerializer<IBaseBizStatusEnum2> serialize2 = new BaseBizStatusEnumSerializer2();
		simpleModule.addSerializer(IBaseBizStatusEnum2.class, serialize2);
		objectMapper.registerModule(simpleModule2);
		HttpMessageConverter<?> converter = new MappingJackson2HttpMessageConverter(objectMapper);
		converters.add(converter);

	}
	
	/**
	 * 1、 extends WebMvcConfigurationSupport </br>
     * 2、重写下面方法; </br>
     * setUseSuffixPatternMatch : 设置是否是后缀模式匹配，如“/user”是否匹配/user.*，默认真即匹配； </br>
     * setUseTrailingSlashMatch : 设置是否自动后缀路径模式匹配，如“/user”是否匹配“/user/”，默认真即匹配； </br>
	 */
	@Override
	protected void configurePathMatch(PathMatchConfigurer configurer) {
		configurer.setUseSuffixPatternMatch(false).setUseTrailingSlashMatch(true);
	}

	/**
	 * 请求处理映射器,api版本管理
	 * @return
	 */
	@Override
	protected RequestMappingHandlerMapping createRequestMappingHandlerMapping() {
		return new ApiVersionRequestHandlerMapping();
	}

}
