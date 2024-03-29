package cn.com.compass.web.feign;

import cn.com.compass.base.util.JacksonObjectMapperWrapper;
import cn.com.compass.web.convert.UniversalEnumConverterFactory;
import com.netflix.hystrix.HystrixCommand;
import feign.Contract;
import feign.Feign;
import feign.Logger;
import feign.Retryer;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.hystrix.HystrixFeign;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.cloud.netflix.feign.AnnotatedParameterProcessor;
import org.springframework.cloud.netflix.feign.DefaultFeignLoggerFactory;
import org.springframework.cloud.netflix.feign.FeignFormatterRegistrar;
import org.springframework.cloud.netflix.feign.FeignLoggerFactory;
import org.springframework.cloud.netflix.feign.support.ResponseEntityDecoder;
import org.springframework.cloud.netflix.feign.support.SpringDecoder;
import org.springframework.cloud.netflix.feign.support.SpringEncoder;
import org.springframework.cloud.netflix.feign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo Fegin配置类
 * @date 2018年6月20日 下午12:57:23
 *
 */
@Configuration
public class FeignConfig {

	/**
	 * 消息转化器
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean
    public ObjectFactory<HttpMessageConverters> httpMessageConverters() {
		ObjectFactory<HttpMessageConverters> factory = new ObjectFactory<HttpMessageConverters>() {
			@Override
			public HttpMessageConverters getObject() throws BeansException {
				return new HttpMessageConverters(new MappingJackson2HttpMessageConverter(JacksonObjectMapperWrapper.getInstance()));
			}
		};
        return factory;
    }

	@Autowired(required = false)
	private List<AnnotatedParameterProcessor> parameterProcessors = new ArrayList<>();
 
	@Autowired(required = false)
	private List<FeignFormatterRegistrar> feignFormatterRegistrars = new ArrayList<>();
 
	@Autowired(required = false)
	private Logger logger;
	
	/**
	 * 解码
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean
	public Decoder feignDecoder() {
		return new ResponseEntityDecoder(new SpringDecoder(httpMessageConverters()));
	}
	
	/**
	 * 编码
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean
	public Encoder feignEncoder() {
		return new SpringEncoder(httpMessageConverters());
	}
	
	/**
	 * 错误处理
	 * @return
	 */
	@Bean
	public DefineErrorDecoder errorDecoder(){
		return new DefineErrorDecoder();
	}
	
	/**
	 * SpringMvcContract
	 * @param feignConversionService
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean
	public Contract feignContract(FormattingConversionService feignConversionService) {
		//在原配置类中是用ConversionService类型的参数，但ConversionService接口不支持addConverterFactory操作，使用FormattingConversionService仍然可以实现feignContract配置.
		feignConversionService.addConverterFactory(new UniversalEnumConverterFactory());
		return new SpringMvcContract(this.parameterProcessors, feignConversionService);
	}
	
	/**
	 * 
	 * @return
	 */
	@Bean
	public FormattingConversionService feignConversionService() {
		FormattingConversionService conversionService = new DefaultFormattingConversionService();
		for (FeignFormatterRegistrar feignFormatterRegistrar : feignFormatterRegistrars) {
			feignFormatterRegistrar.registerFormatters(conversionService);
		}
		return conversionService;
	}
	
	/**
	 * 使用HystrixFeign。builder
	 */
	@Configuration
	@ConditionalOnClass({ HystrixCommand.class, HystrixFeign.class })
	protected class HystrixFeignConfiguration {
		@Bean
		@Scope("prototype")
		@ConditionalOnMissingBean
		@ConditionalOnProperty(name = "feign.hystrix.enabled", matchIfMissing = false)
		public Feign.Builder feignHystrixBuilder() {
			return HystrixFeign.builder();
		}
	}
	
	/**
	 * 重试
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean
	public Retryer feignRetryer() {
		return Retryer.NEVER_RETRY;
	}
	
	@Bean
	@Scope("prototype")
	@ConditionalOnMissingBean
	public Feign.Builder feignBuilder(Retryer retryer) {
		return Feign.builder().retryer(retryer);
	}
	
	/**
	 * logger
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean(FeignLoggerFactory.class)
	public FeignLoggerFactory feignLoggerFactory() {
		return new DefaultFeignLoggerFactory(logger);
	}
	

}
