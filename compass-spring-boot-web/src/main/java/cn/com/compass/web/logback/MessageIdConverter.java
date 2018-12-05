package cn.com.compass.web.logback;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

import java.util.UUID;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo logback 日志pattern 转换，添加自定义信息
 * @date 2018/12/4 11:01
 */
public class MessageIdConverter extends ClassicConverter {

    /**
     * 转换
     * @param event
     * @return
     */
    @Override
    public String convert(ILoggingEvent event) {
        return UUID.randomUUID().toString();
    }

}
