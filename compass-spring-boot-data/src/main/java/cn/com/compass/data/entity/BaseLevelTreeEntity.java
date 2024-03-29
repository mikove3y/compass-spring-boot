package cn.com.compass.data.entity;

import lombok.Data;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 层级树基础实体
 * @date 2018年7月19日 下午12:26:37
 *
 */
@Data
@MappedSuperclass
public class BaseLevelTreeEntity<PK extends Serializable> extends BaseEntity<PK> {

	private static final long serialVersionUID = -5445434931452155963L;
	/**
	 * 层级编码
	 */
	private String code;
	/**
	 * 层级名
	 */
	private String name;
	/**
	 * 层级
	 */
	private Integer level;
	/**
	 * 排序
	 */
	private Integer sortNum;
	/**
	 * 父Id
	 */
	private Long parentId;
	
	public static final String CODE = "code";
	public static final String NAME = "name";
	public static final String LEVEL = "level";
	public static final String SORTNUM = "sortNum";
	public static final String PARENTID = "parentId";

}
