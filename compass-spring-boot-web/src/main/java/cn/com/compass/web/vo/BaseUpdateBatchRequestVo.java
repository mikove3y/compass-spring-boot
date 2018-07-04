package cn.com.compass.web.vo;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

/**
 * 批量更新记录
 */
@Getter
@Setter
public class BaseUpdateBatchRequestVo implements Serializable {
	
	private static final long serialVersionUID = -3796278925186438303L;
	@NotNull(message="list can not be empty")
	@Size(min=1,message="list can not be empty")
	private List<BaseUpdateOneRequestVo> list;
}
