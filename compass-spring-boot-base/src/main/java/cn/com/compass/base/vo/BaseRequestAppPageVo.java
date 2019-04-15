package cn.com.compass.base.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public abstract class BaseRequestAppPageVo extends BaseDataX{

	private static final long serialVersionUID = -2113556648016554107L;
	/**
	 * 主键Id
	 */
	private Serializable dataId ;
	/**
	 * 页条数
	 */
	private Integer pageSize = 12;

	/**
	 * 添加转换字段
	 */
//	public void addSource2TargetProperties() {
//		source2TargetProperties().putAll();
//	}

	/**
	 * 添加排序字段
	 */
//	public void addOrder() {
//		orders().put("id",true);
//	}


}
