package cn.com.compass.starter.convert;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import cn.com.compass.base.constant.IBaseBizStatusEnum;

/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo spring message convert
 *       自定义integr-string转enum转换器,注册到WebMvcConfigurerAdapter中的FormatterRegistry
 * @date 2018年6月7日 下午2:17:54
 *
 */
public class UniversalEnumConverterFactory implements ConverterFactory<String, IBaseBizStatusEnum> {

	/**
	 * 使用weakHashMap 偶尔自动触发gc
	 */
	private static final Map<Class, Converter> converterMap = new WeakHashMap<>();

	@Override
	public <T extends IBaseBizStatusEnum> Converter<String, T> getConverter(Class<T> targetType) {
		Converter result = converterMap.get(targetType);
		if (result == null) {
			result = new IntegerStrToEnum<T>(targetType);
			converterMap.put(targetType, result);
		}
		return result;
	}

	/**
	 * 
	 * @author wanmk
	 * @git https://gitee.com/milkove
	 * @email 524623302@qq.com
	 * @todo integer to enum
	 * @date 2018年6月7日 下午2:25:12
	 *
	 */
	private class IntegerStrToEnum<T extends IBaseBizStatusEnum> implements Converter<String, T> {

		private Map<String, T> enumMap = new HashMap<>();

		public IntegerStrToEnum(Class<T> enumType) {
			T[] enums = enumType.getEnumConstants();
			for (T e : enums) {
				enumMap.put(e.getCode().toString(), e);
			}
		}

		@Override
		public T convert(String source) {
			T result = enumMap.get(source);
			if (result == null) {
				throw new IllegalArgumentException("No element matches " + source);
			}
			return result;
		}
	}

}
