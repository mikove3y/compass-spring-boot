package cn.com.compass.data.repository;

import cn.com.compass.base.vo.BaseRequestAppPageVo;
import cn.com.compass.base.vo.BaseRequestPcPageVo;
import cn.com.compass.base.vo.BaseResponseAppPageVo;
import cn.com.compass.base.vo.BaseResponsePcPageVo;
import cn.com.compass.data.entity.BaseEntity;
import cn.com.compass.data.util.PageTransformUtil;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.QueryDslJpaRepository;
import org.springframework.data.querydsl.SimpleEntityPathResolver;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo hibernate jpa use for add、selete、update、simple-select and entity-table auto build </br>
 * 		 mybatis plus use for complex-select actions </br>
 * 		 since v1.3.0 extra: queryDsl Projections for DTO to Entity </br>
 * @date 2018年6月6日 下午3:54:06
 *
 */
@Transactional(readOnly = true)
public class BaseEntityRepositoryImpl<T extends BaseEntity,PK extends Serializable> extends QueryDslJpaRepository<T, PK>
		implements BaseEntityRepository<T,PK> {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private JPAQueryFactory jpaQueryFactory;

	private Class<T> entityClass;
	
	/**
	 * Creates a new {@link QueryDslJpaRepository} from the given domain class and {@link EntityManager}. This will use
	 * the {@link SimpleEntityPathResolver} to translate the given domain class into an {@link EntityPath}.
	 *
	 * @param entityInformation must not be {@literal null}.
	 * @param entityManager     must not be {@literal null}.
	 */
	public BaseEntityRepositoryImpl(JpaEntityInformation<T, PK> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
		this.entityClass = entityInformation.getJavaType();
		this.entityManager = entityManager;
	}

//	public BaseEntityRepositoryImpl(Class<T> domainClass, EntityManager entityManager) {
//		super(domainClass,entityManager);
//		this.entityManager = entityManager;
//		this.entityClass = domainClass;
//	}


	/**
	 * 是否存在
	 *
	 * @param id
	 * @return
	 */
	@Override
	public boolean exist(PK id) {
		return this.exists(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.com.compass.data.service.IBaseEntityService#saveOne(cn.com.compass.base.entity.
	 * BaseEntity)
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public T saveOne(T entity) {
		Assert.notNull(entity, "saveOne->The given entity not be null!");
		return this.save(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.com.compass.data.service.IBaseEntityService#saveBatch(java.util.List)
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<T> saveBatch(List<T> entities) {
		Assert.notEmpty(entities, "saveBatch->The given entities not be null!");
		return this.save(entities);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.com.compass.data.service.IBaseEntityService#deleteOne(cn.com.compass.base.entity.
	 * BaseEntity)
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean deleteOne(T entity) {
		Assert.notNull(entity, "deleteOne->The given entity not be null!");
		this.delete(entity);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.com.compass.data.service.IBaseEntityService#deleteById(java.io.Serializable)
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean deleteById(PK id) {
		Assert.notNull(id, "deleteById->The given id not be null!");
		this.delete(id);
		return true;
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.com.compass.data.service.IBaseEntityService#deleteByIds(java.util.List)
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean deleteByIds(List<PK> ids) {
		Assert.notEmpty(ids, "deleteByIds->The given ids not be empty!");
		List<T> entities = this.findByIds(ids);
		this.deleteInBatch(entities);
		return true;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.com.compass.data.service.IBaseEntityService#deletByParams(java.util.Map)
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean deletBySpec(Specification<T> spec) {
		Assert.notNull(spec, "deletBySpec->The given spec not be null!");
		List<T> entities = this.findListBySpec(spec);
		this.deleteBatch(entities);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.com.compass.data.service.IBaseEntityService#deleteBatch(java.util.List)
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean deleteBatch(List<T> entities) {
		Assert.notEmpty(entities, "deleteBatch->The given entities not be null!");
		this.delete(entities);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.com.compass.data.service.IBaseEntityService#updateOne(cn.com.compass.base.entity.
	 * BaseEntity)
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public T updateOne(T entity) {
		Assert.notNull(entity, "updateOne->The given entity not be null!");
		return this.save(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.com.compass.data.service.IBaseEntityService#updateBatch(java.util.List)
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<T> updateBatch(List<T> entities) {
		Assert.notEmpty(entities, "updateBatch->The given entities not be empty!");
		return this.saveBatch(entities);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.com.compass.data.service.IBaseEntityService#findById(java.io.Serializable)
	 */
	@Override
	public Optional<T> findById(PK id) {
		Assert.notNull(id, "findById->The given id not be null!");
		return Optional.ofNullable(this.findOne(id));
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.com.compass.data.service.IBaseEntityService#findOneByParams(java.util.Map)
	 */
	@Override
	public Optional<T> findOneBySpec(Specification<T> spec) {
		Assert.notNull(spec, "findOneByParams->The given spec not be null!");
		return Optional.ofNullable(this.findOne(spec));
	}

	/**
	 * find one by the domainClass property
	 *
	 * @param propertyName
	 * @param propertyValue
	 * @return
	 */
	@Override
	public Optional<T> findByProperty(String propertyName, Object propertyValue) {
		Assert.notNull(propertyName,"findByProperty->The given propertyName not be null");
		Assert.notNull(propertyValue,"findByProperty->The given propertyValue not be null");
		return Optional.empty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.com.compass.data.service.IBaseEntityService#findByIds(java.util.List)
	 */
	@Override
	public List<T> findByIds(List<PK> ids,Sort... sort) {
		Assert.notEmpty(ids, "findByIds->The given ids not be empty!");
		return this.findAll(ids);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.com.compass.data.service.IBaseEntityService#findListByParams(java.util.Map)
	 */
	@Override
	public List<T> findListBySpec(Specification<T> spec,Sort... sort) {
		Assert.notNull(spec, "findListByParams->The given spec not be null!");
		return this.findAll(spec);
	}

	/**
	 * find list by domainClass property ,sort or not
	 *
	 * @param propertyName
	 * @param propertyValue
	 * @param sort
	 * @return
	 */
	@Override
	public List<T> findByProperty(String propertyName, Object propertyValue, Sort... sort) {
		Assert.notNull(propertyName,"findByProperty->The given propertyName not be null");
		Assert.notNull(propertyValue,"findByProperty->The given propertyValue not be null");
		return null;
	}

	/**
	 * find all
	 *
	 * @param sort
	 * @return
	 */
	@Override
	public List<T> findAll(Sort... sort) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.com.compass.data.service.IBaseEntityService#findPage(cn.com.compass.base.vo.
	 * BaseRequestPageVo)
	 */
	@Override
	public BaseResponsePcPageVo<T> findPcPage(BaseRequestPcPageVo pageVo, Specification<T> spec) {
		Assert.noNullElements(new Object[] { pageVo, spec }, "findPcPage->The given pageVo & spec not be null!");
		// do query page
		Pageable pageRequest = new PageRequest(pageVo.getPageNo() >= 1 ? pageVo.getPageNo() - 1 : 0,
				pageVo.getPageSize(), this.buildSort(pageVo.orders(),pageVo.isAsc()));
		// transform jpa page to frameWork pc-page
		return PageTransformUtil.transformJpaPage2PcPage(this.findAll(spec, pageRequest));
	}

	/**
	 * 构造排序
	 * @param orders
	 * @param isAsc
	 * @return
	 */
	private Sort buildSort(List<String> orders,boolean isAsc){
		List<Order> ors = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(orders)){
			orders.forEach(o->{
				Order or = new Order((isAsc ? Direction.ASC : Direction.DESC), o);
				ors.add(or);
			});
		}
		// build sort
		return new Sort(ors);
	}
	
	@Override
	public BaseResponseAppPageVo<T> findAppPage(BaseRequestAppPageVo pageVo, Specification<T> spec) {
		Assert.noNullElements(new Object[] { pageVo, spec }, "findAppPage->The given pageVo & spec not be null!");
		// do query page
		Pageable pageRequest = new PageRequest(0, pageVo.getPageSize(), this.buildSort(pageVo.orders(),pageVo.isAsc()));
		// transform jpa page to frameWork app-page
		return PageTransformUtil.transformJpaPage2AppPage(this.findAll(spec, pageRequest),pageVo);
	}

	/**
	 * count by spec
	 *
	 * @param spec
	 * @return
	 */
	@Override
	public Long countNum(Specification<T> spec) {
		return this.count(spec);
	}


	@Override
	public Class<T> domainClass() {
		return entityClass;
	}

	@Override
	public EntityManager entityManager() {
		return entityManager;
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

}
