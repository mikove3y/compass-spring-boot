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
public class ApiGroup {
    /**
     * 分组名
     */
    private String name;
    /**
     * 标签，用英文逗号隔开
     */
    private String[] tags = new String[0];
    /**
     * 分组描述
     */
    private String description;
    /**
     * 基础服务地址 当前框架通过截取window.location.href去拼接接口请求地址host，限制了自定义host的情况，需要优化
     * compass-spring-boot-swagger包 resources/webjars/bycdao-ui/cdao/swaggerbootstrapui.js?v=1.9.0
     * 1、createApiInfoInstance 4315 4317两行代码
     * 2、requestSend 2272 ajax 发送请求代码
     * 3、响应结果 curl拼接优化 buildCurl() 2774行代码
     */
    private String baseServerUrl;
    /**
     * 请求前缀
     */
    private String prefixPath = "";
    /**
     * 是否隐藏
     */
    private boolean hidden = false;
    /**
     * 是否鉴权
     */
    private boolean needAuth = false;
}
