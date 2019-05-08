package cn.com.compass.demo.dto;

import cn.com.compass.data.dto.BaseDTO;
import lombok.Data;

import java.util.Date;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo
 * @date 2019/4/15 17:56
 */
@Data
public class DemoDTO extends BaseDTO<Long> {

    private String name;

    private String business;

    private Date time;

    private Date createTime;
}
