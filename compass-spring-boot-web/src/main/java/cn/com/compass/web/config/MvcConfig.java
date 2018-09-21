package cn.com.compass.web.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.filter.HttpPutFormContentFilter;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import cn.com.compass.base.constant.BaseBizStatusEnumDeserializer;
import cn.com.compass.base.constant.BaseBizStatusEnumSerializer;
import cn.com.compass.base.constant.IBaseBizStatusEnum;
import cn.com.compass.util.JacksonObjectMapperWrapper;
import cn.com.compass.web.convert.UniversalEnumConverterFactory;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
		super.addResourceHandlers(registry);
	}

	/**
	 * 注册枚举转换器
	 */
	@Override
	public void addFormatters(FormatterRegistry registry) {
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
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
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
	public void configurePathMatch(PathMatchConfigurer configurer) {
		configurer.setUseSuffixPatternMatch(false).setUseTrailingSlashMatch(true);

	}
	// @Bean
	// public HttpMessageConverters jacksonHttpMessageConverters(){
	// HttpMessageConverter<?> converter = new
	// MappingJackson2HttpMessageConverter(JacksonObjectMapperWrapper.getInstance());
	// return new HttpMessageConverters(converter);
	// }

}
