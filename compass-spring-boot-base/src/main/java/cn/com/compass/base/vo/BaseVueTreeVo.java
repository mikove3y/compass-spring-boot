package cn.com.compass.base.vo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo vue树vo
 * @date 2018年7月24日 上午10:01:11
 *
 */
@Getter
@Setter
public class BaseVueTreeVo implements Serializable{
	
	private static final long serialVersionUID = -4007798288234006756L;
	/**
	 * 节点Id
	 */
	private Long id;
	/**
	 * 节点label
	 */
	private String label;
	/**
	 * 是否禁用
	 */
	private Boolean disabled;
	/**
	 * 是否子节点
	 */
	private Boolean isLeaf;
	/**
	 * children
	 */
	private BaseVueTreeVo[] children;
	
	public BaseVueTreeVo() {
		
	}
	
	public BaseVueTreeVo(Long id,String label) {
		
	}
	
	public BaseVueTreeVo(Long id,String label,Boolean disabled,Boolean isLeaf) {
		
	}
	

}
