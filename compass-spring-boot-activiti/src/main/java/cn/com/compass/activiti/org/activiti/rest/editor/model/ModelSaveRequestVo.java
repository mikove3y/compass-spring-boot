package cn.com.compass.activiti.org.activiti.rest.editor.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 模型-流程保存请求vo
 * @date 2018年8月29日 下午3:11:23
 *
 */
@Getter
@Setter
public class ModelSaveRequestVo implements Serializable{

	private static final long serialVersionUID = 341918384831318497L;
	/**
	 * 模型名称
	 */
	@NotNull(message="模型名称不能为空")
	private String name;
	/**
	 * 模型描述
	 */
	private String description;
	/**
	 * 流程明细
	 */
	@NotNull(message="流程明细不能为空")
	private String jsonXml;
	/**
	 * 流程矢量图
	 */
	@NotNull(message="流程矢量图不能为空")
	private String svgXml;

}
