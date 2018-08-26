package cn.com.compass.activiti.handler;

import org.activiti.engine.ActivitiException;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import cn.com.compass.base.constant.BaseConstant;
import cn.com.compass.base.vo.BaseErroVo;

/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 流程异常处理器
 * @date 2018年8月26日 下午10:34:18
 *
 */
@RestControllerAdvice
@EnableWebMvc
@Configuration
public class ActivitiExceptionHandler {
	
	/**
	 * 流程出错
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(ActivitiException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public BaseErroVo handleBaseException(ActivitiException exception) {
		return new BaseErroVo(BaseConstant.INNER_ERRO,exception.getMessage());
	}

}
