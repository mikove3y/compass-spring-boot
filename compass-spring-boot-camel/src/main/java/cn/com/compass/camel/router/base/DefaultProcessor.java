/**
 * 
 */
package cn.com.compass.camel.router.base;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.github.benmanes.caffeine.cache.Cache;

import cn.com.compass.autoconfig.constant.ConstantUtil;
import cn.com.compass.base.constant.BaseConstant;
import cn.com.compass.base.vo.BaseResponseVo;
import cn.com.compass.camel.local.LocalCamel;
import cn.com.compass.web.context.AppContext;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 默认转换器
 * @date 2018年9月4日 下午3:16:03
 * 
 */
public class DefaultProcessor implements Processor {

	/* (non-Javadoc)
	 * @see org.apache.camel.Processor#process(org.apache.camel.Exchange)
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public void process(Exchange exchange) throws Exception {
		// 交换Id
		String exchangeId = exchange.getExchangeId();
		ConstantUtil constant = AppContext.getInstance().getBean(ConstantUtil.class);
		Object in = exchange.getIn().getBody();
		if (in instanceof BaseResponseVo) {
			BaseResponseVo rv = (BaseResponseVo)in;
			rv.setMsg(constant.getValue(rv.getStatus()));
			exchange.getOut().setBody(in);
		}else {
			exchange.getOut().setBody(BaseResponseVo.success().setMsg(constant.getValue(BaseConstant.SUCCESS)).setData(in));
		}
		// 清空缓存caffeine缓存
		Cache cache = AppContext.getInstance().getBean(Cache.class);
		cache.invalidate(exchangeId);
		// 清空localCamel
		LocalCamel.getLocalCamel().clear();
	}

}
