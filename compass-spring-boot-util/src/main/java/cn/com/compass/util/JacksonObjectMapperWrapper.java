package cn.com.compass.util;

import java.io.IOException;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
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
		// null 转 ''
		objectMapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
			@Override
			public void serialize(Object arg0, JsonGenerator arg1, SerializerProvider arg2)
					throws IOException, JsonProcessingException {
				arg1.writeString("");
			}
		});
		// 时间格式化
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		objectMapper.setDateFormat(dateFormat);
		// 数字转 string
		SimpleModule simpleModule = new SimpleModule();
		simpleModule.addSerializer(Number.class, ToStringSerializer.instance);
		objectMapper.registerModule(simpleModule);
	}

	public static JacksonObjectMapperWrapper getInstance() {
		return objectMapper;
	}

}
