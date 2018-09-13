/**
 * 
 */
package cn.com.compass.feign;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo feign 头参处理
 * @date 2018年9月12日 下午11:50:54
 * 
 */
public class HeadInterceptor implements RequestInterceptor{

	@Override
	public void apply(RequestTemplate template) {
		 ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                 .getRequestAttributes();
         HttpServletRequest request = attributes.getRequest();
         Enumeration<String> headerNames = request.getHeaderNames();
         if (headerNames != null) {
             while (headerNames.hasMoreElements()) {
                 String name = headerNames.nextElement();
                 String values = request.getHeader(name);
                 template.header(name, values);
             }
         }
         Enumeration<String> bodyNames = request.getParameterNames();
         StringBuffer body =new StringBuffer();
         if (bodyNames != null) {
             while (bodyNames.hasMoreElements()) {
                 String name = bodyNames.nextElement();
                 String values = request.getParameter(name);
                 body.append(name).append("=").append(values).append("&");
             }
         }
         if(body.length()!=0) {
             body.deleteCharAt(body.length()-1);
             template.body(body.toString());
         }
	}

}
