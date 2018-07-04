package cn.com.compass.web.vo;

import javax.validation.constraints.NotNull;

import cn.com.compass.base.vo.BaseDataX;
import lombok.Getter;
import lombok.Setter;

/**
 * 单条更新记录
 */
@Getter
@Setter
public class BaseUpdateOneRequestVo extends BaseDataX {

	private static final long serialVersionUID = 3080869245679812222L;
	@NotNull(message="id can not be null")
	private Long id;
	
}