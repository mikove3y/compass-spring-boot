package cn.com.compass.swagger.conf;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo
 * @date 2019/3/26 10:38
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiInfo {
    /**
     * 标题
     */
    private String title;
    /**
     * 描述
     */
    private String description;
    /**
     * 服务地址
     */
    private String termsOfServiceUrl;
    /**
     * 联系人
     */
    private String contactName;
    /**
     * 联系人网站
     */
    private String contactUrl;
    /**
     * 联系人email
     */
    private String contactEmail;
    /**
     * 授权类型
     */
    private String license;
    /**
     * 授权地址
     */
    private String licenseUrl;

}
