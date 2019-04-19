package cn.com.compass.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo
 * @date 2019/4/15 17:56
 */
@Data
public class DemoDTO implements Serializable {

    private Long id;

    private String name;

    private String business;

    private Date time;
}
