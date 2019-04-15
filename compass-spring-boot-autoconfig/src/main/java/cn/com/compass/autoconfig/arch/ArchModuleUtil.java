package cn.com.compass.autoconfig.arch;

import cn.com.compass.util.DESUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 异构系统工具类
 * @date 2018/11/26 15:14
 */
@Slf4j
public class ArchModuleUtil {
    @Autowired
    private ArchModuleProperties properties;

    /**
     * 获取开发者账号信息
     * @return
     */
    public ArchModuleProperties getProperties(){
        return properties;
    }

    /**
     * 检查账号是否正确
     * @param appKey
     * @param masterSecret
     * @return
     */
    public boolean checkAccount(String appKey,String masterSecret){
        if(!properties.getAppKey().equals(appKey)){
            // 账号错误
            return false;
        }
        try {
           String encryptMasterSecret = DESUtil.encrypt(properties.getSalt(),masterSecret);
           if(!properties.getMasterScrect().equals(encryptMasterSecret)){
               // 密码错误
               return false;
           }
        } catch (Exception e) {
            log.error("checkAccount,error:{}",e);
            return false;
        }
        return true;
    }

    /**
     * 获取异构系统信息
     * @param archModuleCode
     * @return
     */
    public ArchModuleProperties.Module getModuleInfo(String archModuleCode){
        Map<String,ArchModuleProperties.Module> map = properties.getModule();
        if(MapUtils.isNotEmpty(map)){
            List<ArchModuleProperties.Module>  modules = map.values().stream().filter(m->m.getCode().equalsIgnoreCase(archModuleCode)).collect(Collectors.toList());
            if(CollectionUtils.isNotEmpty(modules))
                return modules.get(0);
        }
        return null;
    }

    /**
     * 获取所有的异构系统模块
     * @return
     */
    public List<ArchModuleProperties.Module> getAllModules(){
        Map<String,ArchModuleProperties.Module> map = properties.getModule();
        if(MapUtils.isNotEmpty(map)){
            List<ArchModuleProperties.Module> modules = new ArrayList<>();
            modules.addAll(map.values());
            return modules;
        }
        return null;
    }

}
