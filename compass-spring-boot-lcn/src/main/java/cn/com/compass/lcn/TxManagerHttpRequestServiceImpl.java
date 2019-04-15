/**
 * 
 */
package cn.com.compass.lcn;

import com.codingapi.tx.netty.service.TxManagerHttpRequestService;
import com.lorne.core.framework.utils.http.HttpUtils;
import org.springframework.stereotype.Service;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo lcn 
 * @date 2018年9月3日 下午4:07:16
 * 
 */
@Service
public class TxManagerHttpRequestServiceImpl implements TxManagerHttpRequestService {

	/* (non-Javadoc)
	 * @see com.codingapi.tx.netty.service.TxManagerHttpRequestService#httpGet(java.lang.String)
	 */
	@Override
	public String httpGet(String url) {
		//GET请求前
        String res = HttpUtils.get(url);
        //GET请求后
        return res;
	}

	/* (non-Javadoc)
	 * @see com.codingapi.tx.netty.service.TxManagerHttpRequestService#httpPost(java.lang.String, java.lang.String)
	 */
	@Override
	public String httpPost(String url, String params) {
		//POST请求前
        String res = HttpUtils.post(url,params);
        //POST请求后
        return res;
	}

}
