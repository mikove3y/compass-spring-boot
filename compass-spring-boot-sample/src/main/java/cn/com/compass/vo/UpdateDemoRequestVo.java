package cn.com.compass.vo;

import cn.com.compass.base.vo.BaseDataX;
import lombok.Data;

import java.util.Date;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo
 * @date 2019/4/15 17:26
 */
@Data
public class UpdateDemoRequestVo extends BaseDataX {

    private String nameX;

    private String business;

    private Date time;

    /**
     * 添加转换字段
     */
    @Override
    public void addSource2TargetProperties() {
        this.addSource2TargetProperties("nameX","name");
    }

}
