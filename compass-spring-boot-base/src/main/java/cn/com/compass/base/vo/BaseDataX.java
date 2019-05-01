package cn.com.compass.base.vo;

import cn.com.compass.base.util.DataXUtil;
import cn.com.compass.base.util.JacksonUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.collections.MapUtils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 针对查询请求-数据字段映射、排序、驼峰转下划线
 * @date 2018年6月29日 下午1:16:09
 *
 */
public class BaseDataX {

	@JsonIgnore
	public BaseDataX(){
		this.addSource2TargetProperties();
		this.addOrder();
	}

	/**
	 * 字段转换映射Map,需要转换的A/B class属性必须保持一致
	 * k 来源字段
	 * v 目标字段
	 */
	@JsonIgnore
	private static final Map<String,String> source2TargetProperties = new HashMap<>();

	/**
	 * 排序字段list
	 */
	@JsonIgnore
	private static final List<String> orders = new LinkedList<>();

	// -----------------------------转换----------------------------
	/**
	 * 对照字段
	 */
	@JsonIgnore
	public Map<String,String> source2TargetProperties(){
		return source2TargetProperties;
	}

	/**
	 * 添加转换字段
	 */
	@JsonIgnore
	public void addSource2TargetProperties(){

	}

	/**
	 * 添加转换字段
	 * @param key
	 * @param value
	 */
	@JsonIgnore
	public BaseDataX addSource2TargetProperties(String key,String value){
		this.source2TargetProperties().put(key,value);
		return this;
	}

	/**
	 * 添加转换字段
	 * @param map
	 */
	@JsonIgnore
	public BaseDataX addSource2TargetProperties(Map<String,String> map){
		this.source2TargetProperties().putAll(map);
		return this;
	}


	// -----------------------------排序----------------------------
	/**
	 * 排序字段
	 * @return
	 */
	@JsonIgnore
	public  List<String> orders(){
		return orders;
	}

	/**
	 * 添加排序字段
	 */
	@JsonIgnore
	public void addOrder(){

	}

	/**
	 * 添加排序字段
	 * @param key
	 */
	@JsonIgnore
	public BaseDataX addOrder(String key){
		this.orders().add(key);
		return this;
	}

	/**
	 * 添加排序字段
	 * @param keys
	 */
	@JsonIgnore
	public BaseDataX addOrder(LinkedList<String> keys){
		this.orders().addAll(keys);
		return this;
	}

	/**
	 * 是否升序，默认升序
	 * @return
	 */
	@JsonIgnore
	public boolean isAsc(){
		return true;
	}

	/**
	 * 是否驼峰转下划线，针对mybatis xml 中获取orders数据
	 * @return
	 */
	@JsonIgnore
	public boolean camel2Underline(){
		return false;
	}

	/**
	 * 获取查询参数
	 * @return
	 */
	public Map<String,Object> params(){
		Map<String,Object> params = MapUtils.EMPTY_MAP;
		params = JacksonUtil.obj2pojo(this,Map.class);
		if(this.camel2Underline()&&MapUtils.isNotEmpty(params)){
			Map<String,Object> target = new HashMap<>();
			// 将所有的key转换为 underline
			params.forEach((k,v)->{
				target.put(DataXUtil.camelToUnderline(k),v);
			});
			return target;
		}
		return params;
	}

}
