package cn.com.compass.autoconfig.security;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(JwtProperties.class)
@ConditionalOnProperty(name = "jwt.enabled", matchIfMissing = true)
public class JwtAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public JwtUtil JwtUtil() {
		return new JwtUtil();
	}

}
