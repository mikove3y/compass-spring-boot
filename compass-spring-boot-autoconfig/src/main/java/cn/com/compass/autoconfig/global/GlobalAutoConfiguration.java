package cn.com.compass.autoconfig.global;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.com.compass.autoconfig.security.JwtAutoConfiguration;

@Configuration
@EnableConfigurationProperties(GlobalProperties.class)
@AutoConfigureAfter(JwtAutoConfiguration.class)
@ConditionalOnProperty(name = "global.enabled", matchIfMissing = true)
public class GlobalAutoConfiguration {
	
	@Bean
	@ConditionalOnMissingBean
	public GlobalUtil globalUtil() {
		return new GlobalUtil();
	}
}
