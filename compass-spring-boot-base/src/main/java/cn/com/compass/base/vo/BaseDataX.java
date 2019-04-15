package cn.com.compass.base.vo;

import cn.com.compass.util.DataXUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.apache.commons.lang3.ArrayUtils;

import java.io.Serializable;
import java.util.*;

/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 针对查询请求-数据字段映射、排序、驼峰转下划线
 * @date 2018年6月29日 下午1:16:09
 *
 */
public abstract class BaseDataX {

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
	public Map<String,String> source2TargetProperties(){
		return source2TargetProperties;
	}

	/**
	 * 添加转换字段
	 */
	@JsonIgnore
	public abstract void addSource2TargetProperties();


	// -----------------------------排序----------------------------
	/**
	 * 排序字段
	 * @return
	 */
	public  Map<String,Boolean> orders(){
		return orders;
	}

	/**
	 * 添加排序字段
	 */
	@JsonIgnore
	public abstract void addOrder();

	/**
	 * 是否驼峰转下划线，针对mybatis xml 中获取orders数据
	 * @return
	 */
	@JsonIgnore
	public abstract boolean camel2Underline();

}
