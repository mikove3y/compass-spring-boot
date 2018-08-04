package cn.com.compass.base.constant;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo IBaseBizStatusEnum jackSon序列化
 * @date 2018年7月1日 下午7:31:40
 *
 */
public class BaseBizStatusEnumSerializer<T extends IBaseBizStatusEnum> extends StdSerializer<T>{

	private static final long serialVersionUID = -1918391420455571610L;

	public BaseBizStatusEnumSerializer(Class<T> t) {
		super(t);
	}
	
	@Override
	public void serialize(T value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		gen.writeStartObject();
        gen.writeFieldName("code");
        gen.writeString(value.getCode().toString());
        gen.writeFieldName("des");
        gen.writeNumber(value.getDes());
        gen.writeEndObject();
	}

}
