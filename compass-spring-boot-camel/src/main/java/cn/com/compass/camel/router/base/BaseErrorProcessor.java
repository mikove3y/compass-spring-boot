package cn.com.compass.camel.router.base;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import cn.com.compass.base.vo.BaseResponseVo;
import cn.com.compass.util.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BaseErrorProcessor implements Processor {
	/**
	 * camel 统一异常处理
	 */
	@Override
	public void process(Exchange exchange) throws Exception {
		Exception e = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
		log.error(ExceptionUtil.stackTraceToString(e,BaseErrorProcessor.class.getName()));
		exchange.getOut().setBody(BaseResponseVo.success());
	}

}
