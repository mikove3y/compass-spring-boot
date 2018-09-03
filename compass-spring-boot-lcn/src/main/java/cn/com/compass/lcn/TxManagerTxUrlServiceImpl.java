/**
 * 
 */
package cn.com.compass.lcn;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.codingapi.tx.config.service.TxManagerTxUrlService;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 
 * @date 2018年9月3日 下午4:08:58
 * 
 */
@Service
public class TxManagerTxUrlServiceImpl implements TxManagerTxUrlService {
	
	@Value("${tm.manager.url}")
    private String url;
	
	/* (non-Javadoc)
	 * @see com.codingapi.tx.config.service.TxManagerTxUrlService#getTxUrl()
	 */
	@Override
	public String getTxUrl() {
		return url;
	}

}
