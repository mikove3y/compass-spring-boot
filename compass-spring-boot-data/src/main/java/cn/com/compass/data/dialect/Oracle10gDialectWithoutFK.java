package cn.com.compass.data.dialect;

import org.hibernate.dialect.Oracle10gDialect;
/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo jpa oracle 方言引擎，主要处理entity onetomany manytoone等字段生成外键，此处默认不生成
 * @date 2018年6月6日 下午3:51:39
 *
 */
public class Oracle10gDialectWithoutFK extends Oracle10gDialect {
	@Override  
    public String getAddForeignKeyConstraintString(  
            String constraintName,  
            String[] foreignKey,  
            String referencedTable,  
            String[] primaryKey,  
            boolean referencesPrimaryKey) {  
//      通过修改外键列的默认值，而不是添加外键，避免生成外键  
        return " modify "+ foreignKey[0] +" default null " ;  
    }  
}
