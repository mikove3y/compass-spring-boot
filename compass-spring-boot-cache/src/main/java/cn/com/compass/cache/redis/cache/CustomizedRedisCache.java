package cn.com.compass.cache.redis.cache;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheElement;
import org.springframework.data.redis.cache.RedisCacheKey;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;

import cn.com.compass.cache.redis.lock.RedisLock;
import cn.com.compass.cache.redis.utils.ThreadTaskUtils;
import cn.com.compass.web.context.AppContext;


/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 自定义的redis缓存
 * @date 2018年8月5日 上午12:22:04
 *
 */
public class CustomizedRedisCache extends RedisCache {

    private static final Logger logger = LoggerFactory.getLogger(CustomizedRedisCache.class);

    public static final String INVOCATION_CACHE_KEY_SUFFIX = ":invocation_suffix";

    /**
     * 刷新缓存重试次数
     */
    private static final int RETRY_COUNT = 5;

    private CacheSupport getCacheSupport() {
        return AppContext.getInstance().getBean(CacheSupport.class);
    }

    ThreadAwaitContainer container = new ThreadAwaitContainer();

    @SuppressWarnings("rawtypes")
    private final RedisOperations redisOperations;

    private final byte[] prefix;

    /**
     * 缓存主动在失效前强制刷新缓存的时间
     * 单位：秒
     */
    private long preloadSecondTime = 0;

    /**
     * 缓存有效时间
     */
    private long expirationSecondTime;

    /**
     * 是否强制刷新（走数据库），默认是false
     */
    private boolean forceRefresh = false;

    public CustomizedRedisCache(String name, byte[] prefix, RedisOperations<? extends Object, ? extends Object> redisOperations, long expiration, long preloadSecondTime, boolean forceRefresh, boolean allowNullValues) {
        super(name, prefix, redisOperations, expiration, allowNullValues);
        this.redisOperations = redisOperations;
        // 指定有效时间
        this.expirationSecondTime = expiration;
        // 指定自动刷新时间
        this.preloadSecondTime = preloadSecondTime;
        // 是强制刷新
        this.forceRefresh = forceRefresh;
        this.prefix = prefix;

    }

    @SuppressWarnings("unchecked")
    @Override
    public void evict(Object key) {
        super.evict(key);
        redisOperations.delete(getCacheKey(key) + INVOCATION_CACHE_KEY_SUFFIX);
    }

    /**
     * 重写get方法，获取到缓存后再次取缓存剩余的时间，如果时间小余我们配置的刷新时间就手动刷新缓存。
     * 为了不影响get的性能，启用后台线程去完成缓存的刷。
     * 并且只放一个线程去刷新数据。
     *
     * @param key
     * @return
     */
    @Override
    public ValueWrapper get(final Object key) {
        RedisCacheKey cacheKey = getRedisCacheKey(key);
        String cacheKeyStr = getCacheKey(key);
        // 调用重写后的get方法
        ValueWrapper valueWrapper = this.get(cacheKey);

        if (null != valueWrapper && CustomizedRedisCache.this.preloadSecondTime > 0) {
            // 刷新缓存数据
            refreshCache(key, cacheKeyStr);
        }
        return valueWrapper;
    }

    /**
     * 重写父类的get函数。
     * 父类的get方法，是先使用exists判断key是否存在，不存在返回null，存在再到redis缓存中去取值。这样会导致并发问题，
     * 假如有一个请求调用了exists函数判断key存在，但是在下一时刻这个缓存过期了，或者被删掉了。
     * 这时候再去缓存中获取值的时候返回的就是null了。
     * 可以先获取缓存的值，再去判断key是否存在。
     *
     * @param cacheKey
     * @return
     */
    @Override
    public RedisCacheElement get(final RedisCacheKey cacheKey) {
        Assert.notNull(cacheKey, "CacheKey must not be null!");

        // Redis cache 不会存null，所以可以直接根据NULL来判断缓存时候存在
        // 根据key获取缓存值
        Object result = lookup(cacheKey);
        if (result == null) {
            // 缓存不存在直接返回NULL
            return null;
        }

        return new RedisCacheElement(cacheKey, fromStoreValue(result));
    }

    @Override
    public <T> T get(final Object key, final Callable<T> valueLoader) {

        String cacheKeyStr = getCacheKey(key);
        // 先获取缓存，如果有直接返回
        Object result = redisOperations.opsForValue().get(cacheKeyStr);
        if (result != null) {
            return (T) result;
        }

        RedisLock redisLock = new RedisLock((RedisTemplate<String, Object>) redisOperations, cacheKeyStr + "_sync_lock");

        for (int i = 0; i < RETRY_COUNT; i++) {
            try {
                // 先取缓存，如果有直接返回，没有再去做拿锁操作
                result = redisOperations.opsForValue().get(key);
                if (result != null) {
                    return (T) result;
                }

                // 获取分布式锁去后台查询数据
                if (redisLock.lock()) {
                    T t = super.get(key, valueLoader);
                    // 唤醒线程
                    container.signalAll(cacheKeyStr);
                    return t;
                }
                // 线程等待
                container.await(cacheKeyStr, 20);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            } finally {
                redisLock.unlock();
            }
        }

        return super.get(key, valueLoader);
    }

    /**
     * 刷新缓存数据
     */
    @SuppressWarnings("unchecked")
    private void refreshCache(Object key, String cacheKeyStr) {
        Long ttl = this.redisOperations.getExpire(cacheKeyStr);
        if (null != ttl && ttl <= CustomizedRedisCache.this.preloadSecondTime) {
            // 判断是否需要强制刷新在开启刷新线程
            if (!getForceRefresh()) {
                softRefresh(cacheKeyStr);
            } else {
                forceRefresh(cacheKeyStr);
            }
        }
    }

    /**
     * 软刷新，直接修改缓存时间
     *
     * @param cacheKeyStr 缓存key
     */
    @SuppressWarnings("unchecked")
    private void softRefresh(String cacheKeyStr) {
        // 加一个分布式锁，只放一个请求去刷新缓存
        RedisLock redisLock = new RedisLock((RedisTemplate<String, Object>) redisOperations, cacheKeyStr + "_lock");
        try {
            if (redisLock.tryLock()) {
                redisOperations.expire(cacheKeyStr, this.expirationSecondTime, TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            redisLock.unlock();
        }
    }

    /**
     * 硬刷新（走数据库）
     *
     * @param cacheKeyStr
     */
    @SuppressWarnings("unchecked")
    private void forceRefresh(String cacheKeyStr) {
        // 尽量少的去开启线程，因为线程池是有限的
        ThreadTaskUtils.run(new Runnable() {
            @Override
            public void run() {
                // 加一个分布式锁，只放一个请求去刷新缓存
                RedisLock redisLock = new RedisLock((RedisTemplate<String, Object>) redisOperations, cacheKeyStr + "_lock");
                try {
                    if (redisLock.lock()) {
                        // 获取锁之后再判断一下过期时间，看是否需要加载数据
                        Long ttl = CustomizedRedisCache.this.redisOperations.getExpire(cacheKeyStr);
                        if (null != ttl && ttl <= CustomizedRedisCache.this.preloadSecondTime) {
                            // 通过获取代理方法信息重新加载缓存数据
                            CustomizedRedisCache.this.getCacheSupport().refreshCacheByKey(CustomizedRedisCache.super.getName(), cacheKeyStr);
                        }
                    }
                } catch (Exception e) {
                    logger.info(e.getMessage(), e);
                } finally {
                    redisLock.unlock();
                }
            }
        });
    }

    /**
     * 获取缓存的有效时间
     *
     * @return
     */
    public long getExpirationSecondTime() {
        return expirationSecondTime;
    }

    /**
     * 获取RedisCacheKey
     *
     * @param key
     * @return
     */
    public RedisCacheKey getRedisCacheKey(Object key) {

        return new RedisCacheKey(key).usePrefix(this.prefix)
                .withKeySerializer(redisOperations.getKeySerializer());
    }

    /**
     * 获取RedisCacheKey
     *
     * @param key
     * @return
     */
    public String getCacheKey(Object key) {
        return new String(getRedisCacheKey(key).getKeyBytes());
    }

    /**
     * 是否强制刷新（走数据库），默认是false
     *
     * @return
     */
    public boolean getForceRefresh() {
        return forceRefresh;
    }
}
