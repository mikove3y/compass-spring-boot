package cn.com.compass.base.constant;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtilsBean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 枚举反序列化
 * @date 2018年8月7日 下午11:13:36
 *
 */
public class BaseBizStatusEnumDeserializer extends JsonDeserializer<IBaseBizStatusEnum> {

	@Override
	public IBaseBizStatusEnum deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		JsonNode node = jp.getCodec().readTree(jp);
        String currentName = jp.getCurrentName();
        Object currentValue = jp.getCurrentValue();
        IBaseBizStatusEnum valueOf = null;
		try {
			Class findPropertyType = BeanUtilsBean.getInstance().getPropertyUtils().getPropertyType(currentValue, currentName);
			JsonFormat annotation = (JsonFormat) findPropertyType.getAnnotation(JsonFormat.class);
			if(annotation == null || annotation.shape() != Shape.OBJECT) {
				valueOf = IBaseBizStatusEnum.fromCode(findPropertyType, node.asInt());
			}else {
				valueOf = IBaseBizStatusEnum.fromCode(findPropertyType, node.get(IBaseBizStatusEnum.CODE).asInt());
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
        return valueOf;
	}


}
