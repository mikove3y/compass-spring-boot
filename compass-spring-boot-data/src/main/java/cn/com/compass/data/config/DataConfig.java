package cn.com.compass.data.config;

import cn.com.compass.data.enhancer.JPAContextListener;
import cn.com.compass.data.util.EntityClassLoader;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl;
import org.hibernate.jpa.boot.internal.PersistenceUnitInfoDescriptor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitManager;
import org.springframework.orm.jpa.persistenceunit.SmartPersistenceUnitInfo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.spi.PersistenceUnitInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

//    private ClassLoader customClassLoader = new EntityClassLoader(org.springframework.util.ClassUtils.getDefaultClassLoader());

    /**
     * 设置自定义ClassLoader
     * @param configurableListableBeanFactory
     */
//    @Autowired
//    public DataConfig(ConfigurableListableBeanFactory configurableListableBeanFactory){
//        configurableListableBeanFactory.setBeanClassLoader(customClassLoader);
//    }

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
    public ServletListenerRegistrationBean<JPAContextListener> ActiveJPAContextListener(EntityManagerFactory entityManagerFactory) {
        ServletListenerRegistrationBean<JPAContextListener> listener = new ServletListenerRegistrationBean<>(new JPAContextListener(entityManagerFactory));
        return listener;
    }

    /**
     * 覆盖 org.springframework.boot.autoconfigure.orm.jpa.JpaBaseConfiguration#entityManagerFactoryBuilder(org.springframework.orm.jpa.JpaVendorAdapter, org.springframework.beans.factory.ObjectProvider) 方法，
     * 加入自定义entity类加载器
     *
     * @param jpaVendorAdapter
     * @param persistenceUnitManager
     * @param properties
     * @return
     */
//    @Bean
//    @Primary
//    @ConditionalOnMissingBean
//    public EntityManagerFactoryBuilder entityManagerFactoryBuilder(
//            JpaVendorAdapter jpaVendorAdapter,
//            ObjectProvider<PersistenceUnitManager> persistenceUnitManager, JpaProperties properties) {
//        EntityManagerFactoryBuilder builder = new EntityManagerFactoryBuilder(
//                jpaVendorAdapter, properties.getProperties(),
//                persistenceUnitManager.getIfAvailable());
//        builder.setCallback(getVendorCallback());
//        return builder;
//    }
//
//    protected EntityManagerFactoryBuilder.EntityManagerFactoryBeanCallback getVendorCallback() {
//        return new EntityManagerFactoryBuilder.EntityManagerFactoryBeanCallback() {
//            @Override
//            public void execute(LocalContainerEntityManagerFactoryBean factory) {
//                // do something
//                factory.setBeanClassLoader(customClassLoader);
//                // 自定义ClassLoader
////                factory.setPersistenceProviderClass(CustomHibernatePersistenceProvider.class);   //java.lang.NoSuchMethodException: cn.com.compass.data.config.DataConfig$CustomHibernatePersistenceProvider.<init>()
//                factory.setPersistenceProvider(new CustomHibernatePersistenceProvider());
//            }
//        };
//    }
//
//    /**
//     * 参考 org.springframework.orm.jpa.vendor.SpringHibernateJpaPersistenceProvider
//     * 自定义
//     */
//    public class CustomHibernatePersistenceProvider extends HibernatePersistenceProvider {
//
//        public CustomHibernatePersistenceProvider(){
//
//        }
//
//        @Override
//        @SuppressWarnings("rawtypes")
//        public EntityManagerFactory createContainerEntityManagerFactory(PersistenceUnitInfo info, Map properties) {
//            final List<String> mergedClassesAndPackages = new ArrayList<String>(info.getManagedClassNames());
//            if (info instanceof SmartPersistenceUnitInfo) {
//                mergedClassesAndPackages.addAll(((SmartPersistenceUnitInfo) info).getManagedPackages());
//            }
//            return new EntityManagerFactoryBuilderImpl(
//                    new PersistenceUnitInfoDescriptor(info) {
//                        @Override
//                        public List<String> getManagedClassNames() {
//                            return mergedClassesAndPackages;
//                        }
//
//                        // 重新设置 ClassLoader
//                        @Override
//                        public ClassLoader getClassLoader() {
//                            return customClassLoader;
//                        }
//
//                        // 重新设置TempClassLoader
//                        @Override
//                        public ClassLoader getTempClassLoader() {
//                            return customClassLoader;
//                        }
//                    }, properties).build();
//        }
//    }

}
