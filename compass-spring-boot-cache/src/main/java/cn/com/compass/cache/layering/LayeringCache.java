package cn.com.compass.cache.layering;

import cn.com.compass.cache.enums.ChannelTopicEnum;
import cn.com.compass.cache.listener.RedisPublisher;
import cn.com.compass.cache.redis.cache.CustomizedRedisCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.AbstractValueAdaptingCache;
import org.springframework.cache.support.NullValue;
import org.springframework.data.redis.core.RedisOperations;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 
 * @date 2018年8月4日 下午11:13:22
 *
 */
@Slf4j
public class LayeringCache extends AbstractValueAdaptingCache {

	/**
     * 缓存的名称
     */
    private final String name;

    /**
     * 是否使用一级缓存
     */
    private boolean usedFirstCache = true;

    /**
     * redi缓存
     */
    private final CustomizedRedisCache redisCache;

    /**
     * Caffeine缓存
     */
    private final CaffeineCache caffeineCache;

    RedisOperations<? extends Object, ? extends Object> redisOperations;

    /**
     * @param name              缓存名称
     * @param prefix            缓存前缀
     * @param redisOperations   操作Redis的RedisTemplate
     * @param expiration        redis缓存过期时间
     * @param preloadSecondTime redis缓存自动刷新时间
     * @param allowNullValues   是否允许存NULL，默认是false
     * @param usedFirstCache    是否使用一级缓存，默认是true
     * @param forceRefresh      是否强制刷新（走数据库），默认是false
     * @param caffeineCache     Caffeine缓存
     */
    public LayeringCache(String name, byte[] prefix, RedisOperations<? extends Object, ? extends Object> redisOperations,
                         long expiration, long preloadSecondTime, boolean allowNullValues, boolean usedFirstCache,
                         boolean forceRefresh, com.github.benmanes.caffeine.cache.Cache<Object, Object> caffeineCache) {

        super(allowNullValues);
        this.name = name;
        this.usedFirstCache = usedFirstCache;
        this.redisOperations = redisOperations;
        this.redisCache = new CustomizedRedisCache(name, prefix, redisOperations, expiration, preloadSecondTime, forceRefresh, allowNullValues);
        this.caffeineCache = new CaffeineCache(name, caffeineCache, allowNullValues);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Object getNativeCache() {
        return this;
    }

    public CustomizedRedisCache getSecondaryCache() {
        return this.redisCache;
    }

    public CaffeineCache getFirstCache() {
        return this.caffeineCache;
    }

    @Override
    public ValueWrapper get(Object key) {
        ValueWrapper wrapper = null;
        if (usedFirstCache) {
            // 查询一级缓存
            wrapper = caffeineCache.get(key);
            if(log.isDebugEnabled()) {
            	log.debug("查询一级缓存{}, key:{},返回值是:{}", this.getName(), key, wrapper);
            }
            if(log.isInfoEnabled()) {
            	log.info("查询一级缓存{}, key:{},返回值是:{}", this.getName(), key, wrapper);
            }
        }

        if (wrapper == null) {
            // 查询二级缓存
            wrapper = redisCache.get(key);
            caffeineCache.put(key, wrapper == null ? null : wrapper.get());
            if(log.isDebugEnabled()) {
            	log.debug("查询二级缓存{},并将数据放到一级缓存{}。 key:{},返回值是:{}", this.getName(), this.getName(), key, wrapper);
            }
            if(log.isInfoEnabled()) {
            	log.info("查询二级缓存{},并将数据放到一级缓存{}。 key:{},返回值是:{}", this.getName(), this.getName(), key, wrapper);
            }
        }
        return wrapper;
    }

    @Override
    public <T> T get(Object key, Class<T> type) {
        T value = null;
        if (usedFirstCache) {
            // 查询一级缓存
            value = caffeineCache.get(key, type);
            if(log.isDebugEnabled()) {
            	log.debug("查询一级缓存{}, key:{},返回值是:{}", this.getName(), key, value);
            }
            if(log.isInfoEnabled()) {
            	log.info("查询一级缓存{}, key:{},返回值是:{}", this.getName(), key, value);
            }
        }

        if (value == null) {
            // 查询二级缓存
            value = redisCache.get(key, type);
            caffeineCache.put(key, value);
            if(log.isDebugEnabled()) {
            	log.debug("查询二级缓存{},并将数据放到一级缓存{}。 key:{},返回值是:{}", this.getName(), this.getName(), key, value);
            }
            if(log.isInfoEnabled()) {
            	log.info("查询二级缓存{},并将数据放到一级缓存{}。 key:{},返回值是:{}", this.getName(), this.getName(), key, value);
            }
        }
        return value;
    }

    @SuppressWarnings("unchecked")
	@Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        T value = null;
        if (usedFirstCache) {
            // 查询一级缓存,如果一级缓存没有值则调用getForSecondaryCache(k, valueLoader)查询二级缓存
            value = (T) caffeineCache.getNativeCache().get(key, k -> getForSecondaryCache(k, valueLoader));
        } else {
            // 直接查询二级缓存
            value = (T) getForSecondaryCache(key, valueLoader);
        }

        if (value instanceof NullValue) {
            return null;
        }
        return value;
    }

    @Override
    public void put(Object key, Object value) {
        if (usedFirstCache) {
            caffeineCache.put(key, value);
        }
        redisCache.put(key, value);
    }

    @Override
    public ValueWrapper putIfAbsent(Object key, Object value) {
        if (usedFirstCache) {
            caffeineCache.putIfAbsent(key, value);
        }
        return redisCache.putIfAbsent(key, value);
    }

    @Override
    public void evict(Object key) {
        // 删除的时候要先删除二级缓存再删除一级缓存，否则有并发问题
        redisCache.evict(key);
        if (usedFirstCache) {
            // 删除一级缓存需要用到redis的Pub/Sub（订阅/发布）模式，否则集群中其他服服务器节点的一级缓存数据无法删除
            Map<String, Object> message = new HashMap<>();
            message.put("cacheName", name);
            message.put("key", key);
            // 创建redis发布者
            RedisPublisher redisPublisher = new RedisPublisher(redisOperations, ChannelTopicEnum.REDIS_CACHE_DELETE_TOPIC.getChannelTopic());
            // 发布消息
            redisPublisher.publisher(message);
        }
    }

    @Override
    public void clear() {
        redisCache.clear();
        if (usedFirstCache) {
            // 清除一级缓存需要用到redis的订阅/发布模式，否则集群中其他服服务器节点的一级缓存数据无法删除
            Map<String, Object> message = new HashMap<>();
            message.put("cacheName", name);
            // 创建redis发布者
            RedisPublisher redisPublisher = new RedisPublisher(redisOperations, ChannelTopicEnum.REDIS_CACHE_CLEAR_TOPIC.getChannelTopic());
            // 发布消息
            redisPublisher.publisher(message);
        }
    }

    @Override
    protected Object lookup(Object key) {
        Object value = null;
        if (usedFirstCache) {
            value = caffeineCache.get(key);
            if(log.isDebugEnabled()) {
            	log.debug("查询一级缓存{}, key:{},返回值是:{}", this.getName(), key, value);
            }
            if(log.isInfoEnabled()) {
            	log.info("查询一级缓存{}, key:{},返回值是:{}", this.getName(), key, value);
            }
        }
        if (value == null) {
            value = redisCache.get(key);
            if(log.isDebugEnabled()) {
            	log.debug("查询二级缓存{}, key:{},返回值是:{}", this.getName(), this.getName(), key, value);
            }
            if(log.isInfoEnabled()) {
            	log.info("查询二级缓存{}, key:{},返回值是:{}", this.getName(), this.getName(), key, value);
            }
        }
        return value;
    }

    /**
     * 查询二级缓存
     *
     * @param key
     * @param valueLoader
     * @return
     */
    private <T> Object getForSecondaryCache(Object key, Callable<T> valueLoader) {
        T value = redisCache.get(key, valueLoader);
        if(log.isDebugEnabled()) {
        	log.debug("查询二级缓存{}, key:{},返回值是:{}", this.getName(), this.getName(), key, value);
        }
        if(log.isInfoEnabled()) {
        	log.info("查询二级缓存{}, key:{},返回值是:{}", this.getName(), this.getName(), key, value);
        }
        return toStoreValue(value);
    }

}
