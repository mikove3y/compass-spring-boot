package cn.com.compass.swagger.ext;

import cn.com.compass.base.vo.BaseResponseVo;
import cn.com.compass.swagger.conf.ApiGroup;
import cn.com.compass.swagger.conf.ApiList;
import cn.com.compass.swagger.util.AnnotationUtil;
import cn.com.compass.swagger.util.ClassScannerUtil;
import com.fasterxml.classmate.TypeResolver;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.builders.OperationBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiDescription;
import springfox.documentation.service.Parameter;
import springfox.documentation.spring.web.readers.operation.CachingOperationNameGenerator;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo
 * @date 2019/3/21 15:27
 */
public class SwaggerApiDescriptionReaderHelper {

    /**
     * 类注解集合
     */
    private static final List<Class> classASet = Arrays.asList(FeignClient.class, RestController.class, Controller.class);
    /**
     * 方法注解集合
     */
    private static final List<Class> methodASet = Arrays.asList(RequestMapping.class, GetMapping.class, PostMapping.class, DeleteMapping.class, PutMapping.class, PatchMapping.class);
    /**
     * 参数注解集合
     */
    private static final List<Class> paramsASet = Arrays.asList(PathVariable.class, RequestAttribute.class, RequestBody.class, RequestHeader.class, RequestParam.class, RequestPart.class);
    /**
     * 参数校验注解
     */
    private static final List<Class> validASet = Arrays.asList(NotNull.class, NotEmpty.class);

    /**
     * api描述
     *
     * @return
     */
    public static List<ApiDescription> read(ApiList apiList, ApiGroup apiGroup) {
        String[] scanPath = apiList.getScanClientPath();
        if (ArrayUtils.isEmpty(scanPath))
            return null;
        // classPath 扫描
        ClassScannerUtil provider = new ClassScannerUtil();
        /**
         * 添加过滤器
         */
        provider.addIncludeFilter(new TypeFilter() {
            @Override
            public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
                ClassMetadata classMetadata = metadataReader.getClassMetadata();
                try {
                    String className = classMetadata.getClassName();
                    Class<?> clazz = Class.forName(className);
                    for(Class c : classASet){
                        return clazz.isAnnotationPresent(c) && !clazz.isAnnotationPresent(Deprecated.class);
                    }
                } catch (ClassNotFoundException e) {
                    return false;
                }
                return false;
            }
        });
        // 有序Api
        List<ApiDescription> apis = new LinkedList<>();
        for(String path : scanPath){
            // 扫描包下面的类
            Set<Class> classes = provider.doScan(path);
            if (CollectionUtils.isNotEmpty(classes)) {
                int apiPosition = 0;
                for(Class c : classes){
                    apiPosition++;
                    // 解析class metadata信息
                    Map<String,Object> classRequestMap = AnnotationUtil.getAnnotationMemberValues(c.getAnnotation(FeignClient.class));
                    String clientPath = (String) classRequestMap.get("path");
                    clientPath = StringUtils.isNotEmpty(clientPath)&&clientPath.endsWith("/")?StringUtils.substringBeforeLast(clientPath,"/"):clientPath;
                    Method[] methods = c.getDeclaredMethods();
                    if (ArrayUtils.isNotEmpty(methods)) {
                        for(int i = 0 ; i < methods.length; i++){
                            ApiDescription api = buildApi4Method(methods[i], apiList,apiGroup,clientPath,(i+1)*apiPosition);
                            if (api != null)
                                apis.add(api);
                        }
                    }
                }
            }
        }
        return apis;
    }

    /**
     * 通过method构建api
     * @param method
     * @param apiList
     * @return
     */
    public static ApiDescription buildApi4Method(Method method, ApiList apiList, ApiGroup apiGroup, String clientPath, int position) {
        // 请求参数信息
        Map<String,Object> methodRequestMap = getMethodRequestMapping(method);
        if(MapUtils.isEmpty(methodRequestMap)){
            return null;
        }
        String name = (String) methodRequestMap.get("name");// 作为api的描述
        name = StringUtils.isEmpty(name) ? method.getName() : name;
        RequestMethod[] me = (RequestMethod[]) methodRequestMap.get("method");// 请求类型 me 为空表示 几种请求方式都支持 ,不建议这样子的接口设计，接口应该参考简单明确的设计原则进行定义
        String[] consumes = (String[]) methodRequestMap.get("consumes");
        String[] produces = (String[]) methodRequestMap.get("produces");
        // 默认都是json格式
        if(ArrayUtils.isEmpty(produces)){
            produces = new String[]{MediaType.APPLICATION_JSON_UTF8_VALUE};
        }
        // headers & params 用的很少，这里不考虑
//        String[] headers = (String[]) methodRequestMap.get("headers");
//        String[] params = (String[]) methodRequestMap.get("params");
        // value 和 path 互为别名 那个不为空取那个，同时与FeignClient中的path进行拼装
        String[] path = methodRequestMap.get("value")!=null?(String[])methodRequestMap.get("value"):(String[])methodRequestMap.get("path");

        /**
         * 方法必须写，而且只能写一个
         * 路径可以不写，但是写了只能有一个
         */
        if((ArrayUtils.isEmpty(me)||me.length>1)||(ArrayUtils.isNotEmpty(path)&&path.length>1)){
            return null;
        }
        String suffixPath = ArrayUtils.isNotEmpty(path) ? (StringUtils.isNotEmpty(path[0]) && path[0].startsWith("/") ? path[0] : "/" + path[0]) : "";
        // 构造请求路径
        String apiPath = /*(StringUtils.isNotEmpty(properties.getPrefixPath()) ? properties.getPrefixPath()  : "") + */clientPath + suffixPath;

        // 请求参数
        List<Parameter> parameters = new ArrayList<>();
        java.lang.reflect.Parameter[] params = method.getParameters();
        TypeResolver typeResolver = new TypeResolver();
        if(ArrayUtils.isNotEmpty(params)){
            Annotation[][] paramAnnos = method.getParameterAnnotations();
            for(int i = 0 ; i < params.length; i++){
                java.lang.reflect.Parameter param = params[i];
                String paramName = param.getName();
                Class<?> paramType = param.getType();
                Annotation[] anos = paramAnnos[i];
//                String defaultValue = null;
                boolean required = true;
                String parameterType = null;
                // 匹配paramsASet
                for(Annotation a : anos){
                    for(Class c : paramsASet){
                        if(a.annotationType().isAssignableFrom(c)){
                            // 判断c是什么类型
                            // body, formData, header, path, query
                            if(c.isAssignableFrom(RequestHeader.class)||
                                    c.isAssignableFrom(RequestAttribute.class)){
                                parameterType = "header";
                            }else if(c.isAssignableFrom(RequestPart.class)){
                                parameterType = "formData";
                            }else if(c.isAssignableFrom(PathVariable.class)){
                                parameterType = "path";
                            }else if (c.isAssignableFrom(RequestParam.class)){
                                parameterType = "query";
                            }else if(c.isAssignableFrom(RequestBody.class)){
                                parameterType = "body";
                                consumes = new String[]{MediaType.APPLICATION_JSON_UTF8_VALUE};
                            }
                            // 获取required defaultValue
                            Map<String,Object> am = AnnotationUtil.getAnnotationMemberValues(a);
//                            defaultValue = (String) am.get("defaultValue");
                            required = (boolean) am.get("required");
                            break;
                        }
                    }
                }
                parameters.add(new ParameterBuilder()
                        .description(paramName)
                        .name(paramName)
                        .type(typeResolver.resolve(paramType))
                        .parameterType(parameterType)
                        .parameterAccess("access")
                        .required(required)
                        .modelRef(new ModelRef(paramType.getSimpleName()))
//                        .defaultValue(defaultValue)
                        .build());
            }
        }

        OperationBuilder ob = new OperationBuilder(new CachingOperationNameGenerator())
                .method(HttpMethod.resolve(me[0].name()))
                .produces(Sets.newHashSet(produces))
                .consumes(Sets.newHashSet(consumes))
                .summary(name)
                .notes(name)
                .tags(Sets.newHashSet(apiList.getTags()))
                .parameters(parameters)
                .responseModel(new ModelRef(method.getReturnType().getSimpleName()))
                .position(position)
                .uniqueId(method.hashCode()+"")
                .responseMessages(Sets.newHashSet(new ResponseMessageBuilder().code(HttpStatus.OK.value()).message("success").responseModel(new ModelRef(BaseResponseVo.class.getSimpleName())).build()));

        return new ApiDescription(apiGroup.getName(),apiPath,apiGroup.getDescription(),Arrays.asList(ob.build()),apiGroup.isHidden());
    }

    /**
     * 获取method的注解参数值
     * @param method
     * @return
     */
    public static Map<String,Object> getMethodRequestMapping(Method method) {
        for(Class c : methodASet){
            if (method.isAnnotationPresent(Deprecated.class))
                break;
            if (method.isAnnotationPresent(c)) {
                // 获取GetMapping PostMapping DeleteMapping PutMapping PatchMapping等对应的RequestMapping 的Method属性值
                Map<String,Object> result = AnnotationUtil.getAnnotationMemberValues(method.getAnnotation(c));
                if(!c.isAssignableFrom(RequestMapping.class)){
                    RequestMethod[] me = (RequestMethod[]) AnnotationUtil.getAnnotationMemberValues(method.getAnnotation(c).annotationType().getAnnotation(RequestMapping.class)).get("method");
                    result.put("method",me);
                }
                return result;
            }
        }
        return null;
    }

}
