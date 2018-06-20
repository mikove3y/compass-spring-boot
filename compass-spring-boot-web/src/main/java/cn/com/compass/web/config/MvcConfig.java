package cn.com.compass.web.config;

import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import cn.com.compass.util.JacksonObjectMapperWrapper;
import cn.com.compass.web.convert.UniversalEnumConverterFactory;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations(
                "classpath:/static/");
        registry.addResourceHandler("swagger-ui.html").addResourceLocations(
                "classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations(
                "classpath:/META-INF/resources/webjars/");
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
	 * 配置消息转换器 
	 */
//	@Override
//	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//		super.configureMessageConverters(converters);
//		// ... mapper
//		HttpMessageConverter<?> converter = new MappingJackson2HttpMessageConverter(mapper);
//		converters.add(converter);
//		
//	}
	@Bean
    public HttpMessageConverters jacksonHttpMessageConverters(){
		HttpMessageConverter<?> converter = new MappingJackson2HttpMessageConverter(JacksonObjectMapperWrapper.getInstance());
        return new HttpMessageConverters(converter);
    }
	
}