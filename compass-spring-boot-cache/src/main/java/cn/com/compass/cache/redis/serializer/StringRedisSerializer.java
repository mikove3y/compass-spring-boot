package cn.com.compass.cache.redis.serializer;

import cn.com.compass.util.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.Assert;

import java.nio.charset.Charset;

/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 必须重写序列化器，否则@Cacheable注解的key会报类型转换错误
 * @date 2018年8月5日 上午12:23:21
 *
 */
@Slf4j
public class StringRedisSerializer implements RedisSerializer<Object> {

    private final Charset charset;

    private final String target = "\"";

    private final String replacement = "";

    public StringRedisSerializer() {
        this(Charset.forName("UTF8"));
    }

    public StringRedisSerializer(Charset charset) {
        Assert.notNull(charset, "Charset must not be null!");
        this.charset = charset;
    }

    @Override
    public String deserialize(byte[] bytes) {
        return (bytes == null ? null : new String(bytes, charset));
    }

    @Override
    public byte[] serialize(Object object) {
		try {
			String string = JacksonUtil.obj2json(object);
			if (string == null) {
				return null;
			}
			string = string.replace(target, replacement);
			return string.getBytes(charset);
		} catch (Exception e) {
			log.error("serialization exception:{}",e);
		}
		return null;
    }
}
