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
 * @todo IBaseBizStatusEnum2 jackSon序列化
 * @date 2018年7月1日 下午7:31:40
 *
 */
public class BaseBizStatusEnumSerializer2 extends StdSerializer<IBaseBizStatusEnum2>{
	
	private static final long serialVersionUID = -1918391420455571610L;
	
	public BaseBizStatusEnumSerializer2() {
		super(IBaseBizStatusEnum2.class);
	}

	public BaseBizStatusEnumSerializer2(Class<IBaseBizStatusEnum2> t) {
		super(t);
	}

	@Override
	public void serialize(IBaseBizStatusEnum2 value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		gen.writeStartObject();
        gen.writeFieldName(IBaseBizStatusEnum.CODE);
        gen.writeString(value.getCode());
        gen.writeFieldName(IBaseBizStatusEnum.DES);
        gen.writeString(value.getDes());
        gen.writeEndObject();
	}

}
