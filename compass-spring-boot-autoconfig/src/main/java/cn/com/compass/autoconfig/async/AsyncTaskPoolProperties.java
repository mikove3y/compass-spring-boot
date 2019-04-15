package cn.com.compass.autoconfig.async;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 异步任务线程池属性配置类
 * @date 2018/12/6 16:57
 */
@Data
@ConfigurationProperties(prefix=AsyncTaskPoolProperties.prefix)
public class AsyncTaskPoolProperties {

    public static final String prefix = "spring.async.pool";
    /**
     * 默认不启用
     */
    private boolean enabled = false;
    /**
     * 核心线程池数
     */
    private int corePoolSize = 10;
    /**
     * 最大线程池数
     */
    private int maxPoolSize = 10;
    /**
     * 队列容量
     */
    private int queueCapacity = 100;
    /**
     * 存活时间
     */
    private int keepAliveSeconds = 30;
    /**
     * 是否允许线程超时
     */
    private boolean allowCoreThreadTimeOut = false;
    /**
     * 线程名前缀
     */
    private String threadNamePrefix = "Async_";
    /**
     * 是否停机前执行完任务
     */
    private boolean waitForJobsToCompleteOnShutdown = true;

}
