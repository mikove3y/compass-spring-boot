package cn.com.compass.util;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.util.*;

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
	 * 空字符
	 */
	public static final String EMPTY = "";
	/**
	 * 下划线字符
	 */
	public static final char UNDERLINE = '_';

	/**
	 * 
	 * @param source
	 *            来源obj 支持类型 DynaBean、map、javaBean
	 * @param target
	 *            目标obj 支持类型 DynaBean、javaBean
	 * @param source2targetProperties
	 *            字段映射关系 k-v key为source字段 value为target字段
	 * @throws Exception
	 */
	public static Object copyProperties(Object source, Class<?> target, Map<String, String> source2targetProperties)
			throws Exception {
		if (source instanceof Collection) {
			// Collection集合映射
			Object[] sc = ((Collection<?>) source).toArray(new Object[0]);
			if (ArrayUtils.isEmpty(sc))
				return null;
			List<Object> result = new ArrayList<>();
			for (int i = 0; i < sc.length; i++) {
				Object temp = xOne(sc[i], target, source2targetProperties);
				result.add(temp);
			}
			return result;
		} else {
			// 单个对象映射
			return xOne(source, target, source2targetProperties);
		}
	}
	
	/**
	 * 单个对象映射转换
	 * @param source
	 * @param target
	 * @param source2targetProperties
	 * @throws Exception
	 */
	private static Object xOne(Object source, Class<?> target, Map<String, String> source2targetProperties) throws Exception {
		// 单个对象映射
		// mapping source 2 target
		Map<String, Object> sourceMap = JacksonUtil.obj2MapIgnoreNull(source);
		if (MapUtils.isNotEmpty(source2targetProperties)) {
			source2targetProperties.forEach((k, v) -> {
				// not contain skip
				Set<String> keys = sourceMap.keySet();
				if (keys.contains(k)) {
					Object sourceValue = sourceMap.get(k);
					// target k-v
					String targetKey = source2targetProperties.get(k);
					sourceMap.put(targetKey, sourceValue);
					sourceMap.remove(k);
				}
			});
		}
		// copy source 2 target
		return JacksonUtil.map2pojo(sourceMap, target);
//			BeanUtilsBean2.getInstance().copyProperties(target, source);
	}

	/**
	 *
	 * @param source
	 *            来源obj 支持类型 DynaBean、map、javaBean
	 * @param target
	 *            目标obj 支持类型 DynaBean、javaBean
	 * @param source2targetProperties
	 *            字段映射关系 k-v key为source字段 value为target字段
	 * @throws Exception
	 */
	public static void copyProperties(Object source, Object target, Map<String, String> source2targetProperties)
			throws Exception {

	}

	/**
	 * 单个对象映射转换
	 * @param source
	 * @param target
	 * @param source2targetProperties
	 * @throws Exception
	 */
	private static void xOne(Object source, Object target, Map<String, String> source2targetProperties) throws Exception {

	}
	/**
     * <p>
     * 字符串驼峰转下划线格式
     * </p>
     *
     * @param param 需要转换的字符串
     * @return 转换好的字符串
     */
	public static String camelToUnderline(String param) {
		if (isEmpty(param)) {
			return EMPTY;
		}
		int len = param.length();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c = param.charAt(i);
			if (Character.isUpperCase(c) && i > 0) {
				sb.append(UNDERLINE);
			}
			sb.append(Character.toLowerCase(c));
		}
		return sb.toString();
	}

	/**
	 * <p>
	 * 字符串下划线转驼峰格式
	 * </p>
	 *
	 * @param param
	 *            需要转换的字符串
	 * @return 转换好的字符串
	 */
	public static String underlineToCamel(String param) {
		if (isEmpty(param)) {
			return EMPTY;
		}
		String temp = param.toLowerCase();
		int len = temp.length();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c = temp.charAt(i);
			if (c == UNDERLINE) {
				if (++i < len) {
					sb.append(Character.toUpperCase(temp.charAt(i)));
				}
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}
	
	/**
	 * 字符串是否为空
	 * @param cs
	 * @return
	 */
	public static boolean isEmpty(final CharSequence cs) {
		int strLen;
		if (cs == null || (strLen = cs.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(cs.charAt(i))) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 是否包含小写
	 * @param s
	 * @return
	 */
	public static boolean containsLowerCase(String s) {
		for (char c : s.toCharArray()) {
			if (Character.isLowerCase(c)) {
				return true;
			}
		}
		return false;
	}

}
