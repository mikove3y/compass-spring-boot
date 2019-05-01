package cn.com.compass.base.constant;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;

import java.io.IOException;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 枚举反序列化
 * @date 2018年8月7日 下午11:13:36
 */
public class BaseBizStatusEnumDeserializer extends JsonDeserializer<IBaseBizStatusEnum> implements ContextualDeserializer {

    private Class<? extends IBaseBizStatusEnum> targetClass;

    public BaseBizStatusEnumDeserializer() {

    }

    public BaseBizStatusEnumDeserializer(Class<? extends IBaseBizStatusEnum> targetClass) {
        this.targetClass = targetClass;
    }


    @Override
    public IBaseBizStatusEnum deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        IBaseBizStatusEnum valueOf = null;
        try {
            if (node instanceof TextNode) {
                // 文本节点
                valueOf = IBaseBizStatusEnum.fromCode(targetClass, node.asText());
                if (valueOf == null)
                    valueOf = IBaseBizStatusEnum.fromDes(targetClass, node.asText());
            } else if (node instanceof ObjectNode) {
                // 对象节点
                if (node.has(IBaseBizStatusEnum.CODE)) {
                    valueOf = IBaseBizStatusEnum.fromCode(targetClass, node.get(IBaseBizStatusEnum.CODE).asText());
                }else if(node.has(IBaseBizStatusEnum.DES)){
                    valueOf = IBaseBizStatusEnum.fromDes(targetClass, node.get(IBaseBizStatusEnum.DES).asText());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return valueOf;
    }

    /**
     *
     * @param ctxt
     * @param property
     * @return
     * @throws JsonMappingException
     */
    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext ctxt,
                                                BeanProperty property) throws JsonMappingException {
        return new BaseBizStatusEnumDeserializer((Class<? extends IBaseBizStatusEnum>) ctxt.getContextualType().getRawClass());
    }
}
