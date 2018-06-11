package cn.com.compass.web.controller;

import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import cn.com.compass.base.constant.BaseConstant;
import cn.com.compass.base.exception.BaseException;
import cn.com.compass.base.vo.BaseResponseVo;
import cn.com.compass.util.JacksonUtil;
/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 基类controller
 * @date 2018年6月6日 下午3:59:24
 *
 */
public abstract class BaseController {
	
	
	@Resource
	private HttpServletResponse response;
	

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
