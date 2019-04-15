package cn.com.compass.web.config;

import cn.com.compass.web.api.ApiVersionRequestHandlerMapping;
import cn.com.compass.web.convert.UniversalEnumConverterFactory;
import cn.com.compass.web.interceptor.BaseHandlerInterceptor;
import cn.com.compass.web.util.HttpMessageConverterUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.filter.HttpPutFormContentFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
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
		converters.add(HttpMessageConverterUtil.jackson2MessageConverter());
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

	/**
	 * 注册拦截器
	 * @param registry
	 */
	@Override
	protected void addInterceptors(InterceptorRegistry registry) {
		super.addInterceptors(registry);
		registry.addInterceptor(new BaseHandlerInterceptor());
	}
}
