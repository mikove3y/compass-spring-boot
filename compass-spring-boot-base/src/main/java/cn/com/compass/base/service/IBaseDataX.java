package cn.com.compass.base.service;

import java.io.Serializable;
import java.util.Map;

/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 数据字段映射接口
 * @date 2018年6月29日 下午1:16:09
 *
 */
public interface IBaseDataX extends Serializable{
	/**
	 * k-v </br>
	 * key: source property </br>
	 * value: target property </br>
	 * @return
	 */
	public Map<String,String> source2targetProperties();

}
