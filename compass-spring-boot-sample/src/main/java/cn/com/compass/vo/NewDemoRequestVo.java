package cn.com.compass.vo;

import cn.com.compass.base.vo.BaseDataX;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo
 * @date 2019/4/15 17:26
 */
@Data
public class NewDemoRequestVo extends BaseDataX {

    public NewDemoRequestVo(){
        /**
         * 初始化数据对照
         */
        Map<String,String> temp = new HashMap<>();
        temp.put("nameX","name");
        this.addSource2TargetProperties(temp);
    }

    private String nameX;

    private String business;

    private Date time;

}
