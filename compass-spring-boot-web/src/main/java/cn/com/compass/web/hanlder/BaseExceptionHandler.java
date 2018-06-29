package cn.com.compass.web.hanlder;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
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
import cn.com.compass.base.vo.BaseResponseVo;

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
	public BaseResponseVo handleBaseException(BaseException exception) {
		return new BaseResponseVo(exception.getErrorCode(),null,exception.getMessage());
	}
	
	/**
	 * 后台代码参数不合法拦截
	 * @param exception
	 */
	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public BaseResponseVo handleIllegalArgumentException(IllegalArgumentException exception) {
		return new BaseResponseVo(BaseConstant.ILLEGAL_ARGUMENT,null,exception.getMessage());
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
	public BaseResponseVo handleValidationException(ValidationException exception) {
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
		return new BaseResponseVo(BaseConstant.REQUEST_PARAMS_VALID_ERRO,null,data);
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
	public BaseResponseVo handleException(MethodArgumentNotValidException exception) {
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
		return new BaseResponseVo(BaseConstant.REQUEST_PARAMS_VALID_ERRO,null,errorMp);
	}
	
//	@Resource
//	private HttpServletResponse response;
	
	/**
	 * json格式数据打印
	 * 
	 * @param rs
	 */
//	public void printResonpseJson(String code,Object data,Integer httpStatus) {
//		PrintWriter writer = null;
//		try {
//			ConstantUtil constantUtil = AppContext.getInstance().getBean(ConstantUtil.class);
//			String msg = constantUtil.getValue(code);
//			BaseResponseVo rsp = new BaseResponseVo(code, msg).setData(data);
//			HttpServletResponse response = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
//			response.setContentType(MediaType.APPLICATION_JSON_UTF8.toString());
//			response.setHeader("Cache-Control", "no-cache");
//			response.setStatus(httpStatus!=null?httpStatus:HttpStatus.OK.value());
//			writer = response.getWriter();
//			writer.write(JacksonUtil.obj2json(rsp));
//			writer.flush();
//		} catch (Exception e) {
//			throw new BaseException(BaseConstant.RESPONSE_DATA_TO_JSON_ERRO, e);
//		} finally {
//			if (writer != null)
//				writer.close();
//		}
//	}

}
