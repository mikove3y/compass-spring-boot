package cn.com.compass.swagger.conf;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo
 * @date 2019/3/26 10:38
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiList {
    /**
     * 标签
     */
    private String[] tags = new String[0];
    /**
     * client扫描路径
     */
    private String[] scanClientPath = new String[0];
    /**
     * model扫描路径
     */
    private String[] scanModelPath = new String[0];
    /**
     * 顺序号
     */
    private Integer sortNum = 0;
}
