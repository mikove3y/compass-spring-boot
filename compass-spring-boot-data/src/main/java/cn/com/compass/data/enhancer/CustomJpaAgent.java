package cn.com.compass.data.enhancer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.instrument.Instrumentation;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo
 * @date 2019/5/5 21:11
 */
public class CustomJpaAgent {

    private static final Logger logger = LoggerFactory.getLogger(CustomJpaAgent.class);

    public static void premain(String args, Instrumentation inst) throws Exception {
        logger.trace("premain method invoked with args: {} and inst: {}", args, inst);
        inst.addTransformer((new BaseEntityClassEnhancer(inst)).getTransformer(), true);
    }

    public static void agentmain(String args, Instrumentation inst) throws Exception {
        logger.trace("agentmain method invoked with args: {} and inst: {}", args, inst);
        inst.addTransformer((new BaseEntityClassEnhancer(inst)).getTransformer(), true);
    }
}
