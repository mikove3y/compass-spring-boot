package cn.com.compass.camel.router.base;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.github.benmanes.caffeine.cache.Cache;

import cn.com.compass.base.constant.BaseConstant;
import cn.com.compass.camel.local.LocalCamel;
import cn.com.compass.web.context.AppContext;

/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo localCamel头参处理器
 * @date 2018年9月13日 下午10:17:55
 *
 */
public class LocalCamelProcessor implements Processor {
	
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void process(Exchange exchange) throws Exception {
		// 交换Id
		String exchangeId = exchange.getExchangeId();
		// 从in-message获取头参
		String authorization = (String) exchange.getIn().getHeader(BaseConstant.AUTHORIZATION_KEY);
		String subject = (String) exchange.getIn().getHeader(BaseConstant.REQUEST_SUBJECT_ATTRIBUTE_KEY);
		String dataScop = (String) exchange.getIn().getHeader(BaseConstant.REQUEST_DATA_PERMISSION);
		// 放入caffeine缓存
		Map<String,String> headerCache = new HashMap<>();
		headerCache.put(BaseConstant.AUTHORIZATION_KEY, authorization);
		headerCache.put(BaseConstant.REQUEST_SUBJECT_ATTRIBUTE_KEY, subject);
		headerCache.put(BaseConstant.REQUEST_DATA_PERMISSION, dataScop);
		Cache cache = AppContext.getInstance().getBean(Cache.class);
		cache.put(exchangeId, headerCache);
		// 放入线程localCamel缓存
		LocalCamel.getLocalCamel().init(subject, authorization, dataScop);
		// 入参塞入exchangeId到头部，作为全服路由Id
		exchange.getIn().setHeader(BaseConstant.MESSAGEID, exchangeId);
	}


}
