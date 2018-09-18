/**
 * 
 */
package cn.com.compass.camel.interceptor;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;

import com.github.benmanes.caffeine.cache.Cache;

import cn.com.compass.base.constant.BaseConstant;
import cn.com.compass.web.context.AppContext;
import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo feign 头参处理
 * @date 2018年9月12日 下午11:50:54
 * 
 */
public class CamelHeadInterceptor implements RequestInterceptor{
	
	/**
	 * hystrix.command.default.execution.isolation.strategy = SEMAPHORE | THREAD </br>
	 * 属性strategy = SEMAPHORE会在同一个线程中 </br>
	 *    	strategy = THREAD 会开启多个线程 </br>
	 * 为兼容以上两种模式，还是使用caffeine作为缓存跨越线程隔离问题 </br>
	 */
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void apply(RequestTemplate template) {
		Cache cache = AppContext.getInstance().getBean(Cache.class);
		if(cache!=null) {
			// 获取MessageId
			Map<String, Collection<String>> headers = template.headers();
			Collection<String> messageColl = headers.get(BaseConstant.MESSAGEID);
			// 获取缓存
			if(CollectionUtils.isNotEmpty(messageColl)) {
				String[] messageArr = messageColl.toArray(new String[0]);
				Map<String,String> cacheMap = (Map<String, String>) cache.getIfPresent(messageArr[0]);
				if(MapUtils.isNotEmpty(cacheMap)) {
					for(Map.Entry<String, String> cm : cacheMap.entrySet()) {
						template.header(cm.getKey(), cm.getValue());
					}
				}
			}
		}
	}

}
