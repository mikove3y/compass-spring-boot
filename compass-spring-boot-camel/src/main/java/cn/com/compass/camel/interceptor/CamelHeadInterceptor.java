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
