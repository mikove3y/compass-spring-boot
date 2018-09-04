package cn.com.compass.autoconfig.jwt;


import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.compass.base.constant.BaseConstant;
import cn.com.compass.util.Base64;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo jwt 工具类，生成token，检查token有效性
 * @date 2018年6月6日 下午3:34:02
 *
 */
@Getter
@Setter
@Slf4j
public class JwtUtil {
	/**
     * JWT 加解密类型
     */
    private static final SignatureAlgorithm JWT_ALG = SignatureAlgorithm.HS256;
    
    @Autowired
    private JwtProperties properties;
    
    /**
     * 使用JWT默认方式，生成加解密密钥
     *
     * @param alg 加解密类型
     * @return
     */
    public static SecretKey generateKey(SignatureAlgorithm alg) {
        return MacProvider.generateKey(alg);
    }

    /**
     * 使用指定密钥生成规则，生成JWT加解密密钥
     *
     * @param alg  加解密类型
     * @param rule 密钥生成规则
     * @return
     */
    public static SecretKey generateKey(SignatureAlgorithm alg, String rule) {
        // 将密钥生成键转换为字节数组
        byte[] bytes = Base64.encodeToByte(rule);
        // 根据指定的加密方式，生成密钥
        return new SecretKeySpec(bytes, alg.getJcaName());
    }

    /**
     * 构建JWT
     *
     * @param alg      jwt 加密算法
     * @param key      jwt 加密密钥
     * @param sub      jwt 面向的用户
     * @param aud      jwt 接收方
     * @param jti      jwt 唯一身份标识
     * @param iss      jwt 签发者
     * @param nbf      jwt 生效日期时间
     * @param duration jwt 有效时间，单位：秒
     * @return JWT字符串
     */
    public String buildJWT(SignatureAlgorithm alg, Key key, String sub, String aud, String jti, String iss, Date nbf,Long expire) {
        // jwt的签发时间
        DateTime iat = DateTime.now();
        // jwt的过期时间，这个过期时间必须要大于签发时间
        DateTime exp = null;
        if (expire != null)
            exp = (nbf == null ? iat.plusSeconds(expire.intValue()) : new DateTime(nbf).plusSeconds(expire.intValue()));

        // 获取JWT字符串
        String compact = Jwts.builder()
                .signWith(alg, key)
                .setSubject(sub)
                .setAudience(aud)
                .setId(jti)
                .setIssuer(iss)
                .setNotBefore(nbf)
                .setIssuedAt(iat.toDate())
                .setExpiration(exp != null ? exp.toDate() : null)
                .compact();

        // 在JWT字符串前添加"Bearer "字符串，用于加入"Authorization"请求头
        return BaseConstant.AUTHORIZATION_VALUE_BEARER_PREFIX + compact;
    }

    
    /**
     * 构建JWT
     * <p>使用 UUID 作为 jti 唯一身份标识</p>
     * <p>JWT有效时间 600 秒，即 10 分钟</p>
     *
     * @param sub jwt 面向的用户
     * @return JWT字符串
     */
    public String buildJWT(String sub) {
        return buildJWT(JWT_ALG, generateKey(JWT_ALG,properties.getSecret()),sub,properties.getAud(), properties.getJti(), properties.getIss(), DateTime.now().toDate(),properties.getExpire());
    }
    
    /**
     * 构建JWT
     * <p>使用 UUID 作为 jti 唯一身份标识</p>
     * <p>JWT有效时间 600 秒，即 10 分钟</p>
     *
     * @param sub jwt 面向的用户
     * @param expire 失效时间
     * @return JWT字符串
     */
    public String buildJWT(String sub,long expire) {
    	return buildJWT(JWT_ALG, generateKey(JWT_ALG,properties.getSecret()),sub,properties.getAud(), properties.getJti(), properties.getIss(), DateTime.now().toDate(),expire);
    }

    /**
     * 解析JWT
     *
     * @param key       jwt 加密密钥
     * @param claimsJws jwt 内容文本
     * @return {@link Jws}
     * @throws Exception
     */
    public Jws<Claims> parseJWT(Key key, String claimsJws) {
        // 移除 JWT 前的"Bearer "字符串
        claimsJws = StringUtils.substringAfter(claimsJws, BaseConstant.AUTHORIZATION_VALUE_BEARER_PREFIX);
        // 解析 JWT 字符串
        return Jwts.parser().setSigningKey(key).parseClaimsJws(claimsJws);
    }
    
    /**
     * 
     * @param claimsJws
     * @return
     */
    public Jws<Claims> parseJWT(String claimsJws) {
    	 SecretKey key = generateKey(JWT_ALG, properties.getSecret());
    	 return parseJWT(key, claimsJws);
    }
    
    /**
     * 获取token的subject
     * @param claimsJws
     * @return
     */
    public String parseSubject(String claimsJws) {
    	return parseJWT(claimsJws).getBody().getSubject();
    }

    /**
     * 校验JWT
     *
     * @param claimsJws jwt 内容文本
     * @return ture or false
     */
    public Boolean checkJWT(String claimsJws) {
        boolean flag = false;
        try {
            SecretKey key = generateKey(JWT_ALG, properties.getSecret());
            // 获取 JWT 的 payload 部分
            flag = (parseJWT(key, claimsJws).getBody() != null);
        } catch (Exception e) {
            log.warn("JWT验证出错，错误原因：{}", e.getMessage());
        }
        return flag;
    }

    /**
     * 校验JWT
     *
     * @param key       jwt 加密密钥
     * @param claimsJws jwt 内容文本
     * @param sub       jwt 面向的用户
     * @return ture or false
     */
    public Boolean checkJWT(Key key, String claimsJws, String sub) {
        boolean flag = false;
        try {
            // 获取 JWT 的 payload 部分
            Claims claims = parseJWT(key, claimsJws).getBody();
            // 比对JWT中的 sub 字段
            flag = claims.getSubject().equals(sub);
        } catch (Exception e) {
            log.warn("JWT验证出错，错误原因：{}", e.getMessage());
        }
        return flag;
    }

    /**
     * 校验JWT
     *
     * @param claimsJws jwt 内容文本
     * @param sub       jwt 面向的用户
     * @return ture or false
     */
    public Boolean checkJWT(String claimsJws, String sub) {
        return checkJWT(generateKey(JWT_ALG, properties.getSecret()), claimsJws, sub);
    }
}
