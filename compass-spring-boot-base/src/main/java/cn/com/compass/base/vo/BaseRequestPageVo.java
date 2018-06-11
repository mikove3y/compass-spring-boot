package cn.com.compass.base.vo;

import java.util.ArrayList;
import java.util.List;

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
public class BaseRequestPageVo extends BaseRequestVo {

	private static final long serialVersionUID = 8464373602146221705L;

	private Integer pageNo = 1;

	private Integer pageSize = 20;

	private Boolean isAsc = true;

	private List<String> orders = new ArrayList<>();
	
	/**
	 * default order by column createTime
	 * 
	 * @return
	 */
	public List<String> getOrders() {
		if (this.orders.size() == 0) {
			this.orders.add(BaseEntity.CREATETIME);
		}
		return this.orders;
	}

}
