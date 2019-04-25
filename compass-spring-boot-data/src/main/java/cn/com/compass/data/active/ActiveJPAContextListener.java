package cn.com.compass.data.active;

import com.sun.tools.attach.VirtualMachine;
import org.activejpa.enhancer.ActiveJpaAgent;
import org.activejpa.jpa.JPA;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.File;
import java.lang.management.ManagementFactory;
import java.security.CodeSource;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo
 * @date 2019/4/17 9:50
 */
@WebListener
public class ActiveJPAContextListener implements ServletContextListener {

    @Resource
    private EntityManagerFactory entityManagerFactory;

    public ActiveJPAContextListener(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public ActiveJPAContextListener() {

    }

    /**
     * Receives notification that the web application initialization
     * process is starting.
     *
     * <p>All ServletContextListeners are notified of context
     * initialization before any filters or servlets in the web
     * application are initialized.
     *
     * @param sce the ServletContextEvent containing the ServletContext
     *            that is being initialized
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            /**
             * 使用该方法会因为获取不到activejpa-core包的正确路径导致javaagent加载失败,Agent JAR not found or no Agent-Class attribute
             * 原因是:
             * ActiveJpaAgentLoaderImpl#loadAgent()中
             *  CodeSource codeSource = ActiveJpaAgent.class.getProtectionDomain().getCodeSource();
             *  vm.loadAgent(codeSource.getLocation().toURI().getPath(), "");
             *  获取activejpa包方法codeSource.getLocation().toURI().getPath()获取的路劲多了/前缀导致无法正确加载javaagent
             *
             */
//            ActiveJpaAgentLoader.instance().loadAgent();
            // 优化后的方法
            // 参考:http://blog.sina.com.cn/s/blog_605f5b4f01010h6g.html
            String nameOfRunningVM = ManagementFactory.getRuntimeMXBean().getName();
            int p = nameOfRunningVM.indexOf('@');
            String pid = nameOfRunningVM.substring(0, p);
            VirtualMachine vm = VirtualMachine.attach(pid);
            CodeSource codeSource = ActiveJpaAgent.class.getProtectionDomain().getCodeSource();
            String path = codeSource.getLocation().toURI().getPath();
            // 截取真实路径
            File file = new File(path);
            vm.loadAgent(file.getAbsolutePath(), "");
            vm.detach();
            JPA.instance.addPersistenceUnit("default", entityManagerFactory, true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Receives notification that the ServletContext is about to be
     * shut down.
     *
     * <p>All servlets and filters will have been destroyed before any
     * ServletContextListeners are notified of context
     * destruction.
     *
     * @param sce the ServletContextEvent containing the ServletContext
     *            that is being destroyed
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
