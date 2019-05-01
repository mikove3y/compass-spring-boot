package cn.com.compass.base.util;

import cn.com.compass.base.constant.BaseBizStatusEnumDeserializer;
import cn.com.compass.base.constant.BaseBizStatusEnumSerializer;
import cn.com.compass.base.constant.IBaseBizStatusEnum;
import cn.com.compass.util.SerializerFeature;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

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
						SerializerFeature.WriteNullBooleanAsFalse
						)));
		// 设置空对象 null 转 ''
//		objectMapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
//			@Override
//			public void serialize(Object arg0, JsonGenerator arg1, SerializerProvider arg2)
//					throws IOException, JsonProcessingException {
//				try {
//					Object bean = arg1.getOutputContext().getCurrentValue();
//					String name = arg1.getOutputContext().getCurrentName();
//					Class<?> proType = BeanUtilsBean2.getInstance().getPropertyUtils().getPropertyType(bean, name);
//					// 判断proType是否存在public的空构造器
//					Constructor<?>[] constructors = proType.getConstructors();
//					if(ArrayUtils.isNotEmpty(constructors)) {
//						Constructor<?> nullConstructor = null;
//						for(Constructor<?> c : constructors) {
//							Class<?>[] paraTypes = c.getParameterTypes();
//							if(ArrayUtils.isEmpty(paraTypes)) {
//								c.setAccessible(true);
//								nullConstructor = c;
//								break;
//							}
//						}
//						if(nullConstructor!=null) {
//							Object proBean = nullConstructor.newInstance();
//							arg1.writeStartObject(proBean);
//							arg1.writeEndObject();
//						}else {
//							arg1.writeNull();
//						}
//					}else {
//						arg1.writeNull();
//					}
//				} catch (Exception e) {
//					arg1.writeNull();
//				}
//			}
//		});
		// 时间格式化
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		objectMapper.setDateFormat(dateFormat);// 格式化时间
		objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));//设置时区
		// 数字转 string
		SimpleModule numberSimpleModule = new SimpleModule();
		numberSimpleModule.addSerializer(Number.class, ToStringSerializer.instance);
		objectMapper.registerModule(numberSimpleModule);

		// 注册IBaseBizStatusEnum序列化和反序列化
		SimpleModule bizStatusEnumSimpleModule = new SimpleModule();
		JsonDeserializer<IBaseBizStatusEnum> deserialize = new BaseBizStatusEnumDeserializer();
		bizStatusEnumSimpleModule.addDeserializer(IBaseBizStatusEnum.class, deserialize);
		StdSerializer<IBaseBizStatusEnum> serialize = new BaseBizStatusEnumSerializer();
		bizStatusEnumSimpleModule.addSerializer(IBaseBizStatusEnum.class, serialize);
		objectMapper.registerModule(bizStatusEnumSimpleModule);
	}

	public static JacksonObjectMapperWrapper getInstance() {
		return objectMapper;
	}
	
}
