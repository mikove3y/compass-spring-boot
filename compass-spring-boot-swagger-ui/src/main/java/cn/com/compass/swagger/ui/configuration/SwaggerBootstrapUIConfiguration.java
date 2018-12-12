package cn.com.compass.swagger.ui.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.resource.WebJarsResourceResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@ComponentScan(
        basePackages = {"cn.com.compass.swagger.ui.web"}
)
@EnableWebMvc
public class SwaggerBootstrapUIConfiguration extends WebMvcConfigurationSupport {


    /**
     * 注册静态资源
     * @param registry
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        // 使用webjars版本管理
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/")
                .resourceChain(false)
                .addResolver(new WebJarsResourceResolver())
                .addResolver(new PathResourceResolver());;
        super.addResourceHandlers(registry);
    }

    /**
     * 视图解析器
     * @return
     */
//    public InternalResourceViewResolver internalresource(){
//        InternalResourceViewResolver internalresource =new InternalResourceViewResolver();
//        internalresource.setPrefix("/");
//        internalresource.setSuffix(".html");
//        return internalresource;
//    }
//
//    /**
//     * 使用默认的处理器
//     * @param configurer
//     */
//    @Override
//    protected void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
//        configurer.enable();
//        super.configureDefaultServletHandling(configurer);
//    }
}
