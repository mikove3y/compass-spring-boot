package cn.com.compass.camel.router.base;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import cn.com.compass.autoconfig.constant.ConstantUtil;
import cn.com.compass.base.constant.BaseConstant;
import cn.com.compass.base.vo.BaseResponseVo;
import cn.com.compass.util.ExceptionUtil;
import cn.com.compass.web.context.AppContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BaseErrorProcessor implements Processor {
	/**
	 * camel 统一异常处理
	 */
	@Override
	public void process(Exchange exchange) throws Exception {
		ConstantUtil constant = AppContext.getInstance().getBean(ConstantUtil.class);
		Exception e = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
		log.error(ExceptionUtil.stackTraceToString(e,BaseErrorProcessor.class.getName()));
		exchange.getOut().setBody(new BaseResponseVo(BaseConstant.INNER_ERRO, constant.getValue(BaseConstant.INNER_ERRO),e));
	}

}
