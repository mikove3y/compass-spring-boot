package cn.com.compass.data.repository;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

import cn.com.compass.base.annotation.LogicDelete;
import cn.com.compass.base.constant.BaseConstant;
import cn.com.compass.base.entity.BaseEntity;
import cn.com.compass.base.exception.BaseException;
import cn.com.compass.base.vo.AppPage;
import cn.com.compass.base.vo.BaseDataX;
import cn.com.compass.base.vo.BaseRequestAppPageVo;
import cn.com.compass.base.vo.BaseRequestPcPageVo;
import cn.com.compass.base.vo.PcPage;
import cn.com.compass.data.util.PageTransformUtil;
import cn.com.compass.util.DataXUtil;

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
	
	private boolean isLogicDelete = false;
	
	@Value("${spring.jpa.open-logic-delete}")
	private boolean openLogicDelete = false;

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
	public boolean deleteById(Long id) {
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
	public boolean deleteByIds(List<Long> ids) {
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
	 * @see cn.com.compass.data.service.IBaseEntityService#updateOneByParams(java.io.
	 * Serializable, java.util.Map)
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public T updateOneByParams(Long id, Map<String, Object> params) {
		Assert.notEmpty(params, "updateOneByParams->The given map of params not be empty!");
		try {
			T ot = this.findById(id);
			if(ot==null) {
				throw new BaseException(BaseConstant.ILLEGAL_ARGUMENT, "can not find entity of " + entityClass.getName() + " by id = " + id );
			}
			T nt = entityClass.newInstance();
			nt.setId(ot.getId());
			DataXUtil.copyProperties(params, nt, null);
			return this.updateOne(nt);
		} catch (Exception e) {
			throw new BaseException(BaseConstant.ILLEGAL_ARGUMENT, e);
		}
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
	 * @see
	 * cn.com.compass.data.service.IBaseEntityService#updateBatchByParams(java.util.List,
	 * java.util.List)
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<T> updateBatchByParams(List<Long> ids, List<Map<String, Object>> params) {
		Assert.noNullElements(new Object[] { ids, params }, "updateBatchByParams->The given Collection of ids & params not be empty!");
		List<T> rs = new ArrayList<>();
		if (params.size() == ids.size()) {
			try {
				List<T> upl = new ArrayList<>();
				for (int i = 0; i < ids.size(); i++) {
					T ot = this.findById(ids.get(i));
					if(ot==null) {
						throw new BaseException(BaseConstant.ILLEGAL_ARGUMENT, "can not find entity of " + entityClass.getName() + " by id = " + ids.get(i) );
					}
					T nt = entityClass.newInstance();
					nt.setId(ot.getId());
					DataXUtil.copyProperties(params.get(i), nt, null);
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
	 * @see cn.com.compass.data.service.IBaseEntityService#findById(java.io.Serializable)
	 */
	@Override
	public T findById(Long id) {
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
	public List<T> findByIds(List<Long> ids) {
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
	public PcPage<T> findPcPage(BaseRequestPcPageVo pageVo, Specification<T> spec) {
		Assert.noNullElements(new Object[] { pageVo, spec }, "findPcPage->The given pageVo & spec not be null!");
		// order columns
		Map<String, String> om = pageVo.getOrders();
		// build orders
		List<Order> orders = new ArrayList<>();
		for (Map.Entry<String, String> entry : om.entrySet()) {
			String orderCol = entry.getKey();
			String orderVal = entry.getValue();
			Order order = new Order(BaseDataX.ORDER_ASC.equals(orderVal) ? Direction.ASC : Direction.DESC, orderCol);
			orders.add(order);
		}
		// build sort
		Sort sort = new Sort(orders);
		// do query page
		Pageable pageRequest = new PageRequest(pageVo.getPageNo() >= 1 ? pageVo.getPageNo() - 1 : 0,
				pageVo.getPageSize(), sort);
		// transform jpa page to frameWork pc-page
		return PageTransformUtil.transformJpaPage2PcPage(this.findAll(spec, pageRequest));
	}
	
	@Override
	public AppPage<T> findAppPage(BaseRequestAppPageVo pageVo, Specification<T> spec) {
		Assert.noNullElements(new Object[] { pageVo, spec }, "findPcPage->The given pageVo & spec not be null!");
		// order columns
		Map<String, String> om = pageVo.getOrders();
		// build orders
		List<Order> orders = new ArrayList<>();
		for (Map.Entry<String, String> entry : om.entrySet()) {
			String orderCol = entry.getKey();
			String orderVal = entry.getValue();
			Order order = new Order(BaseDataX.ORDER_ASC.equals(orderVal) ? Direction.ASC : Direction.DESC, orderCol);
			orders.add(order);
		}
		// build sort
		Sort sort = new Sort(orders);
		// do query page
		Pageable pageRequest = new PageRequest(0, pageVo.getPageSize(), sort);
		// transform jpa page to frameWork app-page
		return PageTransformUtil.transformJpaPage2AppPage(this.findAll(spec, pageRequest),pageVo);
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
