package cn.com.compass.base.vo;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 基类层级树响应vo
 * @date 2018年8月14日 下午12:21:40
 *
 */
@Getter
@Setter
public class BaseLevelTreeResponseVo<V extends BaseLevelTreeResponseVo<V>> implements Serializable {

	private static final long serialVersionUID = -2210097770425272702L;
	/**
	 * 子节点id
	 */
	private Long id;
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
	/**
	 * 子节点
	 */
	private List<V> child;
}
