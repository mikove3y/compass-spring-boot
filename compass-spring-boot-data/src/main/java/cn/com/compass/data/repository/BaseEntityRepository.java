package cn.com.compass.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import cn.com.compass.base.entity.BaseEntity;
import cn.com.compass.base.service.BaseService;

/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 基类jpa repository
 * @date 2018年6月5日 下午4:02:54
 *
 */
@NoRepositoryBean
public interface BaseEntityRepository<T extends BaseEntity> extends JpaRepository<T, Long>,
		JpaSpecificationExecutor<T>, BaseService<T> {

}
