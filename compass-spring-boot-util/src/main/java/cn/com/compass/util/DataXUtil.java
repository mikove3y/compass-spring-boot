package cn.com.compass.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.collections.MapUtils;

/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 数据交换工具
 * @date 2018年6月29日 下午12:54:40
 *
 */
public class DataXUtil {

	/**
	 * 
	 * @param source
	 *            来源obj 支持类型 DynaBean、map、javaBean
	 * @param target 
	 *            目标obj 支持类型 DynaBean、javaBean
	 * @param propertyMapping
	 *            字段映射关系 k-v key为source字段 value为target字段
	 * @throws Exception
	 */
	public static void copyProperties(Object source, Object target, Map<String, String> source2targetProperties) throws Exception {
		if (MapUtils.isEmpty(source2targetProperties)) {
			// mapping source 2 target
			Map<String, Object> sourceMap = JacksonUtil.obj2MapIgnoreNull(source);
			Map<String, Object> sourceMapX = new HashMap<>();
			Iterator<String> sourceIt = source2targetProperties.keySet().iterator();
			while(sourceIt.hasNext()) {
				// source k-v
				String sourceKey = sourceIt.next();
				// not contain skip
				if(!sourceMap.containsKey(sourceKey))continue;
				Object sourceValue = sourceMap.get(sourceKey);
				// target k-v
				String targetKey = source2targetProperties.get(sourceKey);
				sourceMapX.put(targetKey, sourceValue);
			}
			// copy source 2 target
			BeanUtilsBean2.getInstance().copyProperties(target, sourceMapX);
		} else {
			// copy source 2 target
			BeanUtilsBean2.getInstance().copyProperties(target, source);
		}
	}

}
