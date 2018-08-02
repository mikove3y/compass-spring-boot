package cn.com.compass.data.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import cn.com.compass.base.constant.BaseConstant;
import cn.com.compass.base.entity.BaseEntity;
import cn.com.compass.base.exception.BaseException;
import cn.com.compass.base.vo.BaseRequestAppPageVo;
import cn.com.compass.base.vo.BaseRequestPcPageVo;
import cn.com.compass.base.vo.Page;
import cn.com.compass.util.DataXUtil;
import cn.com.compass.util.JacksonUtil;

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
	@Transactional(rollbackFor = Exception.class)
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
		try {
			T ot = this.findById(id);
			T nt = entityClass.newInstance();
			nt.setId(ot.getId());
			BeanUtilsBean2.getInstance().copyProperties(nt, params);
			return this.updateOne(nt);
		} catch (Exception e) {
			throw new BaseException(BaseConstant.ILLEGAL_ARGUMENT, e);
		}
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
		Assert.notEmpty(ids, "updateBatchByParams->The given Collection of ids not be empty!");
		List<T> rs = new ArrayList<>();
		if (params.size() == ids.size()) {
			try {
				List<T> upl = new ArrayList<>();
				for (int i = 0; i < ids.size(); i++) {
					T ot = this.findById(ids.get(i));
					T nt = entityClass.newInstance();
					nt.setId(ot.getId());
					BeanUtilsBean2.getInstance().copyProperties(nt, params.get(i));
					upl.add(nt);
				}
				rs = this.updateBatch(upl);
			} catch (Exception e) {
				throw new BaseException(BaseConstant.ILLEGAL_ARGUMENT, e);
			}
		}else {
			throw new BaseException(BaseConstant.ILLEGAL_ARGUMENT, "ids's length is not equals params's length");
		}
		return rs;
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
			BeanUtilsBean2.getInstance().copyProperties(t, params);
			Example<T> example = Example.of(t);
			return this.findAll(example);
		} catch (Exception e) {
			throw new BaseException(BaseConstant.ILLEGAL_ARGUMENT, e);
		} 
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.com.compass.base.service.BaseService#findPage(cn.com.compass.base.vo.
	 * BaseRequestPageVo)
	 */
	@Override
	public Page<T> findPcPage(BaseRequestPcPageVo pageVo) {
		try {
			Map<String,Object> conditions = JacksonUtil.obj2Map(pageVo);
			// build example
			T t = entityClass.newInstance();
			DataXUtil.copyProperties(conditions, t, pageVo.source2targetProperties());
			Example<T> example = Example.of(t);
			// order columns 
			Map<String,Boolean> om = pageVo.getOrders();
			// build orders
			List<Order> orders = new ArrayList<>();
			for(Map.Entry<String,Boolean> entry : om.entrySet()) {
				String orderCol = entry.getKey();
				boolean isAsc = entry.getValue();
				Order order = new Order(isAsc ? Direction.ASC : Direction.DESC, orderCol);
				orders.add(order);
			}
			// build sort
			Sort sort = new Sort(orders);
			// do query page
			Pageable pageRequest = new PageRequest(pageVo.getPageNo() - 1, pageVo.getPageSize(), sort);
			org.springframework.data.domain.Page<T> page = this.findAll(example,pageRequest);
			// build result
			Page<T> result = new Page<>();
			result.setTotal(page.getTotalElements());
			result.setRecords(page.getContent());
			result.setCurrent(page.getNumber());
			result.setSize(page.getSize());
			return result;
		} catch (Exception e) {
			throw new BaseException(BaseConstant.ILLEGAL_ARGUMENT, e);
		}
	}
	
	@Override
	public Page<T> findAppPage(BaseRequestAppPageVo pageVo) {
		return null;
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
