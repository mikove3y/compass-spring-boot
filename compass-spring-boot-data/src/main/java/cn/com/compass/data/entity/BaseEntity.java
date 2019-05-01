package cn.com.compass.data.entity;

import cn.com.compass.base.constant.BaseBizeStatusEnum;
import cn.com.compass.data.annotation.LogicDeleteColumn;
import cn.com.compass.data.interceptor.JpaCrudInterceptor;
import lombok.Data;
import org.activejpa.entity.Model;
import org.hibernate.annotations.Type;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo jpa基类baseEntity，非完整实体不进行建表，包含所有子类entity通用字段,扩展提供JPA Active Model能力
 * @date 2018年6月6日 下午2:50:02
 * @see AbstractPersistable
 */
@Data
@MappedSuperclass
@EntityListeners(JpaCrudInterceptor.class)
public class BaseEntity<PK extends Serializable> extends Model implements Persistable<PK> {

	private static final long serialVersionUID = 4076489068887449464L;
	/**
	 * <hr>
	 * 主键类型固定为long类型 </br>
	 * 1、MYSQL 主键自增 strategy=GenerationType.AUTO </br>
	 * 2、ORACLE 主键自增strategy= GenerationType.SEQUENCE </br>
	 * create sequence product_id_seq increment by 1 start with 1;</br>
	 * 3、SQLSERVER 主键自增 strategy=GenerationType.IDENTITY</br>
	 * <hr>
	 * –IDENTITY：采用数据库ID自增长的方式来自增主键字段，Oracle 不支持这种方式； </br>
	 * –AUTO： JPA自动选择合适的策略，是默认选项； </br>
	 * –SEQUENCE：通过序列产生主键，通过@SequenceGenerator 注解指定序列名，MySql不支持这种方式</br>
	 * –TABLE：通过表产生主键，框架借由表模拟序列产生主键，使用该策略可以使应用更易于数据库移植</br>
	 * <hr>
	 * 综合考虑以上几种情况，最终选用table主键策略，方便在不改动代码情况下数据库迁移，<strong>并发量比较小的情况下使用，如并发比较高建议使用uuid或者guid</strong></br>
	 * {@link javax.persistence.TableGenerator} </br>
	 * 属性说明： </br>
	 * name 表示该表主键生成策略的名称，它被引用在@GeneratedValue中设置的“generator”值中。 </br>
	 * table 表示表生成策略所持久化的表名，例如，这里表使用的是数据库中的“sys_pk_generator”。 </br>
	 * catalog 和schema具体指定表所在的目录名或是数据库名。 </br>
	 * pkColumnName
	 * 的值表示在持久化表中，该主键生成策略所对应键值的名称。例如在“sys_pk_generator”中将“name”作为主键的键值</br>
	 * valueColumnName
	 * 的值表示在持久化表中，该主键当前所生成的值，它的值将会随着每次创建累加。例如，在“sys_pk_generator”中将“value”作为主键的值</br>
	 * pkColumnValue
	 * 的值表示在持久化表中，该生成策略所对应的主键。例如在“sys_pk_generator”表中，将“name”的值为“customer_pk”。 </br>
	 * initialValue 表示主键初识值，默认为0。 </br>
	 * allocationSize 表示每次主键值增加的大小，例如设置成1，则表示每次创建新记录后自动加1，默认为50。 </br>
	 * UniqueConstraint与@Table标记中的用法类似</br>
	 * <hr>
	 * Sql代码 ：mysql示例 </br>
	 * create table sys_pk_generator ( id bigint(20) not null, name varchar(255) not
	 * null, value bigint(20) not null, primary key(id))
	 * <hr>
	 * 插入纪录，供生成主键使用 ：mysql示例 </br>
	 * insert into sys_pk_generator(id, name, value) values (1,'customer_pk', 1);
	 * <hr>
	 * <h1>使用sharding-jdbc分库分表框架</h1>
	 */

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// @SequenceGenerator(name =
	// "prodG",sequenceName="product_id_seq",allocationSize=1) /*ORACLE*/
	// @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prodG")
	// @GeneratedValue(strategy=GenerationType.IDENTITY) /*SQLSERVER*/
	// @GeneratedValue(strategy = GenerationType.TABLE, generator = "pk_gen")
	// @TableGenerator(name = "pk_gen", table = "sys_pk_generator", pkColumnName =
	// "name", valueColumnName = "value", pkColumnValue = "customer_pk",
	// initialValue = 1,allocationSize = 1)
	@Id
	private PK id;
	// id 作为主键每张表存在差异性，这里不做强制
	/**
	 * 创建人Id
	 */
	private PK creatorId;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新人Id
	 */
	private PK lastUpdaterId;
	/**
	 * 更新时间
	 */
	private Date lastUpdateTime;
	/**
	 * 版本号
	 */
	@Version
	private Long version;
	/**
	 * 逻辑删除字段
	 */
	@LogicDeleteColumn
	@Type(type="cn.com.compass.data.convert.JpaDbEnumTypeHandler")
	private BaseBizeStatusEnum.YesOrNo enabled;

	public static final String ID = "id";

	public static final String VERSION = "version";

	public static final String CREATETIME = "createTime";

	public static final String CREATORID = "creatorId";
	
	public static final String LASTUPDATETIME = "lastUpdateTime";

	public static final String LASTUPDATERID = "lastUpdaterId";

	public static final String ENABLED = "enabled";

	/**
	 * Returns if the {@code Persistable} is new or was persisted already.
	 *
	 * @return if the object is new
	 */
	@Override
	@Transient// DATAJPA-622
	public boolean isNew() {
		return null == getId();
	}
}
