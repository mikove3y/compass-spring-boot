/**
 * 
 */
package cn.com.compass.camel.local;

import org.apache.commons.lang3.StringUtils;

import cn.com.compass.base.vo.BaseSubject;
import cn.com.compass.util.JacksonUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo camel本地缓存
 * @date 2018年9月13日 下午9:54:26
 * 
 */
@Slf4j
@Data
public class LocalCamel {
	
	private static ThreadLocal<LocalCamel> local =new ThreadLocal<>(); 	
	
	/**
	 * 获取localCamel信息
	 * @return
	 */
	public static LocalCamel getLocalCamel(){
		LocalCamel lc = local.get();
		if( lc==null ){
			lc =new LocalCamel();
			local.set(lc);
		}
		return lc;
	}
	
	/**
	 * 清空当前线程参数
	 */
	public void clear() {
		local.remove();
	}
	
	private String subject;
	
	private String authorization;
	
	private String dataScop;
	
	/**
	 * 初始化
	 * @param subject
	 * @param authorization
	 * @param dataScop
	 */
	public void init(String subject,String authorization,String dataScop) {
		this.subject = subject;
		this.authorization = authorization;
		this.dataScop = dataScop;
	}
	
	/**
	 * 解析用户信息
	 * @return
	 */
	public BaseSubject parseSubject() {
		try {
			String subject = this.getSubject();
			if(StringUtils.isNotEmpty(subject)&&JacksonUtil.isJSONValid(subject)) {
				return JacksonUtil.json2pojo(subject, BaseSubject.class);
			}
		} catch (Exception e) {
			log.error("parseSubject,error:{}", e);
		}
		return null;
	}
	
}
