package cn.com.compass.base.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseRequestAppPageVo extends BaseDataX{

	private static final long serialVersionUID = -2113556648016554107L;
	/**
	 * 主键Id
	 */
	private Serializable dataId = 0L;
	/**
	 * 页条数
	 */
	private int pageSize = 12;

}
