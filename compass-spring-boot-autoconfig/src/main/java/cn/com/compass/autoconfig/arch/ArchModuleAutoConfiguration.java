package cn.com.compass.autoconfig.arch;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 异构系统自动化配置类
 * @date 2018/11/26 15:38
 */
@Configuration
@EnableConfigurationProperties(ArchModuleProperties.class)
@ConditionalOnProperty(name = "arch.enabled", matchIfMissing = true)
public class ArchModuleAutoConfiguration {


    @Bean
    @Primary
    @ConditionalOnMissingBean
    public ArchModuleUtil ArchModuleUtil(){
        return new ArchModuleUtil();
    }
}
