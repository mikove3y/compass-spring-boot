package cn.com.compass.web.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.View;

import cn.com.compass.base.constant.BaseConstant;
import cn.com.compass.base.exception.BaseException;
import cn.com.compass.base.vo.BaseResponseVo;
import cn.com.compass.util.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 基类controller
 * @date 2018年6月6日 下午3:59:24
 *
 */
@Validated
@Slf4j
public abstract class BaseController {
	
	
	@Resource
	private HttpServletResponse response;
	@Resource
	private HttpServletRequest request;
	
	public HttpServletResponse response() {
		return response;
	}
	
	public HttpServletRequest request() {
		return request;
	}
	
	public NativeWebRequest nativeWebRequest() {
		return new ServletWebRequest(request);
	}
	
	public Map<String, Object> pathVarialbes() {
		Object obj = nativeWebRequest().getAttribute(View.PATH_VARIABLES, RequestAttributes.SCOPE_REQUEST);
		return obj != null ? (Map<String, Object>) obj : new HashMap<>();
	}
	
	
	public Object getPathVarialbes(String key) {
		return pathVarialbes().get(key);
	}
	
	
	public Map<String,Object> requestHeaders() {
		return null;
	}
	
	public Map<String,Object> requestParams() {
		return null;
	}
	
	/**
	 * 检查hibernate ConstraintViolation是否有校验不通过的数据
	 * @param set
	 * @return
	 * @throws Exception
	 */
	public <T> void checkHVConstraintViolation(Set<ConstraintViolation<T>> set) {
		if (set != null && !set.isEmpty()) {
			StringBuffer buff = new StringBuffer();
			for (ConstraintViolation<T> item : set) {
				buff.append(item.getPropertyPath()+":"+item.getMessage()+",");
			}
			if (buff.length()>0) {
				throw new BaseException(BaseConstant.REQUEST_PARAMS_VALID_ERRO, buff.substring(0, buff.length()-1));
			}
		}
	}
	/**
	 * json格式数据打印
	 * 
	 * @param rs
	 */
	public void printResonpseJson(Object rs) {
		PrintWriter writer = null;
		try {
			response.setContentType(MediaType.APPLICATION_JSON_UTF8.toString());
			response.setHeader("Cache-Control", "no-cache");
			response.setStatus(HttpStatus.OK.value());
			BaseResponseVo rsp = BaseResponseVo.success().setData(rs);
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
