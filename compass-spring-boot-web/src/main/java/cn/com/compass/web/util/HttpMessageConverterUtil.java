package cn.com.compass.web.util;

import cn.com.compass.base.constant.BaseBizStatusEnumDeserializer;
import cn.com.compass.base.constant.BaseBizStatusEnumSerializer;
import cn.com.compass.base.constant.IBaseBizStatusEnum;
import cn.com.compass.util.JacksonObjectMapperWrapper;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo
 * @date 2019/4/12 22:26
 */
public class HttpMessageConverterUtil {

    /**
     * jackson消息转换器
     * @return
     */
    public static HttpMessageConverter<?> jackson2MessageConverter(){
        JacksonObjectMapperWrapper objectMapper = JacksonObjectMapperWrapper.getInstance();
        // 注册IBaseBizStatusEnum序列化和反序列化
        SimpleModule simpleModule = new SimpleModule();
        JsonDeserializer<IBaseBizStatusEnum> deserialize = new BaseBizStatusEnumDeserializer();
        simpleModule.addDeserializer(IBaseBizStatusEnum.class, deserialize);
        StdSerializer<IBaseBizStatusEnum> serialize = new BaseBizStatusEnumSerializer();
        simpleModule.addSerializer(IBaseBizStatusEnum.class, serialize);
        objectMapper.registerModule(simpleModule);
        return new MappingJackson2HttpMessageConverter(objectMapper);
    }
}
