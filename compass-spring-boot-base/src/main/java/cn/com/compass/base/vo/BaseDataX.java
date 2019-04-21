package cn.com.compass.base.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashMap;
import java.util.LinkedHashMap;
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
	 * 字段转换映射Map
	 * k 来源字段
	 * v 目标字段
	 */
	@JsonIgnore
	private static final Map<String,String> source2TargetProperties = new HashMap<>();

	/**
	 * 排序字段Map
	 * k 排序字段
	 * v 是否升序
	 */
	@JsonIgnore
	private static final Map<String,Boolean> orders = new LinkedHashMap<>();

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
	public void addSource2TargetProperties(String key,String value){
		this.source2TargetProperties().put(key,value);
	}

	/**
	 * 添加转换字段
	 * @param map
	 */
	@JsonIgnore
	public void addSource2TargetProperties(Map<String,String> map){
		this.source2TargetProperties().putAll(map);
	}


	// -----------------------------排序----------------------------
	/**
	 * 排序字段
	 * @return
	 */
	@JsonIgnore
	public  Map<String,Boolean> orders(){
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
	 * @param value
	 */
	@JsonIgnore
	public void addOrder(String key,Boolean value){
		this.orders().put(key,value);
	}

	/**
	 * 添加排序字段
	 * @param map
	 */
	@JsonIgnore
	public void addOrder(Map<String,Boolean> map){
		this.orders().putAll(map);
	}

	/**
	 * 是否驼峰转下划线，针对mybatis xml 中获取orders数据
	 * @return
	 */
	@JsonIgnore
	public boolean camel2Underline(){
		return false;
	}

}
