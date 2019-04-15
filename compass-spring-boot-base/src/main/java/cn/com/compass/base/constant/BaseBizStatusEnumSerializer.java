package cn.com.compass.base.constant;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo IBaseBizStatusEnum jackSon序列化
 * @date 2018年7月1日 下午7:31:40
 *
 */
public class BaseBizStatusEnumSerializer extends StdSerializer<IBaseBizStatusEnum>{
	
	private static final long serialVersionUID = -1918391420455571610L;
	
	public BaseBizStatusEnumSerializer() {
		super(IBaseBizStatusEnum.class);
	}

	public BaseBizStatusEnumSerializer(Class<IBaseBizStatusEnum> t) {
		super(t);
	}

	@Override
	public void serialize(IBaseBizStatusEnum value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		gen.writeStartObject();
        gen.writeFieldName(IBaseBizStatusEnum.CODE);
        gen.writeNumber(value.getCode());
        gen.writeFieldName(IBaseBizStatusEnum.DES);
        gen.writeString(value.getDes());
        gen.writeEndObject();
	}

}
