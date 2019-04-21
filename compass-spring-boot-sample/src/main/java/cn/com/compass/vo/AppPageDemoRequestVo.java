package cn.com.compass.vo;

import cn.com.compass.base.vo.BaseRequestAppPageVo;
import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo
 * @date 2019/4/15 17:26
 */
@Data
public class AppPageDemoRequestVo extends BaseRequestAppPageVo {

    /**
     * 添加排序字段
     */
    @Override
    public void addOrder() {
        this.addOrder("id",true);
    }

    private String name;
}
