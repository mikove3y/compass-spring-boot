package cn.com.compass.base.constant;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 业务枚举接口
 * @date 2018年6月6日 下午2:51:29
 *
 */
// enum json序列化为对象
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public interface IBaseBizStatusEnum {
	/**
	 * code
	 * @return
	 */
	public Integer getCode();
	/**
	 * 描述
	 * @return
	 */
	public String getDes();
	
	public static final String CODE = "code";
	
	public static final String DES = "des";
	
	/**
	 * 按枚举的code获取枚举实例
	 * @param enumType
	 * @param code
	 * @return
	 */
	public static <T extends IBaseBizStatusEnum> T fromCode(Class<T> enumType, Integer code) {
        for (T object : enumType.getEnumConstants()) {
            if (Objects.equals(code, object.getCode())) {
                return object;
            }
        }
        throw new IllegalArgumentException("No enum code " + code + " of " + enumType.getCanonicalName());
    }
	
	/**
	 * 按枚举的desc获取枚举实例
	 * @param enumType
	 * @param desc
	 * @return
	 */
	public static <T extends IBaseBizStatusEnum> T fromDesc(Class<T> enumType, String des) {
        for (T object : enumType.getEnumConstants()) {
            if (Objects.equals(des, object.getDes())) {
                return object;
            }
        }
        throw new IllegalArgumentException("No enum desc " + des + " of " + enumType.getCanonicalName());
    }

}
