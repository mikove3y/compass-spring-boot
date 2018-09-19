package cn.com.compass.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

public class JacksonObjectMapperWrapper extends ObjectMapper {

	private static final long serialVersionUID = 8056760953142914261L;

	private static final JacksonObjectMapperWrapper objectMapper;
	/**
	 * can not instanced
	 */
	private JacksonObjectMapperWrapper() {
		
	}

	static {
		objectMapper = new JacksonObjectMapperWrapper();
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);//忽略无法转换的对象
//		objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);//格式化输出
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);//允许对象忽略json中不存在的属性
		objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);//允许出现特殊字符和转义符
		objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);//允许出现单引号
		objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);// key允许出现无引号
//		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);// 为null的值去掉
		// 自定义null转换
		objectMapper.setSerializerFactory(objectMapper.
				getSerializerFactory().
				withSerializerModifier(new FastJsonSerializerFeatureCompatibleForJackson(
						SerializerFeature.WriteNullListAsEmpty,
						SerializerFeature.WriteNullStringAsEmpty,
						SerializerFeature.WriteNullNumberAsZero,
						SerializerFeature.WriteNullBooleanAsFalse,
						SerializerFeature.WriteNullObjectAsEmpty
						)));
		// 设置空 null 转 ''
		objectMapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
			@Override
			public void serialize(Object arg0, JsonGenerator arg1, SerializerProvider arg2)
					throws IOException, JsonProcessingException {
				arg1.writeString("");
			}
		});
		// 时间格式化
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		objectMapper.setDateFormat(dateFormat);// 格式化时间
		objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));//设置时区
		// 数字转 string
		SimpleModule simpleModule = new SimpleModule();
		simpleModule.addSerializer(Number.class, ToStringSerializer.instance);
		objectMapper.registerModule(simpleModule);
	}

	public static JacksonObjectMapperWrapper getInstance() {
		return objectMapper;
	}

}
