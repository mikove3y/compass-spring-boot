package cn.com.compass.base.vo;

import lombok.Data;

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
public class BaseRequestPcPageVo extends BaseDataX{

	private static final long serialVersionUID = 8464373602146221705L;
	/**
	 * 页码
	 */
	private int pageNo = 1;
	/**
	 * 页条数
	 */
	private int pageSize = 20;

}
