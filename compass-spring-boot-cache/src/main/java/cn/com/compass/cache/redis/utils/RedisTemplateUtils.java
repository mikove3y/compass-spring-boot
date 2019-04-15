package cn.com.compass.cache.redis.utils;

import cn.com.compass.cache.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 获取默认的RedisTemplate
 * @date 2018年8月5日 上午12:23:34
 *
 */
public final class RedisTemplateUtils {

    private static RedisTemplate<String, Object> redisTemplate;

    public static RedisTemplate<String, Object> getRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        if (redisTemplate == null) {
            synchronized (RedisTemplateUtils.class) {
                if (redisTemplate == null) {
                    redisTemplate = new RedisTemplate<String, Object>();
                    redisTemplate.setConnectionFactory(redisConnectionFactory);

                    JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
                    redisTemplate.setValueSerializer(jdkSerializationRedisSerializer);
                    redisTemplate.setHashValueSerializer(jdkSerializationRedisSerializer);

                    // 设置键（key）的序列化采用StringRedisSerializer。
                    redisTemplate.setKeySerializer(new StringRedisSerializer());
                    redisTemplate.setHashKeySerializer(new StringRedisSerializer());
                    redisTemplate.afterPropertiesSet();
                }
            }

        }
        return redisTemplate;
    }
}  