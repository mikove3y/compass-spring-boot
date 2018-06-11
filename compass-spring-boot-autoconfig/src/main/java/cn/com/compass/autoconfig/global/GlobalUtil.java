package cn.com.compass.autoconfig.global;

import org.springframework.beans.factory.annotation.Autowired;

import cn.com.compass.autoconfig.security.JwtUtil;
import cn.com.compass.util.DESUtil;
import cn.com.compass.util.JacksonUtil;
/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo global util dev免登录检查，root账号检查
 * @date 2018年6月6日 下午3:31:29
 *
 */
public class GlobalUtil {
	
	@Autowired
	private GlobalProperties properties;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	/**
	 * 检查是否自动登录
	 * @return
	 */
	public boolean autoLogin() {
		return properties.getMode().equals(GlobalConstant.Mode.dev);
	}
	
	/**
	 * 检查系统用户
	 * @param user
	 * @param pwd
	 * @return
	 */
	public boolean checkRoot(String user, String pwd) throws Exception {
		boolean result = false;
		String ciphertext = DESUtil.encrypt(properties.getDesKey(), pwd);
		if (GlobalConstant.ROOT.equals(user)) {
			result = ciphertext.equals(properties.getRootPassword());
		}
		return result;
	}
	
	/**
	 * 生成token
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public String genRootToken(String user) throws Exception {
		String token = null;
		if (GlobalConstant.ROOT.equals(user)) {
			token = jwtUtil.buildJWT(JacksonUtil.obj2json(GlobalConstant.ROOT_SUBJECT));
		}
		return token;
	}

}
