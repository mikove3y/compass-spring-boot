/**
 * 
 */
package cn.com.compass.activiti.id;

import org.activiti.engine.impl.cfg.IdGenerator;

import java.util.UUID;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 
 * @date 2018年8月26日 下午6:04:46
 * 
 */
public class UUIDGenerator implements IdGenerator {

	/* (non-Javadoc)
	 * @see org.activiti.engine.impl.cfg.IdGenerator#getNextId()
	 */
	@Override
	public String getNextId() {
		return UUID.randomUUID().toString().replace("-", "");
	}

}
