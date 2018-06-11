package cn.com.compass.base.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 基础请求vo
 * @date 2018年6月6日 下午2:45:26
 *
 */
@Getter
@Setter
public class BaseRequestVo implements Serializable{

	private static final long serialVersionUID = 8464373602146221705L;
	
	private Map<String,Object> conditions = new HashMap<>();

}
