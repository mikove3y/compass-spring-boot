package cn.com.compass.activiti.org.activiti.rest.editor.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModelSaveRestRequestVo implements Serializable{

	private static final long serialVersionUID = -2464356212935970431L;
	
	private String name;
	
	private String description;
	
	private String json_xml;
	
	private String svg_xml;

}
