package cn.com.compass.swagger.ui.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.resource.WebJarsResourceResolver;

@Configuration
@ComponentScan(
        basePackages = {"cn.com.compass.swagger.ui.web"}
)
@EnableWebMvc
public class SwaggerBootstrapUIConfiguration extends WebMvcConfigurerAdapter {


    /**
     * 注册静态资源
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations(
                "classpath:/static/");
        registry.addResourceHandler("swagger-ui.html").addResourceLocations(
                "classpath:/META-INF/resources/");
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
