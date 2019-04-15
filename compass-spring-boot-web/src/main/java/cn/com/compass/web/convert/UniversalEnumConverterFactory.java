package cn.com.compass.web.convert;

import cn.com.compass.base.constant.IBaseBizStatusEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo spring message convert
 *       自定义integr-string转enum转换器,注册到WebMvcConfigurerAdapter中的FormatterRegistry
 * 注意:该转换器仅能处理简单参数，如请求查询参数，url路径参数等，复杂的对象参数不行
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
			result = new StringToEnum<T>(targetType);
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
	private class StringToEnum<T extends IBaseBizStatusEnum> implements Converter<String, T> {

		private final Class<T> targerType;

		public StringToEnum(Class<T> targerType) {
			this.targerType = targerType;
		}

		@Override
		public T convert(String source) {
			return IBaseBizStatusEnum.fromCode(targerType, source);
		}
	}

}
