package cn.com.compass.data.entity;

import cn.com.compass.base.constant.BaseBizeStatusEnum.FileType;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 附件基础实体
 * @date 2018年7月19日 下午12:26:37
 *
 */
@Data
@MappedSuperclass
public class BaseAttachmentEntity<PK extends Serializable> extends BaseEntity<PK> {

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
	/**
	 * 文件类型
	 */
	@Type(type="cn.com.compass.data.convert.JpaDbEnumTypeHandler")
	private FileType fileType;
	/**
	 * 文件key
	 */
	private String fileKey;
	
	public static final String FILENAME = "fileName";
	public static final String ORIGINALFILENAME = "originalFileName";
	public static final String EXTENDNAME = "extendName";
	public static final String FILESIZE = "fileSize";
	public static final String FILEURL = "fileUrl";
	public static final String FILETYPE = "fileType";
	public static final String FILEKEY = "fileKey";
}
