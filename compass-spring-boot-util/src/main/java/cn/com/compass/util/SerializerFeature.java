/**
 * 
 */
package cn.com.compass.util;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 自定义序列化类型
 * @date 2018年9月19日 下午9:41:34
 * 
 */
public enum SerializerFeature {
	WriteNullListAsEmpty, // 空数组转[]
	WriteNullStringAsEmpty, // 空string 转""
	WriteNullNumberAsZero, // 空number 转0
	WriteNullBooleanAsFalse, // 空boolean 转false
	WriteNullObjectAsEmpty;// 空对象转{}
	public final int mask;

	SerializerFeature() {
		mask = (1 << ordinal());
	}
}
