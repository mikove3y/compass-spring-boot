/**
 * 
 */
package cn.com.compass.autoconfig.yml;

import org.springframework.boot.env.PropertySourcesLoader;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.DefaultPropertySourceFactory;
import org.springframework.core.io.support.EncodedResource;

import java.io.IOException;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 自定义yml文件读取工厂
 * @date 2018年10月19日 下午11:57:41
 * 
 */
public class YamlPropertySourceFactory extends DefaultPropertySourceFactory {

	@Override
    public PropertySource<?> createPropertySource(String name, EncodedResource resource) throws IOException {
        return name != null ? new PropertySourcesLoader().load(resource.getResource(), name, null) : new PropertySourcesLoader().load(
                resource.getResource(), getNameForResource(resource.getResource()), null);
    }

    private static String getNameForResource(Resource resource) {
        String name = resource.getDescription();
        if (!org.springframework.util.StringUtils.hasText(name)) {
            name = resource.getClass().getSimpleName() + "@" + System.identityHashCode(resource);
        }
        return name;
    }

}
