package cn.com.compass.base.constant;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtilsBean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;

/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 枚举反序列化
 * @date 2018年8月7日 下午11:13:36
 *
 */
public class BaseBizStatusEnumDeserializer2 extends JsonDeserializer<IBaseBizStatusEnum2> {

	@Override
	public IBaseBizStatusEnum2 deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		JsonNode node = jp.getCodec().readTree(jp);
        String currentName = jp.getCurrentName();
        Object currentValue = jp.getCurrentValue();
        IBaseBizStatusEnum2 valueOf = null;
		try {
			Class findPropertyType = BeanUtilsBean.getInstance().getPropertyUtils().getPropertyType(currentValue, currentName);
			JsonFormat annotation = (JsonFormat) findPropertyType.getAnnotation(JsonFormat.class);
			if(node instanceof IntNode || node instanceof TextNode) {
				valueOf = IBaseBizStatusEnum2.fromCode(findPropertyType, node.asText());
			}else if(node instanceof ObjectNode) {
				if(node.has(IBaseBizStatusEnum.CODE)) {
					valueOf = IBaseBizStatusEnum2.fromCode(findPropertyType, node.get(IBaseBizStatusEnum.CODE).asText());
				}
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
