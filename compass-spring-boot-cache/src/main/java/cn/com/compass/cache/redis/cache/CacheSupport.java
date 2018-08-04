package cn.com.compass.cache.redis.cache;

import java.lang.reflect.Method;
import java.util.Set;

/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 注册和刷新缓存接口
 * @date 2018年8月5日 上午12:21:26
 *
 */
public interface CacheSupport {

    /**
     * 注册缓存方法信息
     *
     * @param invokedBean          代理Bean
     * @param invokedMethod        代理方法名称
     * @param invocationParamTypes 代理方法参数类型
     * @param invocationArgs       代理方法参数
     * @param cacheNames           缓存名称（@Cacheable注解的value）
     * @param cacheKey             缓存key（@Cacheable注解的key）
     */
    void registerInvocation(Object invokedBean, Method invokedMethod, @SuppressWarnings("rawtypes") Class[] invocationParamTypes, Object[] invocationArgs, Set<String> cacheNames, String cacheKey);

    /**
     * 按容器以及指定键更新缓存
     *
     * @param cacheName
     * @param cacheKey
     */
    void refreshCacheByKey(String cacheName, String cacheKey);

}