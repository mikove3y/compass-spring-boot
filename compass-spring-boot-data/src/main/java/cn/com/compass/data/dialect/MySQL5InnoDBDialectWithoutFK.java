package cn.com.compass.data.dialect;

import org.hibernate.dialect.MySQL5InnoDBDialect;
/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo jpa mysql 方言引擎，主要处理entity onetomany manytoone等字段生成外键，此处默认不生成
 * @date 2018年6月6日 下午3:51:39
 *
 */
public class MySQL5InnoDBDialectWithoutFK extends MySQL5InnoDBDialect {

	/**
	 * mysql 数据库OneToMany OneToOne ManyToOne ManyToMany 统一设置不生成外键</br>
	 * 两种处理方式：1、拼接设置column的默认值为null </br>
	 * 			2、拼接设置column添加索引		 </br>
	 */
	@Override
	public String getAddForeignKeyConstraintString(String constraintName, String[] foreignKey, String referencedTable,
			String[] primaryKey, boolean referencesPrimaryKey) {
		// 添加索引不建立外键关联，hibernate每次启动会对比schema,所以不生成外键关联会每次都自行增加索引会报索引已存在错误
//		String indexName = "INDEX_" + foreignKey[0].toUpperCase();
		String col = foreignKey[0];
//		return String.format(" add index %s (%s)", indexName, col);
		
		// 设置foreignkey对应的列值可以为空 " alter table 'table_name' alter column 'column_name' set default null "
		return String.format(" alter column %s set default null ", col);
	}
	
}
