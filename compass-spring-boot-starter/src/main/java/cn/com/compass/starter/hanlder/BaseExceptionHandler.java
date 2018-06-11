package cn.com.compass.starter.hanlder;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import cn.com.compass.autoconfig.constant.ConstantUtil;
import cn.com.compass.base.constant.BaseConstant;
import cn.com.compass.base.exception.BaseException;
import cn.com.compass.base.vo.BaseResponseVo;
import cn.com.compass.util.JacksonUtil;
import cn.com.compass.web.context.AppContext;

/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 统一异常处理器
 * @date 2018年6月6日 下午3:55:57
 *
 */
@RestControllerAdvice
@EnableWebMvc
@Configuration
public class BaseExceptionHandler {
	
	/**
	 * 基类异常封装继承{@link cn.com.compass.base.constant.IBaseBizStatusEnum}接口的枚举
	 * 
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(BaseException.class)
	public void handleBaseException(BaseException exception) {
		this.printResonpseJson(exception.getErrorCode(), null,HttpStatus.BAD_REQUEST.value());
	}
	
	/**
	 * 后台代码参数不合法拦截
	 * @param exception
	 */
	@ExceptionHandler(IllegalArgumentException.class)
	public void handleIllegalArgumentException(IllegalArgumentException exception) {
		this.printResonpseJson(BaseConstant.ILLEGAL_ARGUMENT, null,HttpStatus.BAD_REQUEST.value());
	}

	/**
	 * 获取@Validated（spring对hibernate validator的扩展用于校验基础数据类型）注解抛出来的错误
	 * 
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(ValidationException.class)
	public void handleValidationException(ValidationException exception) {
		String data = null;
		if (exception instanceof ConstraintViolationException) {
			ConstraintViolationException exs = (ConstraintViolationException) exception;
			StringBuffer buff = new StringBuffer();
			Set<ConstraintViolation<?>> violations = exs.getConstraintViolations();
			for (ConstraintViolation<?> item : violations) {
				buff.append(item.getMessage());
			}
			data = buff.toString();
		}
		this.printResonpseJson(BaseConstant.REQUEST_PARAMS_VALID_ERRO, data,HttpStatus.BAD_REQUEST.value());
	}

	/**
	 * 获取@valid（只能校验bean） 注解抛出来的错误
	 * 
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public void handleException(MethodArgumentNotValidException exception) {
		BindingResult validResult = exception.getBindingResult();
		Map<String, Object> errorMp = new HashMap<>();
		for (ObjectError error : validResult.getAllErrors()) {
			String field = null;
			if (error instanceof FieldError) {
				field = ((FieldError) error).getField();
			} else {
				field = error.getCode();
			}
			String message = error.getDefaultMessage();
			errorMp.put(field, message);
		}
		this.printResonpseJson(BaseConstant.REQUEST_PARAMS_VALID_ERRO, errorMp ,HttpStatus.BAD_REQUEST.value());
	}
	
	@Resource
	private HttpServletResponse response;
	
	/**
	 * json格式数据打印
	 * 
	 * @param rs
	 */
	public void printResonpseJson(String code,Object data,Integer httpStatus) {
		PrintWriter writer = null;
		try {
			ConstantUtil constantUtil = AppContext.getInstance().getBean(ConstantUtil.class);
			String msg = constantUtil.getValue(code);
			BaseResponseVo rsp = new BaseResponseVo(code, msg).setData(data);
			response.setContentType(MediaType.APPLICATION_JSON_UTF8.toString());
			response.setHeader("Cache-Control", "no-cache");
			response.setStatus(httpStatus!=null?httpStatus:HttpStatus.OK.value());
			writer = response.getWriter();
			writer.write(JacksonUtil.obj2json(rsp));
			writer.flush();
		} catch (Exception e) {
			throw new BaseException(BaseConstant.RESPONSE_DATA_TO_JSON_ERRO, e);
		} finally {
			if (writer != null)
				writer.close();
		}
	}

}
