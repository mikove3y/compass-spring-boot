package cn.com.compass.base.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

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

		YES(1, "是"), NO(0, "否");

		private final Integer code;

		private final String des;

		@JsonCreator
		private YesOrNo(Integer code, String des) {
			this.code = code;
			this.des = des;
		}

		@Override
		@JsonValue
		public Integer getCode() {
			return this.code;
		}

		@Override
		public String getDes() {
			return this.des;
		}
	}
	
	/**
	 * 文件类型（ 0-图片，1-音频，2-视频，3-其他）
	 */
	public enum FileType implements IBaseBizStatusEnum {

		PIC(1, "图片"), AUDIO(2, "音频"), VIDEO(3,"视频"), OTHER(4,"其他");

		private final Integer code;

		private final String des;

		private FileType(Integer code, String des) {
			this.code = code;
			this.des = des;
		}

		@Override
		public Integer getCode() {
			return this.code;
		}

		@Override
		public String getDes() {
			return this.des;
		}
	}
	

}
