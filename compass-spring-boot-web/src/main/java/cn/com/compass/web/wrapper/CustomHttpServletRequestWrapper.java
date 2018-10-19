/**
 * 
 */
package cn.com.compass.web.wrapper;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 自定义request-wrapper 用于塞入自定义头参
 * @date 2018年10月17日 下午9:41:40
 * 
 */
public class CustomHttpServletRequestWrapper extends HttpServletRequestWrapper{

	private Map<String, String> customHeaders;
	 
    public CustomHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
        this.customHeaders = new HashMap<>();
    }
 
    public void setHeader(String name, String value) {
        this.customHeaders.put(name, value);
    }
 
    public String getHeader(String name) {
        String value = this.customHeaders.get(name);
        if (value != null) {
            return value;
        }
        return ((HttpServletRequest) getRequest()).getHeader(name);
    }
 
    public Enumeration<String> getHeaderNames() {
        Set<String> set = new HashSet<>(customHeaders.keySet());
        Enumeration<String> enumeration = ((HttpServletRequest) getRequest()).getHeaderNames();
        while (enumeration.hasMoreElements()) {
            String name = enumeration.nextElement();
            set.add(name);
        }
        return Collections.enumeration(set);
    }

}
