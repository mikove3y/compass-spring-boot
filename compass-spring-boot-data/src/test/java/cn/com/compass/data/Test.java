package cn.com.compass.data;

import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;
import org.activejpa.enhancer.ActiveJpaAgent;
import org.activejpa.enhancer.ActiveJpaAgentLoader;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.security.CodeSource;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo
 * @date 2019/4/20 22:46
 */
public class Test {

    public static void main(String[] args) throws IOException,
            AttachNotSupportedException, AgentLoadException,
            AgentInitializationException, InterruptedException {
//        ActiveJpaAgentLoader.instance().loadAgent();
        // 获取当前jvm的进程pid
//        String pid = ManagementFactory.getRuntimeMXBean().getName();
//        int indexOf = pid.indexOf('@');
//        if (indexOf > 0) {
//            pid = pid.substring(0, indexOf);
//        }
//        System.out.println("当前JVM Process ID: " + pid);
//        // 获取当前jvm
//        VirtualMachine vm = VirtualMachine.attach(pid);
//        // 当前jvm加载代理jar包,参数1是jar包路径地址,参数2是给jar包代理类传递的参数
//        // -javaagent:E:\maven\reposity\org\activejpa\activejpa-core\1.0.2\activejpa-core-1.0.2.jar
//        vm.loadAgent("/E:/maven/reposity/org/activejpa/activejpa-core/1.0.2/activejpa-core-1.0.2.jar", "");
//        Thread.sleep(1000);
//        vm.detach();
        String nameOfRunningVM = ManagementFactory.getRuntimeMXBean().getName();
        int p = nameOfRunningVM.indexOf('@');
        String pid = nameOfRunningVM.substring(0, p);
        try {
            VirtualMachine vm = VirtualMachine.attach(pid);
            CodeSource codeSource = ActiveJpaAgent.class.getProtectionDomain().getCodeSource();
            String path = codeSource.getLocation().toURI().getPath();
            // 截取真实路径
            File file=new File(path);
            vm.loadAgent(file.getAbsolutePath(), "");
            vm.detach();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
