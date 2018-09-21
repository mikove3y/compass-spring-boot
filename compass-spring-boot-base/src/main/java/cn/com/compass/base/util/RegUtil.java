/**
 * 
 */
package cn.com.compass.base.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.com.compass.base.constant.BaseBizeStatusEnum.FileType;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 
 * @date 2018年9月20日 下午8:24:29
 * 
 */
public class RegUtil {
	
	/**
	 * 图片
	 */
	public static final String IMG_REG= "(?i).+?\\.(jpg|jpeg|gif|png|bmp)$";
	/**
	 * 文件
	 */
	public static final String FILE_REG= "(?i).+?\\.(doc|docx|rtf|xls|xlsx|xlsm|xlt|xltx|xltm|cvs)$";
	/**
	 * 视频
	 */
	public static final String VIDEO_REG= "(?i).+?\\.(avi|wmv|mpeg|mp4|mov|mkv|flv|f4v|m4v|rmvb|rm|3gp|dat|ts|mts|vob)$";
	/**
	 * 音频
	 */
	public static final String AUDIO_REG= "(?i).+?\\.(wmv|mp3|wma|mp3pro|mod|ra|md|asf|aac|vqf|mid|ogg|m4a|aac+|aiff|au|cd|wav|flac|ape)$";
	
	// 手机号
	
	// 邮箱
	
	// 日期
	
	/**
	 * 判断文件类型
	 * @param fileName
	 * @return
	 */
	public static FileType fileType(String fileName) {
		Pattern regex = Pattern.compile(IMG_REG);
		Matcher matcher = regex.matcher( fileName );
		boolean isMatch = matcher.matches();
		if(!isMatch) {
			regex = Pattern.compile(FILE_REG);
			matcher = regex.matcher( fileName );
			isMatch = matcher.matches();
		}else {
			return FileType.PIC;
		}
		if(!isMatch) {
			regex = Pattern.compile(VIDEO_REG);
			matcher = regex.matcher( fileName );
			isMatch = matcher.matches();
		}else {
			return FileType.FILE;
		}
		if(!isMatch) {
			regex = Pattern.compile(AUDIO_REG);
			matcher = regex.matcher( fileName );
			isMatch = matcher.matches();
		}else {
			return FileType.VIDEO;
		}
		if(isMatch) {
			return FileType.AUDIO;
		}else {
			return FileType.OTHER;
		}
	}
	
}
