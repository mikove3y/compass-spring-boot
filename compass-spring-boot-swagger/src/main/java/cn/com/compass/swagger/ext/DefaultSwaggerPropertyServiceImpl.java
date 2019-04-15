package cn.com.compass.swagger.ext;

import cn.com.compass.swagger.conf.SwaggerProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo
 * @date 2019/4/12 10:48
 */
@Service
@Slf4j
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)// 单例模式
public class DefaultSwaggerPropertyServiceImpl implements SwaggerPropertyService {

    /**
     * 是否已经读取过
     */
    private boolean isRead = false;

    /**
     * 配置文件
     */
    @Autowired
    private SwaggerProperties properties;

    /**
     * 获取swagger配置文件
     *
     * @return
     */
    @Override
    public SwaggerProperties getProperties() {
        if(!isRead){
            isRead = true;
            return properties;
        }
        return null;
    }
}
