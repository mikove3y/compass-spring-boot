package cn.com.compass.data.listner;

import org.activejpa.enhancer.ActiveJpaAgentLoader;
import org.activejpa.jpa.JPA;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

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

    public ActiveJPAContextListener(EntityManagerFactory entityManagerFactory){
        this.entityManagerFactory = entityManagerFactory;
    }

    public ActiveJPAContextListener(){

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
//            ServletContext context = sce.getServletContext();
            ActiveJpaAgentLoader.instance().loadAgent();
            JPA.instance.addPersistenceUnit("default", entityManagerFactory, true);
        } catch (Exception e) {
            throw e;
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
