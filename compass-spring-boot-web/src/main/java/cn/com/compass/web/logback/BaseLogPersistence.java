package cn.com.compass.web.logback;

import cn.com.compass.base.vo.BaseLogVo;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo
 * @date 2019/4/12 14:40
 */
public interface BaseLogPersistence {

    public Object persistence(BaseLogVo log);
}
