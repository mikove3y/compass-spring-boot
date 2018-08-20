package cn.com.compass.cache.redis.serializer;

import java.nio.charset.Charset;

import org.springframework.cache.support.NullValue;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import cn.com.compass.util.JacksonUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 
 * @date 2018年8月5日 上午12:23:06
 *
 */
@Slf4j
public class JaksonRedisSerializer<T> implements RedisSerializer<T> {

    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    public static final String EMPTY_OBJECT_FLAG = "EMPTY_OBJECT_FLAG_@$#";

    private Class<T> clazz;

    public JaksonRedisSerializer(Class<T> clazz) {
        super();
        this.clazz = clazz;
    }

    @Override
    public byte[] serialize(T t) throws SerializationException {
        if (t == null || t instanceof NullValue) {
            // 如果是NULL,则存空对象标示
            return EMPTY_OBJECT_FLAG.getBytes(DEFAULT_CHARSET);
        }
        try {
			return JacksonUtil.obj2json(t).getBytes(DEFAULT_CHARSET);
		} catch (Exception e) {
			log.error("serialization exception:{}",e);
			throw new SerializationException("serialization exception",e);
		}
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length <= 0) {
            return null;
        }
        String str = new String(bytes, DEFAULT_CHARSET);
        // 判断存储对象是否是NULL，是就返回null
        if (EMPTY_OBJECT_FLAG.equals(str)) {
            return null;
        }
        try {
			return (T) JacksonUtil.json2pojo(str, clazz);
		} catch (Exception e) {
			log.error("serialization exception:{}",e);
			throw new SerializationException("serialization exception",e);
		}
    }

}