package cn.com.compass.data.repository;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import cn.com.compass.base.entity.BaseEntity;
import cn.com.compass.base.vo.BaseRequestPageVo;
import cn.com.compass.base.vo.Page;

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
public class BaseEntityRepositoryImpl<T extends BaseEntity> extends SimpleJpaRepository<T, Long>
		implements BaseEntityRepository<T> {

	@PersistenceContext
	private EntityManager entityManager;

	private Class<T> entityClass;

	public BaseEntityRepositoryImpl(Class<T> domainClass, EntityManager entityManager) {
		super(domainClass, entityManager);
		this.entityManager = entityManager;
		this.entityClass = domainClass;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.com.compass.base.service.BaseService#saveOne(cn.com.compass.base.entity.
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
	 * @see cn.com.compass.base.service.BaseService#saveBatch(java.util.List)
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
	 * cn.com.compass.base.service.BaseService#deleteOne(cn.com.compass.base.entity.
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
	 * @see cn.com.compass.base.service.BaseService#deleteById(java.io.Serializable)
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean deleteById(Long id) {
		Assert.notNull(id, "deleteById->The given id not be null!");
		this.delete(id);
		return true;
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.com.compass.base.service.BaseService#deleteByIds(java.util.List)
	 */
	@Override
	public boolean deleteByIds(List<Long> ids) {
		Assert.notEmpty(ids, "deleteByIds->The given ids not be empty!");
		this.deleteInBatch(this.findByIds(ids));
		return false;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.com.compass.base.service.BaseService#deletByParams(java.util.Map)
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean deletByParams(Map<String, Object> params) {
		Assert.notEmpty(params, "deletByParams->The given map of params not be empty!");
		List<T> entities = this.findListByParams(params);
		return this.deleteBatch(entities);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.com.compass.base.service.BaseService#deleteBatch(java.util.List)
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
	 * cn.com.compass.base.service.BaseService#updateOne(cn.com.compass.base.entity.
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
	 * @see cn.com.compass.base.service.BaseService#updateOneByParams(java.io.
	 * Serializable, java.util.Map)
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public T updateOneByParams(Long id, Map<String, Object> params) {
		Assert.notEmpty(params, "updateOneByParams->The given map of params not be empty!");
		T t = this.findById(id);
		try {
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				BeanUtilsBean2.getInstance().setProperty(t, entry.getKey(), entry.getValue());
			}
			t = this.updateOne(t);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return t;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.com.compass.base.service.BaseService#updateBatch(java.util.List)
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
	 * @see
	 * cn.com.compass.base.service.BaseService#updateBatchByParams(java.util.List,
	 * java.util.List)
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<T> updateBatchByParams(List<Long> ids, List<Map<String, Object>> params) {
		Assert.notEmpty(params, "updateBatchByParams->The given Collection of params not be empty!");
		List<T> entities = this.findByIds(ids);
		if (CollectionUtils.isNotEmpty(entities) && params.size() == entities.size()) {
			try {
				for (int i = 0; i < entities.size(); i++) {
					T t = entities.get(i);
					Map<String, Object> temp = params.get(i);
					for (Map.Entry<String, Object> entry : temp.entrySet()) {
						BeanUtilsBean2.getInstance().setProperty(t, entry.getKey(), entry.getValue());
					}
				}
				entities = this.updateBatch(entities);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return entities;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.com.compass.base.service.BaseService#findById(java.io.Serializable)
	 */
	@Override
	public T findById(Long id) {
		Assert.notNull(id, "findById->The given id not be null!");
		return this.findOne(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.com.compass.base.service.BaseService#findByIds(java.util.List)
	 */
	@Override
	public List<T> findByIds(List<Long> ids) {
		Assert.notEmpty(ids, "findByIds->The given ids not be empty!");
		return this.findAll(ids);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.com.compass.base.service.BaseService#findListByParams(java.util.Map)
	 */
	@Override
	public List<T> findListByParams(Map<String, Object> params) {
		Assert.notEmpty(params, "findListByParams->The given map of params not be empty!");
		try {
			T t = entityClass.newInstance();
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				BeanUtilsBean2.getInstance().setProperty(t, entry.getKey(), entry.getValue());
			}
			Example<T> example = Example.of(t);
			return this.findAll(example);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.com.compass.base.service.BaseService#findPage(cn.com.compass.base.vo.
	 * BaseRequestPageVo)
	 */
	@Override
	public Page<T> findPage(BaseRequestPageVo pageVo) {
		Assert.notEmpty(pageVo.getConditions(), "findPage->The given pageVo of conditions not be empty!");
		Page<T> result = new Page<>();
		try {
			// build example
			T t = entityClass.newInstance();
			for (Map.Entry<String, Object> entry : pageVo.getConditions().entrySet()) {
				BeanUtilsBean2.getInstance().setProperty(t, entry.getKey(), entry.getValue());
			}
			Example<T> example = Example.of(t);
			// order columns 
			List<String> orderCols = pageVo.getOrders();
			// build orders
			List<Order> orders = new ArrayList<>();
			for (String oc : orderCols) {
				Order order = new Order(pageVo.getIsAsc() ? Direction.ASC : Direction.DESC, oc);
				orders.add(order);
			}
			// build sort
			Sort sort = new Sort(orders);
			// do query page
			Pageable pageRequest = new PageRequest(pageVo.getPageNo() - 1, pageVo.getPageSize(), sort);
			org.springframework.data.domain.Page<T> page = this.findAll(example,pageRequest);
			// build result
			result.setTotal(page.getTotalElements());
			result.setRecords(page.getContent());
			result.setCurrent(page.getNumber());
			result.setSize(page.getSize());
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return result;
	}

}
