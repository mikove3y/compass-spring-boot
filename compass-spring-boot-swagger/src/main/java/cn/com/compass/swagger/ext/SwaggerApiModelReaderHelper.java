package cn.com.compass.swagger.ext;

import cn.com.compass.base.constant.BaseBizeStatusEnum;
import cn.com.compass.base.constant.IBaseBizStatusEnum;
import cn.com.compass.swagger.annotations.ApiModelProperty;
import cn.com.compass.swagger.conf.ApiList;
import cn.com.compass.swagger.util.AnnotationUtil;
import cn.com.compass.swagger.util.ClassScannerUtil;
import com.fasterxml.classmate.ResolvedType;
import com.fasterxml.classmate.TypeResolver;
import com.google.common.base.Function;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.nullness.qual.Nullable;
import springfox.documentation.builders.ModelBuilder;
import springfox.documentation.builders.ModelPropertyBuilder;
import springfox.documentation.schema.Model;
import springfox.documentation.schema.ModelProperty;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.schema.ModelReference;
import springfox.documentation.service.ObjectVendorExtension;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.swagger.schema.ApiModelProperties;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo
 * @date 2019/3/21 17:09
 */
public class SwaggerApiModelReaderHelper {

    private static final String[] baseModelPath = new String[]{"cn.com.compass.base.vo", "cn.com.compass.base.entity"};

    private static List<Class> defaultEnmuClass = Arrays.asList(BaseBizeStatusEnum.YesOrNo.class, BaseBizeStatusEnum.FileType.class, BaseBizeStatusEnum.ClientType.class);

    private static final String[] baseValidPath = new String[]{"javax.validation.constraints", "org.hibernate.validator.constraints"};

    /**
     * 参数校验注解
     */
    private static final List<Class> validASet = Arrays.asList();

    public static Map<String, Model> read(ApiList apiList) {
        String[] scanPath = apiList.getScanModelPath();
        Set<Class> classes = ClassScannerUtil.scan(baseModelPath);
        if (ArrayUtils.isNotEmpty(scanPath)) {
            classes.addAll(ClassScannerUtil.scan(scanPath));
        }
        classes.addAll(defaultEnmuClass);
        Set<Class> validClasses = ClassScannerUtil.scan(baseValidPath);
        TypeResolver typeResolver = new TypeResolver();
        Map<String, Model> modelMap = new HashMap<>();
        for (Class c : classes) {
            // 判断class是否包含泛型参数
            Field[] fields = c.getDeclaredFields();
            Map<String, ModelProperty> prop = new HashMap<>();
            if (ArrayUtils.isNotEmpty(fields)) {
                for (int i = 0; i < fields.length; i++) {
                    Field f = fields[i];
                    // 非final和static双重属性
                    if (Modifier.isStatic(f.getModifiers()) && Modifier.isFinal(f.getModifiers())) continue;
                    f.setAccessible(true);
                    Class<?> fClass = f.getType();
                    Annotation[] annos = f.getAnnotations();
                    // 扩展属性列表
                    List<VendorExtension> extensions = new ArrayList<>();
                    for (Annotation a : annos) {
                        for (Class v : validClasses) {
                            if (a.annotationType().isAssignableFrom(v)) {
                                // message payload groups
                                Map<String, Object> mv = AnnotationUtil.getAnnotationMemberValues(a);
                                ObjectVendorExtension objectVendorExtension = new ObjectVendorExtension(f.getName());
                                for (Map.Entry<String, Object> en : mv.entrySet()) {
                                    VendorExtension extension = new VendorExtension() {
                                        @Override
                                        public String getName() {
                                            return en.getKey();
                                        }

                                        @Override
                                        public Object getValue() {
                                            return en.getValue();
                                        }
                                    };
                                    objectVendorExtension.addProperty(extension);
                                }
                                extensions.add(objectVendorExtension);
                            }
                        }
                    }
                    // 构造属性
                    ApiModelProperty property = f.getAnnotation(ApiModelProperty.class);
                    String description = null;
                    String name = null;
                    boolean required = false;
                    Object example = null;
                    boolean hidden = false;
                    String allowableValues = null;
                    if (property != null) {
                        description = property.description();
                        name = property.name();
                        required = property.required();
                        example = property.example();
                        hidden = property.hidden();
                        allowableValues = property.allowableValues();
                    }
                    name = StringUtils.isEmpty(name) ? f.getName() : name;
                    // 属性构造
                    ModelPropertyBuilder propertyBuilder = new ModelPropertyBuilder()
                            .name(name)
                            .description(description)
                            .isHidden(hidden)
                            .required(required)
                            .position(i + 1);
                    // 判断fClass 是否是 IBaseBizStatusEnum 的实现类，如果是那就获取code对应的类型
                    // 类型
                    if (IBaseBizStatusEnum.class.isAssignableFrom(fClass)) {
                        propertyBuilder.type(typeResolver.resolve(Integer.class));
                    }else {
                        propertyBuilder.type(typeResolver.resolve(fClass));
                    }
                    // 允许的取值范围
                    if (StringUtils.isNotEmpty(allowableValues)) {
                        propertyBuilder.allowableValues(ApiModelProperties.allowableValueFromString(allowableValues));
                    }
                    // 扩展属性
                    if (CollectionUtils.isNotEmpty(extensions)) {
                        propertyBuilder.extensions(extensions);
                    }
                    // 示例
                    if (example != null) {
                        propertyBuilder.example(example);
                    }
                    ModelProperty modelProperty = propertyBuilder.build();
                    // 属性值必须存在modelRef
                    // 更新modelRef
                    modelProperty.updateModelRef(
                            new Function<ResolvedType, ModelReference>() {
                                @Nullable
                                @Override
                                public ModelReference apply(@Nullable ResolvedType resolvedType) {
                                    Type genericType = f.getGenericType();
                                    String typeName = fClass.getSimpleName();
                                    if (genericType != null && genericType instanceof ParameterizedType) {
                                        // 获取泛型modelRef
                                        ParameterizedType pt = (ParameterizedType) genericType;
                                        String genericTypeName = pt.getActualTypeArguments()[0].getTypeName();
                                        try {
                                            return new ModelRef(typeName, new ModelRef(Class.forName(genericTypeName).getSimpleName()));
                                        } catch (ClassNotFoundException e) {
                                        }
                                    } else if (IBaseBizStatusEnum.class.isAssignableFrom(fClass)) {
                                        return new ModelRef(Integer.class.getSimpleName());
                                    }
                                    return new ModelRef(typeName);
                                }
                            }
                    );
                    prop.put(f.getName(), modelProperty);
                }
            }
            // modelRef 的示例
            Object example = null;
            try {
                if (c.isEnum()) {
                    example = c.getEnumConstants();
                } else {
                    example = c.newInstance();
                }
            } catch (Exception e) {
            }
            modelMap.put(c.getSimpleName(), new ModelBuilder().id(c.hashCode() + "").name(c.getSimpleName()).type(typeResolver.resolve(c)).properties(prop).example(example).build());
        }
        return modelMap;
    }

}
