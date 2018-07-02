package cn.com.compass.base.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 数据字段映射类型
 * @date 2018年6月29日 下午1:16:09
 *
 */
public class BaseDataX implements Serializable{
	
	private static final long serialVersionUID = 2706722751432545941L;
	
	private static final Map<String,String> source2targetProperties = new HashMap<>();

	/**
	 * k-v unRepetd</br> 
	 * key: source property </br>
	 * value: target property </br>
	 * ex: {{"s1","t1"},{"s2","t2"},{"s3","t3"}} </br>
	 * @return
	 */
	@JsonIgnore
	public Map<String, String> source2targetProperties() {
		source2targetProperties.clear();
		String[][] pairs = this.getPairs();
		if (ArrayUtils.isNotEmpty(pairs)) {
			for (int i = 0; i < pairs.length; i++) {
				if(ArrayUtils.isEmpty(pairs[i]) || pairs[i].length != 2) continue;
				String source = pairs[i][0];
				String target = pairs[i][1];
				source2targetProperties.put(source, target);
			}
		}
		return source2targetProperties;
	}
	
	@JsonIgnore
	private String[][] pairs;
	
	@JsonIgnore
	public String[][] getPairs() {
		return pairs;
	}
	
	@JsonIgnore
	public void setPairs(String[][] pairs) {
		this.pairs = pairs;
	}
	

}
