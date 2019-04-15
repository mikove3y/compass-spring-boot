package cn.com.compass.base.constant;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Objects;

/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 业务枚举接口 code:Integer类型
 * @date 2018年6月6日 下午2:51:29
 *
 */
// enum json序列化为对象
//@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonSerialize(using=BaseBizStatusEnumSerializer.class)//BaseBizStatusEnumSerializer需要实例化
@JsonDeserialize(using=BaseBizStatusEnumDeserializer.class)//BaseBizStatusEnumDeserializer需要实例化
public interface IBaseBizStatusEnum {
	/**
	 * code
	 * @return
	 */
	public String getCode();
	/**
	 * 描述
	 * @return
	 */
	public String getDes();
	
	public static final String CODE = "code";
	
	public static final String DES = "des";
	
	/**
	 * 获取枚举类的所有枚举值
	 * @param enumType
	 * @return
	 */
	public static <T extends IBaseBizStatusEnum> T[] enums(Class<T> enumType) {
		return enumType.getEnumConstants();
	}
	/**
	 * 按枚举的code获取枚举实例
	 * @param enumType
	 * @param code
	 * @return
	 */
	public static <T extends IBaseBizStatusEnum> T fromCode(Class<T> enumType, String code) {
        for (T object : enums(enumType)) {
            if (Objects.equals(code, object.getCode())) {
                return object;
            }
        }
        return null;
    }
	
	/**
	 * 按枚举的desc获取枚举实例
	 * @param enumType
	 * @param des
	 * @return
	 */
	public static <T extends IBaseBizStatusEnum> T fromDes(Class<T> enumType, String des) {
        for (T object : enums(enumType)) {
            if (Objects.equals(des, object.getDes())) {
                return object;
            }
        }
        return null;
    }

}
