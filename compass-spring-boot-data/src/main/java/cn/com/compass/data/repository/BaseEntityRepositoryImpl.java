package cn.com.compass.data.repository;

import cn.com.compass.base.vo.BaseRequestAppPageVo;
import cn.com.compass.base.vo.BaseRequestPcPageVo;
import cn.com.compass.base.vo.BaseResponseAppPageVo;
import cn.com.compass.base.vo.BaseResponsePcPageVo;
import cn.com.compass.data.annotation.LogicDelete;
import cn.com.compass.data.entity.BaseEntity;
import cn.com.compass.data.util.PageTransformUtil;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo hibernate jpa use for add、selete、update、simple-select and entity-table auto build </br>
 * 		 mybatis plus use for complex-select actions </br>
 * @date 2018年6月6日 下午3:54:06
 *
 */
@Transactional(readOnly = true)
public class BaseEntityRepositoryImpl<T extends BaseEntity,PK extends Serializable> extends SimpleJpaRepository<T, PK>
		implements BaseEntityRepository<T,PK> {

	@PersistenceContext
	private EntityManager entityManager;

	private Class<T> entityClass;
	
	private boolean isLogicDelete = false;
	
	@Value("${spring.jpa.openLogicDelete:false}")
	private boolean openLogicDelete;

	public BaseEntityRepositoryImpl(Class<T> domainClass, EntityManager entityManager) {
		super(domainClass, entityManager);
		this.entityManager = entityManager;
		this.entityClass = domainClass;
		// 判断是否逻辑删除
		Field[] fields = entityClass.getFields();
		for(Field f : fields) {
			f.setAccessible(true);
			LogicDelete ld = f.getAnnotation(LogicDelete.class);
			this.isLogicDelete = ld != null;
			if(this.isLogicDelete)break;
		}
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
		if(this.isLogicDelete&&this.openLogicDelete) {
			this.updateOne(entity);
		}else {
			this.delete(entity);
		}
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
		if(this.isLogicDelete&&this.openLogicDelete) {
			this.updateOne(this.findById(id));
		}else {
			this.delete(id);
		}
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
		List<T> ts = this.findByIds(ids);
		if(this.isLogicDelete&&this.openLogicDelete) {
			this.updateBatch(ts);
		}else {
			this.deleteInBatch(ts);
		}
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
		if(this.isLogicDelete&&this.openLogicDelete) {
			this.updateBatch(entities);
		}else {
			this.deleteBatch(entities);
		}
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
		if(this.isLogicDelete&&this.openLogicDelete) {
			this.updateBatch(entities);
		}else {
			this.delete(entities);
		}
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
	public T findById(PK id) {
		Assert.notNull(id, "findById->The given id not be null!");
		return this.findOne(id);
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.com.compass.data.service.IBaseEntityService#findOneByParams(java.util.Map)
	 */
	@Override
	public T findOneBySpec(Specification<T> spec) {
		Assert.notNull(spec, "findOneByParams->The given spec not be null!");
		return this.findOne(spec);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.com.compass.data.service.IBaseEntityService#findByIds(java.util.List)
	 */
	@Override
	public List<T> findByIds(List<PK> ids) {
		Assert.notEmpty(ids, "findByIds->The given ids not be empty!");
		return this.findAll(ids);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.com.compass.data.service.IBaseEntityService#findListByParams(java.util.Map)
	 */
	@Override
	public List<T> findListBySpec(Specification<T> spec) {
		Assert.notNull(spec, "findListByParams->The given spec not be null!");
		return this.findAll(spec);
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
				pageVo.getPageSize(), this.buildSort(pageVo.orders()));
		// transform jpa page to frameWork pc-page
		return PageTransformUtil.transformJpaPage2PcPage(this.findAll(spec, pageRequest));
	}

	/**
	 * 构造排序
	 * @param orderMap
	 * @return
	 */
	private Sort buildSort(Map<String,Boolean> orderMap){
		List<Order> orders = new ArrayList<>();
		if(MapUtils.isNotEmpty(orderMap)){
			for(Map.Entry<String,Boolean> en : orderMap.entrySet()){
				Order order = new Order((en.getValue() ? Direction.ASC : Direction.DESC), en.getKey());
				orders.add(order);
			}
		}
		// build sort
		return new Sort(orders);
	}
	
	@Override
	public BaseResponseAppPageVo<T> findAppPage(BaseRequestAppPageVo pageVo, Specification<T> spec) {
		Assert.noNullElements(new Object[] { pageVo, spec }, "findAppPage->The given pageVo & spec not be null!");
		// do query page
		Pageable pageRequest = new PageRequest(0, pageVo.getPageSize(), this.buildSort(pageVo.orders()));
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

}
