package cn.com.compass.cache.setting;

import com.github.benmanes.caffeine.cache.CaffeineSpec;

/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 以及缓存设置
 * @date 2018年8月5日 上午12:24:36
 *
 */
public class FirstCacheSetting {

    /**
     * 一级缓存配置，配置项请点击这里 {@link CaffeineSpec#configure(String, String)}
     * @param cacheSpecification
     */
    public FirstCacheSetting(String cacheSpecification) {
        this.cacheSpecification = cacheSpecification;
    }

    private String cacheSpecification;

    public String getCacheSpecification() {
        return cacheSpecification;
    }
}
