/**
 * 
 */
package cn.com.compass.util;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo fastJson一些常用的转换添加到jackson
 * @date 2018年9月19日 下午9:37:43
 * 
 */
final public class FastJsonSerializerFeatureCompatibleForJackson extends BeanSerializerModifier {
	
	final private JsonSerializer<Object> nullBooleanJsonSerializer;// 空Boolean序列化
	final private JsonSerializer<Object> nullNumberJsonSerializer;// 空Number序列化
	final private JsonSerializer<Object> nullListJsonSerializer;// 空List序列化
	final private JsonSerializer<Object> nullStringJsonSerializer;// 空String序列化
	final private JsonSerializer<Object> nullObjectJsonSerializer;// 空Object序列化

	FastJsonSerializerFeatureCompatibleForJackson(SerializerFeature... features) {
		int config = 0;
		for (SerializerFeature feature : features) {
			config |= feature.mask;
		}
		nullBooleanJsonSerializer = (config & SerializerFeature.WriteNullBooleanAsFalse.mask) != 0 ? new NullBooleanSerializer() : null;
		nullNumberJsonSerializer = (config & SerializerFeature.WriteNullNumberAsZero.mask) != 0 ? new NullNumberSerializer() : null;
		nullListJsonSerializer = (config & SerializerFeature.WriteNullListAsEmpty.mask) != 0 ? new NullListJsonSerializer() : null;
		nullStringJsonSerializer = (config & SerializerFeature.WriteNullStringAsEmpty.mask) != 0 ? new NullStringSerializer() : null;
		nullObjectJsonSerializer = (config & SerializerFeature.WriteNullObjectAsEmpty.mask) != 0 ? new NullObjectSerializer() : null;
	}

	@Override
	public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc,
			List<BeanPropertyWriter> beanProperties) {
		for (BeanPropertyWriter writer : beanProperties) {
			final JavaType javaType = writer.getType();
			final Class<?> rawClass = javaType.getRawClass();
			if (javaType.isArrayType() || javaType.isCollectionLikeType()) {
				writer.assignNullSerializer(nullListJsonSerializer);
			} else if (Number.class.isAssignableFrom(rawClass) && rawClass.getName().startsWith("java.lang")) {
				writer.assignNullSerializer(nullNumberJsonSerializer);
			} else if (Boolean.class.equals(rawClass)) {
				writer.assignNullSerializer(nullBooleanJsonSerializer);
			} else if (String.class.equals(rawClass)) {
				writer.assignNullSerializer(nullStringJsonSerializer);
			} else if (/*javaType.isEnumType() ||*/ javaType.isMapLikeType() || Object.class.equals(rawClass)) {
				writer.assignNullSerializer(nullObjectJsonSerializer);
			}
		}
		return beanProperties;
	}
	
	/**
	 * 空list 转[]
	 */
	private static class NullListJsonSerializer extends JsonSerializer<Object> {
		@Override
		public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
			jgen.writeStartArray();
			jgen.writeEndArray();
		}
	}
	
	/**
	 * 空number转0
	 */
	private static class NullNumberSerializer extends JsonSerializer<Object> {
		@Override
		public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
			jgen.writeNumber(0);
		}
	}
	
	/**
	 * 空boolean转false
	 */
	private static class NullBooleanSerializer extends JsonSerializer<Object> {
		@Override
		public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
			jgen.writeBoolean(false);
		}
	}
	
	/**
	 * 空字符串转""
	 */
	private static class NullStringSerializer extends JsonSerializer<Object> {
		@Override
		public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
			jgen.writeString("");
		}
	}
	
	/**
	 * 空对象转{}
	 */
	private static class NullObjectSerializer extends JsonSerializer<Object> {
		@Override
		public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
			jgen.writeStartObject();
			jgen.writeEndObject();
		}
	}

}
