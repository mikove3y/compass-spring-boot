package cn.com.compass.data.util;

import cn.com.compass.base.constant.BaseBizeStatusEnum;
import cn.com.compass.data.annotation.EnableLogicDelete;
import cn.com.compass.data.annotation.LogicDeleteColumn;
import cn.com.compass.data.entity.BaseEntity;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.util.Assert;

import javax.persistence.Entity;
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
        return domainClass.getAnnotation(EnableLogicDelete.class)!=null;
    }

    /**
     * judging whether the deleteStrategy of the domain entity is status or not
     * @param domainClass
     * @param <E>
     * @return
     */
    public static <E extends BaseEntity> boolean isStatusDeleteStrategy(Class<E> domainClass){
        if(enableLogicDelete(domainClass)){
            return logicDeleteStrategyInfo(domainClass).logicDeleteStrategy.equals(EnableLogicDelete.LogicDeleteStrategy.STATUS);
        }
        return false;
    }

    /**
     * getting LogicDeleteStrategyInfo form the Annotation of EnableLogicDelete
     * @param domainClass
     * @param <E>
     * @return
     */
    public static <E extends BaseEntity> LogicDeleteStrategyInfo logicDeleteStrategyInfo(Class<E> domainClass) {
        if (enableLogicDelete(domainClass)) {
            EnableLogicDelete logicDeleteAnn = domainClass.getAnnotation(EnableLogicDelete.class);
            if (EnableLogicDelete.LogicDeleteStrategy.BACKUP.equals(logicDeleteAnn.strategy())) {
                Assert.isTrue(logicDeleteAnn.backupDomainClass().isAnnotationPresent(Entity.class), "backupDomainClass must be an Entity");
                Assert.isTrue(!logicDeleteAnn.backupDomainClass().equals(domainClass),"backupDomainClass can't be the self of domainClass");
            }
            return LogicDeleteStrategyInfo.builder().logicDeleteStrategy(logicDeleteAnn.strategy()).backupDomainClass(logicDeleteAnn.backupDomainClass()).build();
        }
        return null;
    }

    /**
     * getting LogicDeleteColumnInfo form the domainClass,if subClass can't define the logicDeleteColumn ,then we will get it from the SuperClass
     * @param domainClass
     * @return
     */
    public static <E extends BaseEntity> LogicDeleteColumnInfo logicDeleteColumn(Class<E> domainClass) {
        Assert.notNull(domainClass, "domainClass can not be null !");
        Field field = loop(domainClass);
        LogicDeleteColumn ldc = field.getAnnotation(LogicDeleteColumn.class);
        return LogicDeleteColumnInfo.builder().columnName(field.getName()).deleteValue(ldc.deleteValue()).notDeleteValue(ldc.notDeleteValue()).build();
    }

    /**
     * loop LogicDeleteColumn form the Fields
     * @param domainClass
     * @return
     */
    private static <E extends BaseEntity> Field loop(Class<E> domainClass){
        Field[] fields = domainClass.getDeclaredFields();
        for (Field f : fields) {
            if(Modifier.isStatic(f.getModifiers())||Modifier.isFinal(f.getModifiers()))continue;
            f.setAccessible(true);
            LogicDeleteColumn ldc = f.getAnnotation(LogicDeleteColumn.class);
            if (ldc != null){
                return f;
            }
        }
        return loop(BaseEntity.class);
    }


    @Data
    @Builder
    @ToString
    public static class LogicDeleteColumnInfo {
        /**
         * 字段名
         */
        private String columnName;
        /**
         * 删除字段值
         */
        private BaseBizeStatusEnum.YesOrNo deleteValue;
        /**
         * 非删除字段值
         */
        private BaseBizeStatusEnum.YesOrNo notDeleteValue;
    }

    @Data
    @Builder
    @ToString
    public static class LogicDeleteStrategyInfo {

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
