package cn.com.compass.base.vo;

import java.util.HashMap;
import java.util.Map;

import cn.com.compass.base.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 分页查询请求vo
 * @date 2018年6月6日 下午2:47:26
 *
 */
@Getter
@Setter
public class BaseRequestPageVo extends BaseDataX{

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
	 * 排序字段以及升降序 </br>
	 * key: order colum default createTime </br>
	 * value: isAsc default false </br>
	 */
	private Map<String,Boolean> orders = new HashMap<>();
	/**
	 * default order by column createTime false
	 * 
	 * @return
	 */
	public Map<String,Boolean> getOrders() {
		if(orders.isEmpty()) {
			orders.put(BaseEntity.CREATETIME, Boolean.FALSE);
		}
		return orders;
	}

}
