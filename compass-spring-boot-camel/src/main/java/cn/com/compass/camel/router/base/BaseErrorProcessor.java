package cn.com.compass.camel.router.base;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.http.MediaType;

import com.github.benmanes.caffeine.cache.Cache;
import com.netflix.hystrix.exception.HystrixBadRequestException;

import cn.com.compass.autoconfig.constant.ConstantUtil;
import cn.com.compass.base.constant.BaseConstant;
import cn.com.compass.base.exception.BaseException;
import cn.com.compass.base.vo.BaseErroVo;
import cn.com.compass.base.vo.BaseResponseVo;
import cn.com.compass.camel.local.LocalCamel;
import cn.com.compass.util.JacksonUtil;
import cn.com.compass.web.context.AppContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BaseErrorProcessor implements Processor {
	/**
	 * camel 统一异常处理
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public void process(Exchange exchange) throws Exception {
		String exchangeId = exchange.getExchangeId();
		ConstantUtil constant = AppContext.getInstance().getBean(ConstantUtil.class);
		Exception e = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
		String status = BaseConstant.INNER_ERRO;
		String errorMsg = e.getMessage();
		// 优化错误提示信息
		if (JacksonUtil.isJSONValid(errorMsg)) {
			BaseErroVo bev = JacksonUtil.json2pojo(errorMsg, BaseErroVo.class);
			status = bev.getStatus();
			errorMsg = bev.getError();
		}else if(e instanceof BaseException) {
			BaseException e1 = (BaseException) e;
			status = e1.getErrorCode();
			errorMsg = e1.getMessage();
		}
		log.error(errorMsg);
		exchange.getOut().setHeader("content-type", MediaType.APPLICATION_JSON_UTF8_VALUE);
		exchange.getOut().setBody(new BaseResponseVo(status, constant.getValue(status), errorMsg));
		// 清空缓存
		Cache cache = AppContext.getInstance().getBean(Cache.class);
		cache.invalidate(exchangeId);
		// 清空localCamel
		LocalCamel.getLocalCamel().clear();
	}

}
