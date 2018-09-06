package cn.com.compass.cache.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import cn.com.compass.cache.enums.ChannelTopicEnum;
import cn.com.compass.cache.listener.RedisMessageListener;
import cn.com.compass.cache.redis.serializer.KryoRedisSerializer;
import cn.com.compass.cache.redis.serializer.StringRedisSerializer;

/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo redis 配置
 * @date 2018年8月18日 下午11:21:34
 *
 */
@Configuration
public class RedisConfig {

    /**
     * 重写Redis序列化方式，使用Json方式:
     * 当我们的数据存储到Redis的时候，我们的键（key）和值（value）都是通过Spring提供的Serializer序列化到数据库的。RedisTemplate默认使用的是JdkSerializationRedisSerializer，StringRedisTemplate默认使用的是StringRedisSerializer。
     * Spring Data JPA为我们提供了下面的Serializer：
     * GenericToStringSerializer、Jackson2JsonRedisSerializer、JacksonJsonRedisSerializer、JdkSerializationRedisSerializer、OxmSerializer、StringRedisSerializer。
     * 在此我们将自己配置RedisTemplate并定义Serializer。
     *
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // value
        KryoRedisSerializer<Object> kryoRedisSerializer = new KryoRedisSerializer<>(Object.class);
        // key
        StringRedisSerializer stringRs =  new StringRedisSerializer();
        // 设置值（value）的序列化采用KryoRedisSerializer。 kryo 性能是源生java序列化的性能的十几倍
        redisTemplate.setValueSerializer(kryoRedisSerializer);
        redisTemplate.setHashValueSerializer(kryoRedisSerializer);
        // 设置键（key）的序列化采用StringRedisSerializer。
        redisTemplate.setKeySerializer(stringRs);
        redisTemplate.setHashKeySerializer(stringRs);
        return redisTemplate;
    }
    
    /**
     * 配置redis消息监听器
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public MessageListenerAdapter MessageListenerAdapter() {
    	return new RedisMessageListener();
    }
    
    /**
     * 配置redis消息监听器
     * @param redisConnectionFactory
     * @param messageListener
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public RedisMessageListenerContainer redisContainer(RedisConnectionFactory redisConnectionFactory, MessageListenerAdapter messageListener) {
        final RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        container.addMessageListener(messageListener, ChannelTopicEnum.REDIS_CACHE_DELETE_TOPIC.getChannelTopic());
        container.addMessageListener(messageListener, ChannelTopicEnum.REDIS_CACHE_CLEAR_TOPIC.getChannelTopic());
        return container;
    }
    
    /**
     * 显示声明缓存key生成器
     *
     * @return
     */
    @Bean
    public KeyGenerator keyGenerator() {
        return new SimpleKeyGenerator();
    }

}
