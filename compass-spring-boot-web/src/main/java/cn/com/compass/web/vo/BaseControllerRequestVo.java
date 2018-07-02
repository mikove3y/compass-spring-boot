package cn.com.compass.web.vo;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import cn.com.compass.base.vo.BaseDataX;
import cn.com.compass.base.vo.BaseRequestPageVo;
import lombok.Getter;
import lombok.Setter;
/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 基类controller请求参数封装
 * @date 2018年6月28日 下午7:30:41
 *
 */
public class BaseControllerRequestVo {
	
	/**
	 * 新增单条记录
	 */
	public static  class AddOne extends BaseDataX {
		
		private static final long serialVersionUID = -1122678082058697210L;
		
	}
	
	/**
	 * 批量增加记录
	 */
	@Getter
	@Setter
	public static class AddBatch implements Serializable {
		
		private static final long serialVersionUID = -7959597882674797944L;
		@NotNull(message="list can not be empty")
		@Size(min=1,message="list can not be empty")
		private List<AddOne> list;
		
	}
	
	/**
	 * 批量删除记录
	 */
	@Getter
	@Setter
	public static  class DeleteBatch implements Serializable {

		private static final long serialVersionUID = 3929950643557226865L;
		@NotNull(message="ids can not be empty")
		@Size(min=1,message="ids can not be empty")
		private List<Long> ids;
		
	}
	
	/**
	 * 单条更新记录
	 */
	@Getter
	@Setter
	public static class UpdateOne extends BaseDataX {

		private static final long serialVersionUID = 3080869245679812222L;
		@NotNull(message="id can not be null")
		private Long id;
		
	}
	
	/**
	 * 批量更新记录
	 */
	@Getter
	@Setter
	public static class UpdateBatch implements Serializable {
		
		private static final long serialVersionUID = -3796278925186438303L;
		@NotNull(message="list can not be empty")
		@Size(min=1,message="list can not be empty")
		private List<UpdateOne> list;
	}
	
	/**
	 * 查询列表
	 */
	public static class GetList extends BaseDataX {

		private static final long serialVersionUID = 5777352902371832824L;
		
	}
	
	/**
	 * 查询分页
	 */
	public static class GetPage extends BaseRequestPageVo {
		
		private static final long serialVersionUID = -2276205545836799150L;
		
	}

}
