package cn.com.compass.vo;

import cn.com.compass.base.vo.BaseRequestPcPageVo;
import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo
 * @date 2019/4/15 17:27
 */
@Data
public class PcPageDemoRequestVo extends BaseRequestPcPageVo {

    public PcPageDemoRequestVo(){
        Map<String,Boolean> map = new LinkedHashMap<>();
        map.put("id",true);
        this.addOrder(map);
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
