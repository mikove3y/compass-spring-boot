package cn.com.compass.base.vo;

import cn.com.compass.base.constant.IBaseBizStatusEnum;
import lombok.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 用户subject
 * @date 2018年6月27日 下午2:34:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseSubject implements Serializable {

    private static final long serialVersionUID = -5471963320229357457L;
    /**
     * 用户Id
     */
    private Serializable userId;
    /**
     * 扩展信息
     */
    private Object extra;

}
