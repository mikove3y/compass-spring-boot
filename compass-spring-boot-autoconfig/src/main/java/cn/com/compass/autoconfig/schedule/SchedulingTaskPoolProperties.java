package cn.com.compass.autoconfig.schedule;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 定时任务属性配置类
 * @date 2018/12/6 19:29
 */
@Data
@ConfigurationProperties(prefix= SchedulingTaskPoolProperties.prefix)
public class SchedulingTaskPoolProperties {

    public static final String prefix = "spring.scheduling.pool";
    /**
     * 默认不启用
     */
    private boolean enabled = false;
    /**
     * 线程池大小
     */
    private int poolSize = 5;
    /**
     * 线程名前缀
     */
    private String threadNamePrefix = "Scheduling_";
    /**
     * shut-down前执行任务的时间（秒），时间过了就直接关闭
     */
    private int awaitTerminationSeconds = 600;
    /**
     * 关机前是否先处理完任务
     */
    private boolean waitForJobsToCompleteOnShutdown = true;
}
