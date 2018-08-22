package cn.com.compass.base.vo;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 分页封装
 * @date 2018年6月6日 下午2:46:11
 *
 */
@Getter
@Setter
public class PcPage<T> {
	/**
	 * 当前页码
	 */
	private int current = 1;
	/**
	 * 每页条目数
	 */
	private int size = 20;
	/**
	 * 查询总数
	 */
	private long total;
	/**
	 * 当前页返回数据集
	 */
	private List<T> records;
	/**
	 * 扩展数据信息
	 */
	private Map<String,Object> extend;
}
