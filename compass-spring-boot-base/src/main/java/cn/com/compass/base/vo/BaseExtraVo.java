package cn.com.compass.base.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo
 * @date 2019/5/1 23:24
 */
@Data
public class BaseExtraVo implements Serializable {

    /**
     * 扩展信息
     */
    private Map<String,Object> extra = new HashMap<>();

    /**
     * 添加扩展信息
     * @param extra
     * @return
     */
    public BaseExtraVo addExtra(Map<String,Object> extra){
        this.extra.putAll(extra);
        return this;
    }

    /**
     * 添加扩展信息
     * @param key
     * @param value
     * @return
     */
    public BaseExtraVo addExtra(String key,Object value){
        this.extra.put(key,value);
        return this;
    }
}
