package cn.com.compass.data.querydsl;

import cn.com.compass.data.dto.BaseDTO;
import cn.com.compass.data.entity.BaseEntity;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.*;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.querydsl.SimpleEntityPathResolver;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 封装BaseDTO 操作
 * @date 2019/4/25 22:57
 */
public class JPAQueryDSLFactory {

    private JPAQueryFactory factory;

    private JPAQuery<?> jpa;

    private static final JPAQueryDSLFactory instance = new JPAQueryDSLFactory();

    private JPAQueryDSLFactory(){

    }

    /**
     * single
     * @return
     */
    public static JPAQueryDSLFactory instance(){
        return instance;
    }

    /**
     * 初始化JPAQueryFactory
     * @param factory
     * @return
     */
    public JPAQueryDSLFactory factory(final JPAQueryFactory factory){
        this.factory = factory;
        return this;
    }

    /**
     * select
     * @param dto
     * @param exprs
     * @param <D>
     * @return
     */
    public <D extends BaseDTO> JPAQueryDSLFactory select(Class<D> dto, Expression<?>... exprs){
        Assert.notNull(this.factory,"JPAQueryFactory can not be null!");
        this.jpa = this.factory.select(Projections.bean(dto,exprs));
        return this;
    }

    /**
     * from
     * @param domains
     * @param <E>
     * @return
     */
    public <E extends BaseEntity> JPAQueryDSLFactory from(Class<E>... domains){
        Assert.notEmpty(domains,"domains can not be empty!");
        Assert.notNull(this.jpa,"JPAQuery can not be null!");
        List<EntityPath<E>> domainList = new ArrayList<>();
        for(Class<E> domain : domains){
            EntityPath<E> path = SimpleEntityPathResolver.INSTANCE.createPath(domain);
            domainList.add(path);
        }
        this.jpa = this.jpa.from(domainList.toArray(new EntityPath[0]));
        return this;
    }

    /**
     * where
     * @param predicates
     * @return
     */
    public JPAQueryDSLFactory where(Predicate... predicates){
        Assert.notEmpty(predicates,"domains can not be empty!");
        Assert.notNull(this.jpa,"JPAQuery can not be null!");
        this.jpa = this.jpa.where(predicates);
        return this;
    }

    /**
     * orderBy
     * @param o
     * @return
     */
    public JPAQueryDSLFactory orderBy(OrderSpecifier<?>... o){
        Assert.notEmpty(o,"domains can not be empty!");
        Assert.notNull(this.jpa,"JPAQuery can not be null!");
        this.jpa = this.jpa.orderBy(o);
        return this;
    }

    /**
     * offset
     * @param offset
     * @return
     */
    public JPAQueryDSLFactory offset(long offset){
        Assert.notNull(this.jpa,"JPAQuery can not be null!");
        this.jpa = this.jpa.offset(offset);
        return this;
    }

    /**
     * limit
     * @param limit
     * @return
     */
    public JPAQueryDSLFactory limit(long limit){
        Assert.notNull(this.jpa,"JPAQuery can not be null!");
        this.jpa = this.jpa.limit(limit);
        return this;
    }

    /**
     * groupBy
     * @param exprs
     * @return
     */
    public JPAQueryDSLFactory groupBy(Expression<?>... exprs){
        Assert.notEmpty(exprs,"domains can not be empty!");
        Assert.notNull(this.jpa,"JPAQuery can not be null!");
        this.jpa = this.jpa.groupBy(exprs);
        return this;
    }

    /**
     * having
     * @param predicates
     * @return
     */
    public JPAQueryDSLFactory having(Predicate... predicates){
        Assert.notEmpty(predicates,"domains can not be empty!");
        Assert.notNull(this.jpa,"JPAQuery can not be null!");
        this.jpa = this.jpa.having(predicates);
        return this;
    }

    /**
     * fetchOne
     * @param <D>
     * @return
     */
    public <D extends BaseDTO> D fetchOne(){
        Assert.notNull(this.jpa,"JPAQuery can not be null!");
        return (D) this.jpa.fetchOne();
    }

    /**
     * fetchList
     * @param <D>
     * @return
     */
    public <D extends BaseDTO> List<D> fetchList(){
        Assert.notNull(this.jpa,"JPAQuery can not be null!");
        return (List<D>) this.jpa.fetch();
    }

    /**
     * page
     * @param <D>
     * @return
     */
    public <D extends BaseDTO> QueryResults<D> page(){
        Assert.notNull(this.jpa,"JPAQuery can not be null!");
        return (QueryResults<D>) this.jpa.fetchResults();
    }


}
