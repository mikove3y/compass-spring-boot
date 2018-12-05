package cn.com.compass.web.logback;

import ch.qos.logback.classic.PatternLayout;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 消息Id log模式布局
 * @date 2018/12/4 20:12
 */
public class MessageIdPatternLayout extends PatternLayout {
    /**
     * 注册%msgId的两种方式:
     * 1、defaultConverterMap.put
     * logback.xml添加<layout class="com.cj.log.MyPatternLayout"> <pattern>%msgId <pattern/> </layout>
     * 2、logback.xml添加<conversionRule conversionWord="msgId" converterClass="cn.com.compass.web.logback.MessageIdConverter" />
     * 并加入<pattern>%msgId <pattern/>
     */
    static {
        defaultConverterMap.put("msgId",MessageIdConverter.class.getName());
    }
}
