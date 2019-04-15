package cn.com.compass.base.context;

import cn.com.compass.base.vo.BaseSubject;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo BaseSubject 上下文
 * @date 2019/4/12 14:13
 */
public class BaseSubjectContext {

    private static final ThreadLocal<BaseSubject> contextHolder = new ThreadLocal<>();

    public static void setBaseSubject(BaseSubject subject) {
        contextHolder.set(subject);
    }

    public static BaseSubject getBaseSubject() {
        return contextHolder.get();
    }

    public static void clearBaseSubject() {
        contextHolder.remove();
    }
}
