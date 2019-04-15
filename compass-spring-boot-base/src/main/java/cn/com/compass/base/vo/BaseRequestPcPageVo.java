package cn.com.compass.base.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo pc分页查询请求vo
 * @date 2018年6月6日 下午2:47:26
 *
 */
@Data
public abstract class BaseRequestPcPageVo extends BaseDataX{

	private static final long serialVersionUID = 8464373602146221705L;
	/**
	 * 页码
	 */
	private Integer pageNo = 1;
	/**
	 * 页条数
	 */
	private Integer pageSize = 20;

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
