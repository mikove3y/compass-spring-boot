package cn.com.compass.base.constant;

import java.util.Objects;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 业务枚举接口2 code:String类型
 * @date 2018年6月6日 下午2:51:29
 *
 */
// enum json序列化为对象
//@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonSerialize(using=BaseBizStatusEnumSerializer2.class)//BaseBizStatusEnumSerializer2需要实例化
@JsonDeserialize(using=BaseBizStatusEnumDeserializer2.class)//BaseBizStatusEnumDeserializer2需要实例化
public interface IBaseBizStatusEnum2 {
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

	/**
	 * 是否消极
	 * @return
	 */
	public Boolean isNegative();
	
	public static final String CODE = "code";
	
	public static final String DES = "des";

	public static final String NEGATIVE = "negative";
	
	/**
	 * 获取枚举类的所有枚举值
	 * @param enumType
	 * @return
	 */
	public static <T extends IBaseBizStatusEnum2> T[] enums(Class<T> enumType) {
		return enumType.getEnumConstants();
	}
	/**
	 * 按枚举的code获取枚举实例
	 * @param enumType
	 * @param code
	 * @return
	 */
	public static <T extends IBaseBizStatusEnum2> T fromCode(Class<T> enumType, String code) {
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
	 * @param desc
	 * @return
	 */
	public static <T extends IBaseBizStatusEnum2> T fromDes(Class<T> enumType, String des) {
        for (T object : enums(enumType)) {
            if (Objects.equals(des, object.getDes())) {
                return object;
            }
        }
        return null;
    }

}
