package cn.com.compass.autoconfig.constant;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@EnableConfigurationProperties(ConstantProperties.class)
@ConditionalOnProperty(name = "constant.enabled", matchIfMissing = true)
public class ConstantAutoConfiguration {
	
	@Bean
	@Primary
	@ConditionalOnMissingBean
	public ConstantUtil ConstantUtil() {
		return new ConstantUtil();
	}

}
