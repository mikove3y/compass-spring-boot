package cn.com.compass.camel.router.base;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.lang3.StringUtils;

import com.github.benmanes.caffeine.cache.Cache;

import cn.com.compass.base.constant.BaseConstant;
import cn.com.compass.base.exception.BaseException;
import cn.com.compass.camel.local.LocalCamel;
import cn.com.compass.web.context.AppContext;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo localCamel头参处理器
 * @date 2018年9月13日 下午10:17:55
 *
 */
@Getter
@Setter
public class LocalCamelProcessor implements Processor {
	
	/**
	 * 是否需要鉴权
	 */
	private boolean needAuth = true;
	
	public LocalCamelProcessor() {
		
	}
	
	public LocalCamelProcessor(boolean needAuth) {
		this.needAuth = needAuth;
	}
	
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void process(Exchange exchange) throws Exception {
		// 交换Id
		String exchangeId = exchange.getExchangeId();
		// 从in-message获取头参
		String authorization = (String) exchange.getIn().getHeader(BaseConstant.AUTHORIZATION_KEY);
		String subject = (String) exchange.getIn().getHeader(BaseConstant.REQUEST_SUBJECT_ATTRIBUTE_KEY);
		String dataScop = (String) exchange.getIn().getHeader(BaseConstant.REQUEST_DATA_PERMISSION);
		// 检验authorization和subject
		if(this.isNeedAuth()&&StringUtils.isEmpty(authorization)&&StringUtils.isEmpty(subject)) {
			throw new BaseException(BaseConstant.TOKEN_GET_ERRO, "Authorization & BaseSubject can't be both empty");
		}
		// 放入线程localCamel缓存
		LocalCamel.getLocalCamel().init(subject, authorization, dataScop);
		// authorization不为空，subject为空重新给subject赋值
		if(StringUtils.isNotEmpty(authorization)&&StringUtils.isEmpty(subject)) {
			LocalCamel.getLocalCamel().parseSubject();
			subject = LocalCamel.getLocalCamel().getSubject();
		}
		// 放入caffeine缓存
		Map<String,String> headerCache = new HashMap<>();
		headerCache.put(BaseConstant.AUTHORIZATION_KEY, authorization);
		headerCache.put(BaseConstant.REQUEST_SUBJECT_ATTRIBUTE_KEY, subject);
		headerCache.put(BaseConstant.REQUEST_DATA_PERMISSION, dataScop);
		Cache cache = AppContext.getInstance().getBean(Cache.class);
		cache.put(exchangeId, headerCache);
		// 入参塞入exchangeId到头部，作为全服路由Id
		exchange.getIn().setHeader(BaseConstant.MESSAGEID, exchangeId);
	}


}
