package cn.com.compass.data.config;

import cn.com.compass.data.active.ActiveJPAContextListener;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo
 * @date 2019/4/12 13:06
 */
@Configuration
//@ServletComponentScan // 扫描WebListener
public class DataConfig {

    /**
     * 初始化JPAQueryFactory
     *
     * @param entityManager
     * @return
     */
    @Bean
    @Primary
    public JPAQueryFactory jpaQueryFactory(EntityManager entityManager) {
        return new JPAQueryFactory(entityManager);
    }

    /**
     * ActiveJPA 监听初始化
     *
     * @return
     */
    @Bean
    public ServletListenerRegistrationBean<ActiveJPAContextListener> ActiveJPAContextListener(EntityManagerFactory entityManagerFactory) {
        ServletListenerRegistrationBean<ActiveJPAContextListener> listener = new ServletListenerRegistrationBean<>(new ActiveJPAContextListener(entityManagerFactory));
        return listener;
    }
}
