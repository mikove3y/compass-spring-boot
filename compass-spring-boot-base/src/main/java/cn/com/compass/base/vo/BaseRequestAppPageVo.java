package cn.com.compass.base.vo;

import java.util.HashMap;
import java.util.Map;

import cn.com.compass.base.entity.BaseEntity;
import cn.com.compass.util.DataXUtil;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseRequestAppPageVo extends BaseDataX{

	private static final long serialVersionUID = -2113556648016554107L;
	/**
	 * 主键Id
	 */
	private Long dataId ;
	/**
	 * 页条数
	 */
	private Integer pageSize = 12;
	/**
	 * 排序字段以及升降序 </br>
	 * key: order colum default createTime </br>
	 * value: isAsc default false </br>
	 */
	private Map<String,String> orders = new HashMap<>();
	/**
	 * default order by column createTime asc false
	 * 
	 * @return
	 */
	public Map<String,String> getOrders() {
		if(orders.isEmpty()) {
			orders.put(BaseEntity.CREATETIME, ORDER_DESC);
		}
		if(this.getCamel2Underline()) {
			Map<String,String> underlineOrders = new HashMap<>();
			for(Map.Entry<String,String> en : orders.entrySet()) {
				underlineOrders.put(DataXUtil.camelToUnderline(en.getKey()), en.getValue());
			}
			this.orders = underlineOrders;
		}
		return orders;
	}
	

}
