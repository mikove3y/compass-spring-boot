package cn.com.compass.web.hanlder;

import java.util.Set;

import javax.validation.ConstraintDeclarationException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import cn.com.compass.base.constant.BaseConstant;
import cn.com.compass.base.exception.BaseException;
import cn.com.compass.base.vo.BaseErroVo;

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
	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public BaseErroVo handleBaseException(BaseException exception) {
		return new BaseErroVo(exception.getErrorCode(),exception.getMessage());
	}
	
	/**
	 * 后台代码参数不合法拦截
	 * @param exception
	 */
	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public BaseErroVo handleIllegalArgumentException(IllegalArgumentException exception) {
		return new BaseErroVo(BaseConstant.ILLEGAL_ARGUMENT,exception.getMessage());
	}

	/**
	 * 获取@Validated（spring对hibernate validator的扩展用于校验基础数据类型）注解抛出来的错误
	 * 
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(ValidationException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public BaseErroVo handleValidationException(ValidationException exception) {
		String error = null;
		if (exception instanceof ConstraintViolationException) {
			ConstraintViolationException exs = (ConstraintViolationException) exception;
			StringBuffer buff = new StringBuffer();
			Set<ConstraintViolation<?>> violations = exs.getConstraintViolations();
			for (ConstraintViolation<?> item : violations) {
				buff.append(item.getPropertyPath()+":"+item.getMessage()+",");
			}
			error = buff.substring(0, buff.length()-1);
		}else if(exception instanceof ConstraintDeclarationException) {
			// HV000151 问题
			String solveScheme = "To solve the issue, add the constraints to the interface method instead of the implementation method.";
			error = exception.getMessage()+","+solveScheme;
		}
		return new BaseErroVo(BaseConstant.REQUEST_PARAMS_VALID_ERRO,error);
	}

	/**
	 * 获取@valid（只能校验bean） 注解抛出来的错误
	 * 
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public BaseErroVo handleException(MethodArgumentNotValidException exception) {
		BindingResult validResult = exception.getBindingResult();
		StringBuffer errorBuff = new StringBuffer();
		for (ObjectError error : validResult.getAllErrors()) {
			String field = null;
			if (error instanceof FieldError) {
				field = ((FieldError) error).getField();
			} else {
				field = error.getCode();
			}
			String message = error.getDefaultMessage();
			errorBuff.append(field+":"+message+",");
		}
		return new BaseErroVo(BaseConstant.REQUEST_PARAMS_VALID_ERRO,errorBuff.substring(0, errorBuff.length()-1));
	}
	
	/**
	 * 请求参数转换错误 HttpMessageConversionException
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(HttpMessageConversionException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public BaseErroVo handleException(HttpMessageConversionException exception) {
		return new BaseErroVo(BaseConstant.REQUEST_PARAMS_VALID_ERRO,exception.getMessage());
	}
	
}
