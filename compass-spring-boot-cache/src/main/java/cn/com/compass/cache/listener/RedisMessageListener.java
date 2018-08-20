package cn.com.compass.cache.listener;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import cn.com.compass.base.constant.BaseConstant;
import cn.com.compass.base.exception.BaseException;
import cn.com.compass.cache.enums.ChannelTopicEnum;
import cn.com.compass.cache.layering.LayeringCache;
import cn.com.compass.util.JacksonUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo redis 消息监听
 * @date 2018年8月4日 下午11:19:23
 *
 */
@Slf4j
public class RedisMessageListener extends MessageListenerAdapter {
	
	@Autowired
	private CacheManager cacheManager;

	@Override
	public void onMessage(Message message, byte[] pattern) {
		try {
			super.onMessage(message, pattern);
			ChannelTopicEnum channelTopic = ChannelTopicEnum.getChannelTopicEnum(new String(message.getChannel()));
			log.info("redis消息订阅者接收到频道【{}】发布的消息。消息内容：{}", channelTopic.getChannelTopicStr(), message.toString());
			// 解析订阅发布的信息，获取缓存的名称和缓存的key
			String ms = new String(message.getBody());
			Map<String, Object> map = JacksonUtil.json2map(ms);
			String cacheName = (String) map.get("cacheName");
			Object key = map.get("key");

			// 根据缓存名称获取多级缓存
			Cache cache = cacheManager.getCache(cacheName);

			// 判断缓存是否是多级缓存
			if (cache != null && cache instanceof LayeringCache) {
				switch (channelTopic) {
				case REDIS_CACHE_DELETE_TOPIC:
					// 获取一级缓存，并删除一级缓存数据
					((LayeringCache) cache).getFirstCache().evict(key);
					log.info("删除一级缓存{}数据,key:{}", cacheName, key.toString());
					break;

				case REDIS_CACHE_CLEAR_TOPIC:
					// 获取一级缓存，并清空一级缓存数据
					((LayeringCache) cache).getFirstCache().clear();
					log.info("清空一级缓存{}数据,key:{}", cacheName, key.toString());
					break;

				default:
					log.info("接收到没有定义的订阅消息频道数据");
					break;
				}

			}
		} catch (Exception e) {
			log.error("redis 缓存监听器出错,ex:{}", e);
			throw new BaseException(BaseConstant.INNER_ERRO, e);
		}
	}
}
