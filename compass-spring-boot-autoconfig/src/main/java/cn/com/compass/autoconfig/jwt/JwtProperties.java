package cn.com.compass.autoconfig.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.UUID;
/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo jwt属性
 * @date 2018年6月6日 下午3:33:08
 *
 */
@Data
@ConfigurationProperties(prefix = JwtProperties.prefix)
public class JwtProperties {
	public static final String prefix = "jwt";
	
	private boolean enabled = true;
	/**
     * JWT 生成密钥使用的密码
     */
    private String secret = "cn.com.compass";
    
    /**
     * 过期时间 默认二个小时
     */
    private long expire = 2*60*60*1000L; 
    /**
     * 默认两天
     */
    private long refreshExpire = 2*24*60*1000L;
    
    /**
     * 签发方
     */
    private String iss = "compass";
    /**
     * 接收方
     */
    private String aud = "anyone";
    /**
     * 唯一标识
     */
    private String jti = UUID.randomUUID().toString();
	
}
