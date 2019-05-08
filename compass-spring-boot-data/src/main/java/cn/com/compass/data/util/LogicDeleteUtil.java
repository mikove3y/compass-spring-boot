package cn.com.compass.data.util;

import cn.com.compass.base.constant.BaseBizeStatusEnum;
import cn.com.compass.base.util.DataXUtil;
import cn.com.compass.data.annotation.EnableLogicDelete;
import cn.com.compass.data.annotation.LogicDeleteColumn;
import cn.com.compass.data.entity.BaseEntity;
import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.StringMemberValue;
import lombok.*;
import org.springframework.util.Assert;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo logic delete util
 * @date 2019/4/29 23:36
 */
public class LogicDeleteUtil {

    /**
     * judging whether the domain entity open logicDelete function or not
     * @param domainClass
     * @return
     */
    public static <E extends BaseEntity> boolean enableLogicDelete(Class<E> domainClass){
        Assert.notNull(domainClass,"domainClass can not be null !");
        return domainClass.isAnnotationPresent(EnableLogicDelete.class)&&domainClass.isAnnotationPresent(Entity.class);
    }

    /**
     * get tableInfo from domain class
     * @param domainClass
     * @param <E>
     * @return
     */
    public static <E extends BaseEntity> TableInfo tableInfo(Class<E> domainClass) {
        Assert.notNull(domainClass,"domainClass can not be null !");
        if(enableLogicDelete(domainClass)){
            Entity entity = domainClass.getAnnotation(Entity.class);
            Table table = domainClass.getAnnotation(Table.class);
            String tableName = null;
            if (table != null) {
                tableName = table.name();
            }
            if (tableName == null && entity != null) {
                tableName = DataXUtil.camelToUnderline(domainClass.getSimpleName());
            }
            if (tableName != null) {
                EnableLogicDelete enableLogicDelete = domainClass.getAnnotation(EnableLogicDelete.class);
                Field pkField = loop(domainClass, Id.class);
                Field deleteColField = loop(domainClass, LogicDeleteColumn.class);
                LogicDeleteColumn logicDeleteColumn = deleteColField.getAnnotation(LogicDeleteColumn.class);
                String pkColName = DataXUtil.camelToUnderline(pkField.getName());
                String deleteColName = DataXUtil.camelToUnderline(deleteColField.getName());
                return TableInfo.builder()
                        .tableName(tableName)
                        .pkColName(pkColName)
                        .deleteColName(deleteColName)
                        .deleteValue(logicDeleteColumn.deleteValue())
                        .notDeleteValue(logicDeleteColumn.notDeleteValue())
                        .logicDeleteStrategy(enableLogicDelete.strategy())
                        .backupDomainClass(enableLogicDelete.backupDomainClass())
                        .build();
            }
        }
        return null;
    }

    /**
     * dynamic add SQLDelete and SQLWhere to the logicDeleteTable
     * @param domainClass
     * @return
     * @throws Exception
     */
    public static Class<?> addDeleteAnnotation2DomainClass(Class<?> domainClass) throws Exception {
        if(enableLogicDelete((Class<? extends BaseEntity>)domainClass)){
            TableInfo tableInfo = tableInfo((Class<? extends BaseEntity>)domainClass);
            if(tableInfo!=null){
                // 从实现的角度说，ClassPool是一个CtClass对象的hash表，类名做为key。ClassPool的get()搜索hash表找到与指定key关联的CtClass对象。
                ClassPool classPool = ClassPool.getDefault();
                classPool.appendClassPath(new ClassClassPath(LogicDeleteUtil.class));
                // 如果CtClass通过writeFile(),toClass(),toBytecode()转换了类文件，javassist冻结了CtClass对象。
                // 以后是不允许修改这个 CtClass对象。这是为了警告开发人员当他们试图修改一个类文件时，已经被JVM载入的类不允许被重新载入。
                CtClass clazz = classPool.get(domainClass.getName());
                clazz.stopPruning(true);
                // Defrost()执行后，CtClass对象将可以再次修改。
                clazz.defrost();
                ClassFile classFile = clazz.getClassFile();

                ConstPool constPool = classFile.getConstPool();
                // 逻辑删除注解
                javassist.bytecode.annotation.Annotation sqlDeleteAnnotation = new javassist.bytecode.annotation.Annotation("org.hibernate.annotations.SQLDelete", constPool);
                String deleteSql = String.format("update %s set %s = %s where %s = ? ",tableInfo.getTableName(),tableInfo.getDeleteColName(),tableInfo.getDeleteValue().getCode(),tableInfo.getPkColName());
                sqlDeleteAnnotation.addMemberValue("sql", new StringMemberValue(deleteSql, constPool));
                // 逻辑查询注解
                javassist.bytecode.annotation.Annotation sqlWhereAnnotation = new javassist.bytecode.annotation.Annotation("org.hibernate.annotations.Where", constPool);
                String whereSql = String.format("%s = %s ",tableInfo.getDeleteColName(),tableInfo.getNotDeleteValue().getCode());
                sqlWhereAnnotation.addMemberValue("clause", new StringMemberValue(whereSql, constPool));

                // 获取运行时注解属性
                AnnotationsAttribute attribute = (AnnotationsAttribute) classFile.getAttribute(AnnotationsAttribute.visibleTag);
                attribute.addAnnotation(sqlDeleteAnnotation);
                attribute.addAnnotation(sqlWhereAnnotation);

                classFile.addAttribute(attribute);
//        classFile.setVersionToJava5();
//       Class<?> c1 = clazz.toClass();
//        SQLDelete delete1 = c1.getAnnotation(SQLDelete.class);
//        System.out.println(delete1.sql());
                // 当前ClassLoader中必须尚未加载该实体。（同一个ClassLoader加载同一个类只会加载一次）
//                EntityClassLoader loader = new EntityClassLoader(org.springframework.util.ClassUtils.getDefaultClassLoader());
                return clazz.toClass();
            }
        }
        return domainClass;
    }

    /**
     * loop LogicDeleteColumn form the Fields
     * @param domainClass
     * @return
     */
    private static <E extends BaseEntity> Field loop(Class<E> domainClass, Class<? extends Annotation> annotation){
        Field[] fields = domainClass.getDeclaredFields();
        for (Field f : fields) {
            if(Modifier.isStatic(f.getModifiers())||Modifier.isFinal(f.getModifiers()))continue;
            f.setAccessible(true);
            if (f.isAnnotationPresent(annotation)){
                return f;
            }
        }
        return loop(BaseEntity.class,annotation);
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class TableInfo {
        /**
         * 表名
         */
        private String tableName;
        /**
         * 主键
         */
        private String pkColName;
        /**
         * 字段名
         */
        private String deleteColName;
        /**
         * 删除字段值
         */
        private BaseBizeStatusEnum.YesOrNo deleteValue;
        /**
         * 非删除字段值
         */
        private BaseBizeStatusEnum.YesOrNo notDeleteValue;
        /**
         * 逻辑删除策略
         */
        private EnableLogicDelete.LogicDeleteStrategy logicDeleteStrategy;
        /**
         * 备份策略对应的备份domainClass
         */
        private Class<? extends BaseEntity> backupDomainClass;
    }


}
