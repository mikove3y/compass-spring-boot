package cn.com.compass.web.vo;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

/**
 * 批量删除记录
 */
@Getter
@Setter
public class BaseDeleteBatchRequestVo implements Serializable {

	private static final long serialVersionUID = 3929950643557226865L;
	@NotNull(message="ids can not be empty")
	@Size(min=1,message="ids can not be empty")
	private List<Long> ids;
	
}
