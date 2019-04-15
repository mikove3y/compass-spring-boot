package cn.com.compass.swagger.conf;

import cn.com.compass.autoconfig.constant.ConstantUtil;
import cn.com.compass.base.constant.BaseConstant;
import cn.com.compass.base.exception.BaseException;
import cn.com.compass.base.vo.BaseResponseVo;
import cn.com.compass.swagger.annotations.EnableSwaggerBootstrapUI;
import cn.com.compass.swagger.ext.SwaggerApiListingScanner;
import cn.com.compass.swagger.ext.SwaggerPropertyService;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.paths.AbstractPathProvider;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.spring.web.plugins.DocumentationPluginsManager;
import springfox.documentation.spring.web.scanners.ApiDescriptionReader;
import springfox.documentation.spring.web.scanners.ApiListingScanner;
import springfox.documentation.spring.web.scanners.ApiModelReader;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo
 * @date 2019/3/20 17:14
 */
@Configuration
@EnableConfigurationProperties(SwaggerProperties.class)
@EnableSwagger2
@EnableSwaggerBootstrapUI// 启动增强模式
@ConditionalOnProperty(name = "swagger.enabled", matchIfMissing = false)
public class SwaggerConfig {
    /**
     * 文档类型
     */
    private DocumentationType documentationType = DocumentationType.SWAGGER_2;

    private SwaggerProperties properties;
    @Autowired
    private ConfigurableListableBeanFactory beanFactory;
    @Autowired
    private ConstantUtil constantUtil;

    @Autowired
    public SwaggerConfig(SwaggerPropertyService propertyService){
        this.properties = propertyService.getProperties();
    }

    /**
     * 初始化Docket分组
     */
    @PostConstruct
    public void initDocket(){
        Map<String,List<ApiGroup>> apiGroups = properties.getApiGroup();
        if(MapUtils.isNotEmpty(apiGroups)){
            for(Map.Entry<String,List<ApiGroup>> en : apiGroups.entrySet()){
                cn.com.compass.swagger.conf.ApiInfo apiInfo = properties.getApiInfo().get(en.getKey());
                if(apiInfo==null)continue;
                List<ApiGroup> groups = en.getValue();
                if(CollectionUtils.isEmpty(groups))continue;
                for(ApiGroup group : groups ){
                    Docket docket = new Docket(documentationType)
                            .groupName(group.getName())
                            .apiInfo(this.ApiInfo(apiInfo,en.getKey()))
                            .globalResponseMessage(RequestMethod.GET, this.globalResponseMessage())
                            .globalResponseMessage(RequestMethod.POST, this.globalResponseMessage())
                            .globalResponseMessage(RequestMethod.PUT, this.globalResponseMessage())
                            .globalResponseMessage(RequestMethod.DELETE, this.globalResponseMessage())
                            .pathProvider(new AbstractPathProvider() {
                                @Override
                                protected String applicationPath() {
                                    return group.getPrefixPath();
                                }
                                @Override
                                protected String getDocumentationPath() {
                                    return "/";
                                }
                            })
                            .tags(new Tag(group.getName(),group.getDescription()))
                            .protocols(Sets.newHashSet(new String[]{"http","https"}))
                            .host(group.getBaseServerUrl())
                            .select()
                            .paths(PathSelectors.any())
                            .build();
                    if(group.isNeedAuth()){
                        docket.globalOperationParameters(this.globalOperationParameters())
                                .securityContexts(Lists.newArrayList(securityContext())).securitySchemes(Lists.<SecurityScheme>newArrayList(apiKey()));
                    }
                    String beanName = group.getName()+"Docket";
                    if(beanFactory.containsBean(beanName)){
                        throw new BaseException(BaseConstant.INNER_ERRO,String.format("beanName of %s is exist",beanName));
                    }
                    // 注册单例bean
                    beanFactory.registerSingleton(beanName,docket);
                }
            }
        }
    }

    /**
     * 获取全局响应码
     * @return
     */
    private List<ResponseMessage> globalResponseMessage(){
        List<ResponseMessage> messages = new ArrayList<>();
        Map<String,String> constant = constantUtil.getAllEnmus();
        if(MapUtils.isNotEmpty(constant)){
            for(Map.Entry<String,String> en : constant.entrySet()){
                messages.add(new ResponseMessageBuilder().code(HttpStatus.OK.value()).message(en.getKey() + ", " + en.getValue()).responseModel(new ModelRef(BaseResponseVo.class.getSimpleName())).build());
            }
        }
        return messages;
    }


    /**
     * 构造apiInfo
     * @param apiInfo
     * @return
     */
    private ApiInfo ApiInfo(cn.com.compass.swagger.conf.ApiInfo apiInfo,String version){
        ApiInfo info = new ApiInfoBuilder()
                .title(apiInfo.getTitle())
                .description(apiInfo.getDescription())
                .termsOfServiceUrl(apiInfo.getTermsOfServiceUrl())
                .license(apiInfo.getLicense())
                .licenseUrl(apiInfo.getLicenseUrl())
                .contact(new Contact(apiInfo.getContactName(), apiInfo.getContactUrl(), apiInfo.getContactEmail()))
                .version(version)
                .build();
        return info;
    }


    /**
     * 扫描Api
     * @param apiDescriptionReader
     * @param apiModelReader
     * @param pluginsManager
     * @return
     */
    @Primary
    @Bean
    public ApiListingScanner addExtraOperations(ApiDescriptionReader apiDescriptionReader, ApiModelReader apiModelReader, DocumentationPluginsManager pluginsManager) {
        return new SwaggerApiListingScanner(apiDescriptionReader, apiModelReader, pluginsManager);
    }

    /**
     * securityContext
     * @return
     */
    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex("/.*"))
                .build();
    }

    /**
     * 默认auth
     * @return
     */
    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Lists.newArrayList(new SecurityReference("BearerToken", authorizationScopes));
    }

    /**
     * apiKey
     * @return
     */
    private ApiKey apiKey() {
        return new ApiKey("BearerToken", "Authorization", "header");
    }

    /**
     * 全局参数
     * @return
     */
    private List<Parameter> globalOperationParameters(){
        List<Parameter> parameters = Lists.newArrayList();
        ParameterBuilder parameterBuilder = new ParameterBuilder();
        parameterBuilder.name("Authorization").description("token令牌").modelRef(new ModelRef("String"))
                .parameterType("header")
                .required(true).build();
        parameters.add(parameterBuilder.build());
        return parameters;
    }

}
