package cn.com.compass.cache.listener;

import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.listener.ChannelTopic;

import lombok.extern.slf4j.Slf4j;
/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo redis 消息通知
 * @date 2018年8月4日 下午11:24:50
 *
 */
@Slf4j
public class RedisPublisher {
	
	RedisOperations<? extends Object, ? extends Object> redisOperations;

    /**
     * 频道名称
     */
    ChannelTopic channelTopic;

    /**
     * @param redisOperations Redis客户端
     * @param channelTopic    频道名称
     */
    public RedisPublisher(RedisOperations<? extends Object, ? extends Object> redisOperations, ChannelTopic channelTopic) {
        this.channelTopic = channelTopic;
        this.redisOperations = redisOperations;
    }

    /**
     * 发布消息到频道（Channel）
     *
     * @param message 消息内容
     */
    public void publisher(Object message) {
        redisOperations.convertAndSend(channelTopic.toString(), message);
        log.info("redis消息发布者向频道【{}】发布了【{}】消息", channelTopic.toString(), message.toString());
    }
}
