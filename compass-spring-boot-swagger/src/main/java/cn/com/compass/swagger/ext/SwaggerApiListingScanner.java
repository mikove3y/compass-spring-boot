package cn.com.compass.swagger.ext;

import cn.com.compass.swagger.conf.ApiGroup;
import cn.com.compass.swagger.conf.ApiList;
import com.google.common.collect.Multimap;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import springfox.documentation.builders.ApiListingBuilder;
import springfox.documentation.schema.Model;
import springfox.documentation.service.ApiDescription;
import springfox.documentation.service.ApiListing;
import springfox.documentation.spring.web.plugins.DocumentationPluginsManager;
import springfox.documentation.spring.web.scanners.ApiDescriptionReader;
import springfox.documentation.spring.web.scanners.ApiListingScanner;
import springfox.documentation.spring.web.scanners.ApiListingScanningContext;
import springfox.documentation.spring.web.scanners.ApiModelReader;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo
 * @date 2019/3/21 15:15
 */
public class SwaggerApiListingScanner extends ApiListingScanner {


    @Autowired
    private SwaggerPropertyService propertyService;


    @Autowired
    public SwaggerApiListingScanner(ApiDescriptionReader apiDescriptionReader, ApiModelReader apiModelReader, DocumentationPluginsManager pluginsManager) {
        super(apiDescriptionReader, apiModelReader, pluginsManager);
    }

    /**
     *
     * @param context
     * @return
     */
    @Override
    public Multimap<String, ApiListing> scan(ApiListingScanningContext context) {
        final Multimap<String, ApiListing> def = super.scan(context);
        def.clear();// 清空默认的basic-controller分组
        Map<String,List<ApiList>> apis = propertyService.getProperties().getApiList();
        Map<String, ApiGroup> groups = propertyService.getProperties().getApiGroupMap();
        if (MapUtils.isNotEmpty(apis)&&MapUtils.isNotEmpty(groups)) {
            for(Map.Entry<String,List<ApiList>> en : apis.entrySet()){
                List<ApiList> api = en.getValue();
                if(CollectionUtils.isEmpty(api))continue;
                ApiGroup group = groups.get(en.getKey());
                if(group==null)continue;
                api.sort(new Comparator<ApiList>() {
                    @Override
                    public int compare(ApiList o1, ApiList o2) {
                        return o1.getSortNum()>o2.getSortNum()?0:-1;
                    }
                });
                for(ApiList a : api){
                    List<ApiDescription> apiDescriptions = SwaggerApiDescriptionReaderHelper.read(a,group);
                    if(CollectionUtils.isEmpty(apiDescriptions))continue;
                    Map<String, Model> modelMap = SwaggerApiModelReaderHelper.read(a);
                    def.put(group.getName(), new ApiListingBuilder(context.getDocumentationContext().getApiDescriptionOrdering())
                            .apis(apiDescriptions)
                            .description(group.getDescription())
                            .models(modelMap)
                            .build());
                }
            }
        }
        return def;
    }
}
