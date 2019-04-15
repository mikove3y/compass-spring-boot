package cn.com.compass.swagger.conf;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections.MapUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo
 * @date 2019/3/21 10:31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = SwaggerProperties.prefix)
public class SwaggerProperties {

    public static final String prefix = "swagger";
    /**
     * 是否启用
     */
    private boolean enabled = true;
    /**
     * 从数据库中读取api配置
     */
    private boolean readFromDb = false;
    /**
     * apiInfo
     */
    private Map<String,ApiInfo> apiInfo = new HashMap<>();
    /**
     * apiGroup
     * key: versions
     * value: List<ApiGroup>
     */
    private Map<String,List<ApiGroup>> apiGroup = new HashMap<>();
    /**
     * apiList
     * key: apiGroupName
     * value: List<ApiList>
     */
    private Map<String,List<ApiList>> apiList = new HashMap<>();

    // --------------------------transfer--------------------------------
    /**
     * apiGroupMap
     * key: apiGroupName
     * value: ApiGroup
     */
    private Map<String,ApiGroup> apiGroupMap = new HashMap<>();

    /**
     * 获取groupMap
     * @return
     */
    public Map<String,ApiGroup> getApiGroupMap() {
        if (MapUtils.isEmpty(apiGroupMap)) {
            apiGroup.values().forEach(g->{
                /**
                 * toMap 如果集合对象有重复的key，会报错Duplicate key ....
                 * 可以用 (k1,k2)->k1 来设置，如果有重复的key,则保留key1,舍弃key2
                 */
                apiGroupMap.putAll(g.stream().collect(Collectors.toMap(ApiGroup::getName, a -> a, (k1, k2) -> k1)));
            });
        }
        return apiGroupMap;
    }

}
