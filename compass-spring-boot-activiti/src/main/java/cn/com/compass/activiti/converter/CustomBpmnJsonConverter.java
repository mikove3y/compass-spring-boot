/**
 * 
 */
package cn.com.compass.activiti.converter;

import java.util.Map;

import org.activiti.bpmn.model.BaseElement;
import org.activiti.editor.language.json.converter.BaseBpmnJsonConverter;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 自定义bpmn转换器，包住自定义userTask转换器
 * @date 2018年8月31日 上午9:10:40
 * 
 */
public class CustomBpmnJsonConverter extends BpmnJsonConverter {
	
	//通过继承开放convertersToJsonMap的访问
    public static Map<Class<? extends BaseElement>, Class<? extends BaseBpmnJsonConverter>> getConvertersToJsonMap(){
        return convertersToJsonMap;
    }

    //通过继承开放convertersToJsonMap的访问
    public static Map<String, Class<? extends BaseBpmnJsonConverter>> getConvertersToBpmnMap(){
        return convertersToBpmnMap;
    }
	
}
