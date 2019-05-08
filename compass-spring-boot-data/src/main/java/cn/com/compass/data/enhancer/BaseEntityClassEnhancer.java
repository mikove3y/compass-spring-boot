package cn.com.compass.data.enhancer;

import cn.com.compass.data.annotation.EnableLogicDelete;
import cn.com.compass.data.entity.BaseEntity;
import cn.com.compass.data.util.LogicDeleteUtil;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.annotation.AnnotationDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;
import org.activejpa.entity.Model;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;

import javax.persistence.Entity;
import java.lang.annotation.Annotation;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.util.HashSet;
import java.util.Set;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo
 * @date 2019/5/5 21:11
 */
public class BaseEntityClassEnhancer implements AgentBuilder.Transformer {

    private Instrumentation instrumentation;
    private static Set<String> transformedClasses = new HashSet();
    private static final Logger logger = LoggerFactory.getLogger(BaseEntityClassEnhancer.class);

    public BaseEntityClassEnhancer(Instrumentation instrumentation){
        this.instrumentation = instrumentation;
    }

    public ClassFileTransformer getTransformer() {
        logger.info("Creating a class file transformer");
        AgentBuilder builder = (new AgentBuilder.Default()).with(net.bytebuddy.agent.builder.AgentBuilder.PoolStrategy.Default.FAST).type(ElementMatchers.isSubTypeOf(BaseEntity.class).and(ElementMatchers.isAnnotatedWith(Entity.class))).and(ElementMatchers.isAnnotatedWith(EnableLogicDelete.class)).transform(this);
        return this.instrumentation != null ? builder.installOn(this.instrumentation) : builder.installOnByteBuddyAgent();
    }

    @Override
    public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder, TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule) {
        // 修改class增加注解
        logger.info("Transforming the model class - {}", typeDescription.getActualName());
        if (transformedClasses.contains(typeDescription.getActualName())) {
            logger.info("Model class - {} is already transformed. Skipping", typeDescription.getActualName());
            return builder;
        } else {
            try {
                Class<?> clazz = classLoader.loadClass(typeDescription.getActualName());
                LogicDeleteUtil.TableInfo tableInfo = LogicDeleteUtil.tableInfo((Class<? extends BaseEntity>) clazz);
                String deleteSql = String.format("update %s set %s = %s where %s = ? ",tableInfo.getTableName(),tableInfo.getDeleteColName(),tableInfo.getDeleteValue().getCode(),tableInfo.getPkColName());
                String whereSql = String.format("%s = %s ",tableInfo.getDeleteColName(),tableInfo.getNotDeleteValue().getCode());
                builder.annotateType(AnnotationDescription.Builder.ofType(SQLDelete.class).define("sql",deleteSql).build())
                        .annotateType(AnnotationDescription.Builder.ofType(Where.class).define("clause",whereSql).build())
                        .make();
                transformedClasses.add(typeDescription.getActualName());
                return builder;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
