package cn.com.compass.data.service;

import cn.com.compass.base.vo.BaseRequestAppPageVo;
import cn.com.compass.base.vo.BaseRequestPcPageVo;
import cn.com.compass.base.vo.BaseResponseAppPageVo;
import cn.com.compass.base.vo.BaseResponsePcPageVo;
import cn.com.compass.data.entity.BaseEntity;
import cn.com.compass.data.repository.BaseEntityRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 基类服务实现类免注入实体Repository
 * @date 2018年8月8日 下午5:02:27
 *
 */
@Transactional(readOnly = true)
public class BaseEntityServiceImpl<T extends BaseEntity,PK extends Serializable> implements IBaseEntityService<T,PK>{
	
	@Autowired
	private BaseEntityRepository<T,PK> repository;
	@Autowired
	private JPAQueryFactory jpaQueryFactory;

	/**
	 * 是否存在
	 *
	 * @param id
	 * @return
	 */
	@Override
	public boolean exist(PK id) {
		return repository.exist(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public T saveOne(T entity) {
		return repository.saveOne(entity);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<T> saveBatch(List<T> entities) {
		return repository.saveBatch(entities);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean deleteOne(T entity) {
		return repository.deleteOne(entity);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean deleteById(PK id) {
		return repository.deleteById(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean deleteByIds(List<PK> ids) {
		return repository.deleteByIds(ids);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean deleteBatch(List<T> entities) {
		return repository.deleteBatch(entities);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean deletBySpec(Specification<T> spec) {
		return repository.deletBySpec(spec);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public T updateOne(T entity) {
		return repository.updateOne(entity);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<T> updateBatch(List<T> entities) {
		return repository.updateBatch(entities);
	}

	@Override
	public T findById(PK id) {
		return repository.findById(id);
	}

	@Override
	public List<T> findByIds(List<PK> ids) {
		return repository.findByIds(ids);
	}
	
	@Override
	public T findOneBySpec(Specification<T> spec) {
		return repository.findOneBySpec(spec);
	}

	@Override
	public List<T> findListBySpec(Specification<T> spec) {
		return repository.findListBySpec(spec);
	}

	/**
	 * find all
	 *
	 * @return
	 */
	@Override
	public List<T> findAll() {
		return repository.findAll();
	}

	@Override
	public Long countNum(Specification<T> spec) {
		return repository.count(spec);
	}

	/**
	 * 领域模型类
	 *
	 * @return
	 */
	@Override
	public Class<T> domainClass() {
		return repository.domainClass();
	}

	/**
	 * 实体管理器
	 *
	 * @return
	 */
	@Override
	public EntityManager entityManager() {
		return repository.entityManager();
	}

	/**
	 * JPAQueryFactory
	 *
	 * @return
	 */
	@Override
	public JPAQueryFactory jpaQueryFactory() {
		return jpaQueryFactory;
	}

	@Override
	public BaseResponsePcPageVo<T> findPcPage(BaseRequestPcPageVo pageVo, Specification<T> spec) {
		return repository.findPcPage(pageVo,spec);
	}

	@Override
	public BaseResponseAppPageVo<T> findAppPage(BaseRequestAppPageVo pageVo, Specification<T> spec) {
		return repository.findAppPage(pageVo,spec);
	}

}
