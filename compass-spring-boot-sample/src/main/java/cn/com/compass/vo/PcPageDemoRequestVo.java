package cn.com.compass.vo;

import cn.com.compass.base.vo.BaseRequestPcPageVo;
import lombok.Data;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo
 * @date 2019/4/15 17:27
 */
@Data
public class PcPageDemoRequestVo extends BaseRequestPcPageVo {

    /**
     * 添加转换字段
     */
    @Override
    public void addSource2TargetProperties() {

    }

    /**
     * 添加排序字段
     */
    @Override
    public void addOrder() {
        this.addOrder("id");;
    }

    /**
     * 是否驼峰转下划线，针对mybatis xml 中获取orders数据
     *
     * @return
     */
    @Override
    public boolean camel2Underline() {
        return true;
    }


    private String name;
}
