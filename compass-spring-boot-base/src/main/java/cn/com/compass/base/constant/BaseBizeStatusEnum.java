package cn.com.compass.base.constant;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 基类业务枚举
 * @date 2018年6月6日 下午8:53:15
 *
 */
public class BaseBizeStatusEnum {
	
	/**
	 * 是否
	 */
	public enum YesOrNo implements IBaseBizStatusEnum {

		YES("1", "是"), NO("0", "否");

		private final String code;

		private final String des;

//		@JsonCreator
		private YesOrNo(String code, String des) {
			this.code = code;
			this.des = des;
		}

		@Override
//		@JsonValue
		public String getCode() {
			return this.code;
		}

		@Override
		public String getDes() {
			return this.des;
		}
	}

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

	/**
	 * 文件类型（ 1-图片，2-音频，3-视频，4-文件，5-其他）
	 */
	public enum FileType implements IBaseBizStatusEnum {

		PIC("1", "图片"), AUDIO("2", "音频"), VIDEO("3","视频"), FILE("4","文件"), OTHER("5","其他");

		private final String code;

		private final String des;

		private FileType(String code, String des) {
			this.code = code;
			this.des = des;
		}

		@Override
		public String getCode() {
			return this.code;
		}

		@Override
		public String getDes() {
			return this.des;
		}
	}

	/**
	 * 客户端类型枚举
	 */
	public enum ClientType implements IBaseBizStatusEnum {
		APP("1", "APP"), PC("2", "PC"), ALL("3", "ALL");
		private final String code;

		private final String des;

		private ClientType(String code, String des) {
			this.code = code;
			this.des = des;
		}

		@Override
		public String getCode() {
			return code;
		}

		@Override
		public String getDes() {
			return des;
		}

	}
	

}
