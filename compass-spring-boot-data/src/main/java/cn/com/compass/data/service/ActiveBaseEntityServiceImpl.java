package cn.com.compass.data.service;

import cn.com.compass.base.vo.BaseRequestAppPageVo;
import cn.com.compass.base.vo.BaseRequestPcPageVo;
import cn.com.compass.base.vo.BaseResponseAppPageVo;
import cn.com.compass.base.vo.BaseResponsePcPageVo;
import cn.com.compass.data.entity.BaseEntity;
import cn.com.compass.data.util.PageTransformUtil;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.activejpa.entity.Condition;
import org.activejpa.entity.Filter;
import org.activejpa.jpa.JPA;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo ActiveModel 模式 不需要Entity新建Repository接口类
 * @date 2019/4/21 14:26
 */
@Transactional(readOnly = true)
public class ActiveBaseEntityServiceImpl<T extends BaseEntity,PK extends Serializable> implements IBaseEntityService<T,PK> {

    private Class<T> domainClass;

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    private EntityManager entityManager;

    public ActiveBaseEntityServiceImpl(){
        try {
            ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
            String typeClazz = type.getActualTypeArguments()[0].getTypeName();
            this.domainClass = (Class<T>) Class.forName(typeClazz);
            this.entityManager = JPA.instance.getDefaultConfig().getContext().getEntityManager();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }


    /**
     * 获取EntityManager
     * @return
     */
    public EntityManager entityManager(){
        return entityManager;
    }

    /**
     * 获取domainClass类
     * @return
     */
    public Class<T> domainClass(){
        return domainClass;
    }

    /**
     *  获取jpaQueryFactory
     * @return
     */
    public JPAQueryFactory jpaQueryFactory(){
        return jpaQueryFactory;
    }

    /**
     * 是否存在
     *
     * @param id
     * @return
     */
    @Override
    public boolean exist(PK id) {
        return T.exists(id);
    }

    /**
     * save one
     *
     * @param entity
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public T saveOne(T entity) {
        entity.persist();
        return entity;
    }

    /**
     * save batch
     *
     * @param entities
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<T> saveBatch(List<T> entities) {
        Assert.notEmpty(entities, "saveBatch->The given entities not be empty!");
        for(T entity : entities){
            entity.persist();
        }
        return entities;
    }

    /**
     * delete one
     *
     * @param entity
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteOne(T entity) {
        entity.delete();
        return true;
    }

    /**
     * delet one by id
     *
     * @param id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(PK id) {
        Assert.notNull(id, "deleteById->The given id not be null!");
        Filter filter = new Filter();
        filter.addCondition(BaseEntity.ID, Condition.Operator.eq,id);
        T.deleteAll(filter);
        return true;
    }

    /**
     * delet by ids
     *
     * @param ids
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteByIds(List<PK> ids) {
        Assert.notEmpty(ids, "deleteByIds->The given ids not be empty!");
        Filter filter = new Filter();
        filter.addCondition(BaseEntity.ID, Condition.Operator.in,ids);
        T.deleteAll(filter);
        return true;
    }

    /**
     * delete batch
     *
     * @param entities
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBatch(List<T> entities) {
        Assert.notEmpty(entities, "deleteBatch->The given entities not be empty!");
        List<Serializable> ids = entities.stream().map(T::getId).distinct().collect(Collectors.toList());
        Filter filter = new Filter();
        filter.addCondition(BaseEntity.ID, Condition.Operator.in,ids);
        T.deleteAll(filter);
        return true;
    }

    /**
     * delete by spec
     *
     * @param spec
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deletBySpec(Specification<T> spec) {
        List<T> list = this.findListBySpec(spec);
        Assert.notEmpty(list, "deletBySpec->The given spec can not find any entities!");
        return this.deleteBatch(list);
    }

    /**
     * update one selective not null column
     *
     * @param entity
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public T updateOne(T entity) {
        entity.merge();
        return entity;
    }

    /**
     * update batch selective not null column
     *
     * @param entities
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<T> updateBatch(List<T> entities) {
        Assert.notEmpty(entities, "updateBatch->The given entities not be empty!");
        for (T entity : entities){
            entity.merge();
        }
        return entities;
    }

    /**
     * find one by id
     *
     * @param id
     * @return
     */
    @Override
    public T findById(PK id) {
        return T.findById(id);
    }

    /**
     * find one by spec
     *
     * @param spec
     * @return
     */
    @Override
    public T findOneBySpec(Specification<T> spec) {
        TypedQuery<T> typedQuery = this.entityManager().createQuery(this.getQuery(spec));
        return typedQuery.getSingleResult();
    }

    /**
     * find list by ids
     *
     * @param ids
     * @return
     */
    @Override
    public List<T> findByIds(List<PK> ids) {
        Assert.notEmpty(ids, "findByIds->The given ids not be empty!");
        Filter filter = new Filter();
        filter.addCondition(BaseEntity.ID, Condition.Operator.in,ids);
        return T.where(filter);
    }

    /**
     * find list by spec
     *
     * @param spec
     * @return
     */
    @Override
    public List<T> findListBySpec(Specification<T> spec) {
        TypedQuery<T> typedQuery = this.entityManager().createQuery(this.getQuery(spec));
        return typedQuery.getResultList();
    }

    /**
     * find all
     *
     * @return
     */
    @Override
    public List<T> findAll() {
        return T.where();
    }

    /**
     * find pc page 按照pageNo pageSize分页
     *
     * @param pageVo
     * @param spec
     * @return
     */
    @Override
    public BaseResponsePcPageVo<T> findPcPage(BaseRequestPcPageVo pageVo, Specification<T> spec) {
        TypedQuery<T> typedQuery = this.entityManager().createQuery(this.getQuery(spec));
        Pageable pageRequest = new PageRequest(pageVo.getPageNo() >= 1 ? pageVo.getPageNo() - 1 : 0, pageVo.getPageSize(), this.buildSort(pageVo.orders()));
        return PageTransformUtil.transformJpaPage2PcPage(this.readPage(typedQuery,pageRequest,spec));
    }

    /**
     * find app page 按照dataId pageSize分页
     *
     * @param pageVo
     * @param spec
     * @return
     */
    @Override
    public BaseResponseAppPageVo<T> findAppPage(BaseRequestAppPageVo pageVo, Specification<T> spec) {
        TypedQuery<T> typedQuery = this.entityManager().createQuery(this.getQuery(spec));
        Pageable pageRequest = new PageRequest(0, pageVo.getPageSize(), this.buildSort(pageVo.orders()));
        return PageTransformUtil.transformJpaPage2AppPage(this.readPage(typedQuery,pageRequest,spec),pageVo);
    }

    /**
     * count by spec
     *
     * @param spec
     * @return
     */
    @Override
    public Long countNum(Specification<T> spec) {
        TypedQuery<Long> typedQuery = this.entityManager().createQuery(this.getCountQuery(spec));
        List<Long> totals = typedQuery.getResultList();
        Long total = 0L;
        for (Long element : totals) {
            total += element == null ? 0 : element;
        }
        return total;
    }

    /**
     * 获取Query
     * @param spec
     * @return
     */
    private CriteriaQuery<T> getQuery(Specification<T> spec){
        CriteriaBuilder builder = this.entityManager().getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(domainClass);
        Root<T> root = query.from(domainClass);
        Predicate predicate = spec.toPredicate(root, query, builder);
        if (predicate != null) {
            query.where(predicate);
        }
        query.select(root);
        return query;
    }

    /**
     * 获取CountQuery
     * @param spec
     * @return
     */
    private CriteriaQuery<Long> getCountQuery(Specification<T> spec){
        CriteriaBuilder builder = this.entityManager().getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<T> root = query.from(domainClass);
        Predicate predicate = spec.toPredicate(root, query, builder);
        if (predicate != null) {
            query.where(predicate);
        }
        if (query.isDistinct()) {
            query.select(builder.countDistinct(root));
        } else {
            query.select(builder.count(root));
        }
        // Remove all Orders the Specifications might have applied
        query.orderBy(Collections.<Order> emptyList());
        return query;
    }

    /**
     * 构造sort
     * @param orderMap
     * @return
     */
    private Sort buildSort(Map<String,Boolean> orderMap){
        List<Sort.Order> orders = new ArrayList<>();
        if(MapUtils.isNotEmpty(orderMap)){
            for(Map.Entry<String,Boolean> en : orderMap.entrySet()){
                Sort.Order order = new Sort.Order((en.getValue() ? Sort.Direction.ASC : Sort.Direction.DESC), en.getKey());
                orders.add(order);
            }
        }
        // build sort
        return new Sort(orders);
    }

    /**
     * 读取分页
     * @param query
     * @param pageable
     * @param spec
     * @return
     */
    private Page<T> readPage(TypedQuery<T> query, Pageable pageable, final Specification<T> spec) {
        query.setFirstResult(pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        return PageableExecutionUtils.getPage(query.getResultList(), pageable, new PageableExecutionUtils.TotalSupplier() {
            @Override
            public long get() {
                return countNum(spec);
            }
        });
    }
}
