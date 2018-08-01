package cn.com.compass.ribbon;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.netflix.loadbalancer.BestAvailableRule;
import com.netflix.loadbalancer.IRule;
/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo ribbo 配置
 * @date 2018年6月21日 下午8:46:50
 *
 */
@Configuration
public class RibboConfig {
	// 负载均衡算法
	/**
	 * RandomRule随机策略
	 * @return
	 */
//	@Bean
//    public IRule randomRule() {
//        return new RandomRule();
//    }
	
	/**
	 * BestAvailableRule最少并发数策略
	 * @return
	 */
	@Bean
	public IRule bestAvailableRule() {
	    return new BestAvailableRule();
	}

}
