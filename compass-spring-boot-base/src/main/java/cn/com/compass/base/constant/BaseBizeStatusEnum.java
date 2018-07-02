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
public enum BaseBizeStatusEnum implements IBaseBizStatusEnum {
	
	YES(1, "是"), NO(0, "否");
	

	private final Integer code;

	private final String des;
	
	@JsonCreator
	private BaseBizeStatusEnum(Integer code, String des) {
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
