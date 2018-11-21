package cn.com.compass.base.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
	 * 排序字段
	 */
	private List<String> orderCols = new ArrayList<>();
	/**
	 * 排序k-v
	 */
	private Map<String,String> orders = new HashMap<>();
	/**
	 * 是否升序
	 */
	private Boolean isAsc = false;
	/**
	 * 表别名
	 */
	private List<String> tableAlias;
	/**
	 * default order by column createTime asc false
	 * 
	 * @return
	 */
	public Map<String,String> getOrders() {
		if(orderCols.isEmpty()) {
			orderCols.add(BaseEntity.ID);
		}
		if(this.getCamel2Underline()) {
			Map<String,String> underlineOrders = new HashMap<>();
			for(String order : orderCols) {
				underlineOrders.put(DataXUtil.camelToUnderline(order),this.getIsAsc()?BaseDataX.ORDER_ASC:BaseDataX.ORDER_DESC);
			}
			this.orders = underlineOrders;
		}
		return orders;
	}
	

}
