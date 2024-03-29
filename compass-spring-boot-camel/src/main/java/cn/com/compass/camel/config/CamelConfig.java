package cn.com.compass.camel.config;

import cn.com.compass.base.constant.BaseBizStatusEnumDeserializer;
import cn.com.compass.base.constant.BaseBizStatusEnumSerializer;
import cn.com.compass.base.constant.IBaseBizStatusEnum;
import cn.com.compass.base.util.JacksonObjectMapperWrapper;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.apache.camel.CamelContext;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.spring.boot.CamelContextConfiguration;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

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
     *
     * @return
     */
    @Bean(name = "json-jackson")
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public JacksonDataFormat jacksonDataFormat() {
        return new JacksonDataFormat(JacksonObjectMapperWrapper.getInstance(), HashMap.class);
    }

    /**
     * Caffeine cache 非常好的方案用于塞入feign RequestTemplate头参，threadLocal不方便在多个线程间传递参数
     * 默认2分钟失效
     *
     * @return
     */
    @Bean
    public Cache<Object, Object> cache() {
        return Caffeine.newBuilder()
                .expireAfterAccess(2, TimeUnit.MINUTES).initialCapacity(5)
                .maximumSize(1_000).build();
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
