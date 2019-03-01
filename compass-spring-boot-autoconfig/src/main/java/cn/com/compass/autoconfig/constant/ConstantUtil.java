package cn.com.compass.autoconfig.constant;

import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 常量工具类
 * @date 2018年6月6日 下午2:52:18
 *
 */
public class ConstantUtil {
	
	@Autowired
	private ConstantProperties properties;
	
	/**
	 * 通过key 获取常量值
	 * @param key
	 * @return
	 */
	public String getValue(String key) {
		Map<String,String> enums = this.getAllEnmus();
		if(MapUtils.isNotEmpty(enums))
			return enums.get(key);
		return null;
	}
	
	/**
	 * 获取所有常量枚举
	 * @return
	 */
	public Map<String,String> getAllEnmus(){
		Map<Lang,Map<String,String>> enums =properties.getEnums();
		if(MapUtils.isNotEmpty(enums))
			return enums.get(properties.getLang());
		return null;
	}
}
