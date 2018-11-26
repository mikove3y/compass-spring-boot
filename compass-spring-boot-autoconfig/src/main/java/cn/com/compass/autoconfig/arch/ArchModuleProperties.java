package cn.com.compass.autoconfig.arch;

import cn.com.compass.util.DESUtil;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 异构系统属性配置类
 * @date 2018/11/26 10:43
 */
@Data
@ConfigurationProperties(prefix=ArchModuleProperties.prefix)
public class ArchModuleProperties {

    public static final String prefix = "arch";
    /**
     * 默认启用
     */
    private boolean enabled = true;
    /**
     * app账号
     */
    private String appKey;
    /**
     * 加密盐
     */
    private String salt = "43ca26c7411f4ab3bdcb39feb1b634a7";
    /**
     * 加密后密文
     */
    private String masterScrect;
    /**
     * id
     */
    private Long id = Long.MAX_VALUE;
    /**
     * 名称
     */
    private String name = "异构系统开发者";
    /**
     * 异构系统模块
     */
    private Map<String,Module> module = new HashMap<>();
    /**
     * 模块实体
     */
    @Data
    public static class Module {
        /**
         * id
         */
        private Long id;
        /**
         * 模块编码
         */
        private String code;
        /**
         * 模块名称
         */
        private String name;
        /**
         * 模块是否启用
         */
        private Boolean enable = false;

    }

    public static void main(String[] args){
//        System.out.println(UUID.randomUUID().toString().replaceAll("-",""));
        // appKey f6e9ba2c10804498968c027470607ac3
//        System.out.println(UUID.randomUUID().toString().replaceAll("-",""));
        // salt 43ca26c7411f4ab3bdcb39feb1b634a7
//        System.out.println(UUID.randomUUID().toString().replaceAll("-",""));
        // masterSecret 43d9ba6bab2741d3ae25ab44b794dc27 decode
        // 85ZXWBerDpkmdKHGsHZnfvIXHfeMdgBFKENE/qwu79SXAlKqFB4Ugw== encrypt
        try {
            System.out.println(DESUtil.encrypt("43ca26c7411f4ab3bdcb39feb1b634a7","43d9ba6bab2741d3ae25ab44b794dc27"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
