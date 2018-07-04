package cn.com.compass.web.vo;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

/**
 * 批量增加记录
 */
@Getter
@Setter
public class BaseAddBatchRequestVo implements Serializable{

	private static final long serialVersionUID = 4380268252864559218L;
	
	@NotNull(message="list can not be empty")
	@Size(min=1,message="list can not be empty")
	private List<BaseAddOneRequestVo> list;

}
