/**
 * 
 */
package cn.com.compass.web.wrapper;

import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 自定义request-wrapper 用于塞入自定义头参以及封装body
 * @date 2018年10月17日 下午9:41:40
 * 
 */
public class CustomHttpServletRequestWrapper extends HttpServletRequestWrapper{
    /**
     * 头参
     */
	private Map<String, String> headers;
    /**
     * body参数
     */
    private byte[] body;

    public CustomHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        this.headers = new HashMap<>();
        body = IOUtils.toByteArray(request.getReader(), "utf-8");
    }

    /**
     * 塞入自定义头参
     * @param name
     * @param value
     */
    public void setHeader(String name, String value) {
        this.headers.put(name, value);
    }

    @Override
    public String getHeader(String name) {
        String value = this.headers.get(name);
        if (value != null) {
            return value;
        }
        return ((HttpServletRequest) getRequest()).getHeader(name);
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        Set<String> set = new HashSet<>(headers.keySet());
        Enumeration<String> enumeration = ((HttpServletRequest) getRequest()).getHeaderNames();
        while (enumeration.hasMoreElements()) {
            String name = enumeration.nextElement();
            set.add(name);
        }
        return Collections.enumeration(set);
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream bais = new ByteArrayInputStream(body);
        return new ServletInputStream() {

            @Override
            public int read() throws IOException {
                return bais.read();
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener( ReadListener arg0 ) {

            }
        };
    }
}
