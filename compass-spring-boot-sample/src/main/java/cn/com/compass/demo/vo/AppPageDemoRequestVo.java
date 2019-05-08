package cn.com.compass.demo.vo;

import cn.com.compass.base.vo.BaseRequestAppPageVo;
import lombok.Data;

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
        this.addOrder("id");
    }

    private String name;
}
