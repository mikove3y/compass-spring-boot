package cn.com.compass.data.repository;

import cn.com.compass.data.util.EntityClassLoader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaPersistableEntityInformation;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo BaseEntityRepository工厂类
 * @date 2018年6月6日 下午3:53:37
 */
public class BaseEntityRepositoryFactoryBean<R extends JpaRepository<T, I>, T, I extends Serializable>
        extends JpaRepositoryFactoryBean<R, T, I> {


    public BaseEntityRepositoryFactoryBean(Class<? extends R> repositoryInterface) {
        super(repositoryInterface);
    }

    @SuppressWarnings("rawtypes")
    @Override
    protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {
        RepositoryFactorySupport support = new BaseEntityRepositoryFactory(entityManager);
//        support.addQueryCreationListener();
//        support.addRepositoryProxyPostProcessor(); // 增加一个事件发布 如果是策略删除那么保存一份数据到备份表中
        return support;
    }

    private class BaseEntityRepositoryFactory<T, I extends Serializable> extends JpaRepositoryFactory {

        public BaseEntityRepositoryFactory(EntityManager entityManager) {
            super(entityManager);
        }

        @SuppressWarnings({"rawtypes", "unchecked", "hiding"})
        @Override
        protected <T, ID extends Serializable> SimpleJpaRepository<?, ?> getTargetRepository(
                RepositoryInformation information, EntityManager entityManager) {
            JpaPersistableEntityInformation entityInformation = new JpaPersistableEntityInformation(information.getDomainType(), entityManager.getMetamodel());
            return new BaseEntityRepositoryImpl(entityInformation, entityManager);
        }

        @Override
        protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
            return BaseEntityRepository.class;
        }

    }

}
