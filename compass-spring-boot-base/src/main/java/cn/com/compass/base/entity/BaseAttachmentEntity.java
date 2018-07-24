package cn.com.compass.base.entity;

import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;
/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 附件基础实体
 * @date 2018年7月19日 下午12:26:37
 *
 */
@Getter
@Setter
@MappedSuperclass
public class BaseAttachmentEntity extends BaseEntity{

	private static final long serialVersionUID = -5445434931452155963L;
	
	/**
     * 文件名
     */
	private String fileName;
    /**
     * 原文件名
     */
	private String originalFileName;
    /**
     * 拓展名
     */
	private String extendName;
    /**
     * 文件大小
     */
	private Long fileSize;
    /**
     * 文件路径
     */
	private String fileUrl;
	
	public static final String FILENAME = "fileName";
	public static final String ORIGINALFILENAME = "originalFileName";
	public static final String EXTENDNAME = "extendName";
	public static final String FILESIZE = "fileSize";
	public static final String FILEURL = "fileUrl";

}
