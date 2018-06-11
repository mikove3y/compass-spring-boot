package cn.com.compass.data.dialect;

import org.hibernate.dialect.PostgreSQL9Dialect;
/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo jpa postgre 方言引擎，主要处理entity onetomany manytoone等字段生成外键，此处默认不生成
 * @date 2018年6月6日 下午3:51:39
 *
 */
public class PostgreSQL9DialectWithoutFK extends PostgreSQL9Dialect {
	@Override  
    public String getAddForeignKeyConstraintString(  
            String constraintName,  
            String[] foreignKey,  
            String referencedTable,  
            String[] primaryKey,  
            boolean referencesPrimaryKey) {  
//      设置foreignkey对应的列值可以为空  
        return " alter "+ foreignKey[0] +" set default null " ;  
    }  
}
