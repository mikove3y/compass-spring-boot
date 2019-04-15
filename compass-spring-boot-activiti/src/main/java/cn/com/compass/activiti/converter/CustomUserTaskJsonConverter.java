/**
 * 
 */
package cn.com.compass.activiti.converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.CustomProperty;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.UserTask;
import org.activiti.editor.language.json.converter.UserTaskJsonConverter;

import java.util.Map;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 自定义usertask属性json转换器
 * @date 2018年8月31日 上午9:02:09
 * 
 */
public class CustomUserTaskJsonConverter extends UserTaskJsonConverter {
	/**
	 * 与stencilset.json自定义的属性对应
	 */
	public static final String[] properties = {"action_code"};
	
	@Override
    protected FlowElement convertJsonToElement(JsonNode elementNode, JsonNode modelNode, Map<String, JsonNode> shapeMap) {
        FlowElement flowElement = super.convertJsonToElement(elementNode, modelNode, shapeMap);
        UserTask userTask = (UserTask) flowElement;
        for(String p : properties) {
        	//将自己的属性添加到activiti自带的自定义属性中
        	CustomProperty customProperty = new CustomProperty();
        	customProperty.setName(p);
        	customProperty.setSimpleValue(this.getPropertyValueAsString(p, elementNode));
        	userTask.getCustomProperties().add(customProperty);
        }
        return userTask;
    }

    @Override
    protected void convertElementToJson(ObjectNode propertiesNode, BaseElement baseElement) {
        super.convertElementToJson(propertiesNode, baseElement);
    }
	
}
