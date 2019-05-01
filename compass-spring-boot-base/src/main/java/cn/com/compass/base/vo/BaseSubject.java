package cn.com.compass.base.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

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
public class BaseSubject extends BaseExtraVo {

    private static final long serialVersionUID = -5471963320229357457L;
    /**
     * 用户Id
     */
    private Serializable userId;
}
