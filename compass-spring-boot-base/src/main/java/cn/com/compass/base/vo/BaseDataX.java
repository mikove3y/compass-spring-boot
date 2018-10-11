package cn.com.compass.base.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
				if(source2targetProperties.containsKey(source)) continue;
				source2targetProperties.put(source, target);
			}
		}
		return source2targetProperties;
	}
	
	/**
	 * 转换键值对
	 */
	@JsonIgnore
	private String[][] pairs;
	/**
	 * orders字段是否需要驼峰转下划线，针对mybatis xml 查询处理
	 */
	@JsonIgnore
	private Boolean camel2Underline = true;
	
	@JsonIgnore
	public Boolean getCamel2Underline() {
		return camel2Underline;
	}
	
	@JsonIgnore
	public void setCamel2Underline(Boolean camel2Underline) {
		this.camel2Underline = camel2Underline;
	}

	@JsonIgnore
	public String[][] getPairs() {
		return pairs;
	}
	
	@JsonIgnore
	public void setPairs(String[][] pairs) {
		// 合并二维数组
		if(this.pairs!=null&&this.pairs.length>0) {
			List<String[]> npl = new ArrayList<>();
			for(String[] o : pairs)
				npl.add(o);
			for(String[] o : this.pairs)
				npl.add(o);
			// can't use Arrays.asList()。。。 what's amazing
			// can't use System.arraycopy。。。 what's amazing 
//			String[][] np = new String[][] {};
//			System.arraycopy(this.pairs, 0, np, 0, this.pairs.length);
//			System.arraycopy(pairs, 0, np, np.length, pairs.length);
			this.pairs = npl.toArray(new String[0][]);
		}else {
			this.pairs = pairs;
		}
	}
	/**
	 * 降序
	 */
	public static final String ORDER_DESC = "desc";
	/**
	 * 升序
	 */
	public static final String ORDER_ASC = "asc";

}
