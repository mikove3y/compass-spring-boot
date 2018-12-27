package cn.com.compass.web.hanlder;

import cn.com.compass.autoconfig.constant.ConstantUtil;
import cn.com.compass.base.constant.BaseConstant;
import cn.com.compass.base.exception.BaseException;
import cn.com.compass.base.vo.BaseResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.validation.ConstraintDeclarationException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.Set;

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
public class BaseExceptionHandler {

	@Autowired
	private ConstantUtil constantUtil;

	/**
	 * 统一封装错误
	 * @param e
	 * @return
	 */
	@ExceptionHandler(Throwable.class)
	@ResponseStatus(HttpStatus.OK)
	public BaseResponseVo handleException(Throwable e){
		if(e instanceof BaseException){
			// 基类异常封装继承{@link cn.com.compass.base.constant.IBaseBizStatusEnum}接口的枚举
			BaseException exception = (BaseException) e;
			return new BaseResponseVo(exception.getErrorCode(),constantUtil.getValue(exception.getErrorCode()),exception.getMessage());
		}else if(e instanceof  IllegalArgumentException){
			// 后台代码参数不合法拦截
			IllegalArgumentException exception = (IllegalArgumentException) e;
			return new BaseResponseVo(BaseConstant.ILLEGAL_ARGUMENT,constantUtil.getValue(BaseConstant.ILLEGAL_ARGUMENT),exception.getMessage());
		}else if(e instanceof  ValidationException){
			// 获取@Validated（spring对hibernate validator的扩展用于校验基础数据类型）注解抛出来的错误
			ValidationException exception = (ValidationException) e;
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
			return new BaseResponseVo(BaseConstant.REQUEST_PARAMS_VALID_ERRO,constantUtil.getValue(BaseConstant.REQUEST_PARAMS_VALID_ERRO),error);
		}else if(e instanceof  MethodArgumentNotValidException){
			// 获取@valid（只能校验bean） 注解抛出来的错误
			MethodArgumentNotValidException exception = (MethodArgumentNotValidException) e;
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
			return new BaseResponseVo(BaseConstant.REQUEST_PARAMS_VALID_ERRO,constantUtil.getValue(BaseConstant.REQUEST_PARAMS_VALID_ERRO),errorBuff.substring(0, errorBuff.length()-1));
		}else if(e instanceof  HttpMessageConversionException){
			// 请求参数转换错误 HttpMessageConversionException
			HttpMessageConversionException exception = (HttpMessageConversionException) e;
			return new BaseResponseVo(BaseConstant.REQUEST_PARAMS_VALID_ERRO,constantUtil.getValue(BaseConstant.REQUEST_PARAMS_VALID_ERRO),exception.getMessage());
		}else if(e instanceof NoHandlerFoundException){
			// 404 not found
			NoHandlerFoundException exception = (NoHandlerFoundException) e;
			return new BaseResponseVo(BaseConstant.API_NOT_FOUND,constantUtil.getValue(BaseConstant.API_NOT_FOUND),exception.getMessage());
		}
		// 未知错误
		return new BaseResponseVo(BaseConstant.UNKONW_ERROR,"unknow error",e.getMessage());
	}

}
