package cn.com.compass.swagger.ext;

import cn.com.compass.swagger.conf.SwaggerProperties;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 获取swagger配置文件
 * @date 2019/4/12 10:46
 */
public interface SwaggerPropertyService {
    /**
     * 获取swagger配置文件
     * @return
     */
    public SwaggerProperties getProperties();
}
