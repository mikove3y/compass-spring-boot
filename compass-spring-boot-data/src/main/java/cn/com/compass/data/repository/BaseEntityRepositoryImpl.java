package cn.com.compass.data.repository;

import cn.com.compass.base.vo.BaseRequestAppPageVo;
import cn.com.compass.base.vo.BaseRequestPcPageVo;
import cn.com.compass.base.vo.BaseResponseAppPageVo;
import cn.com.compass.base.vo.BaseResponsePcPageVo;
import cn.com.compass.data.entity.BaseEntity;
import cn.com.compass.data.util.PageTransformUtil;
import org.apache.commons.collections.CollectionUtils;
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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo hibernate jpa use for add、selete、update、simple-select and entity-table auto build </br>
 * mybatis plus use for complex-select actions </br>
 * since v1.3.0 extra: queryDsl Projections for DTO to Entity </br>
 * @date 2018年6月6日 下午3:54:06
 */
@Transactional(readOnly = true)
public class BaseEntityRepositoryImpl<T extends BaseEntity, PK extends Serializable> extends QueryDslJpaRepository<T, PK>
        implements BaseEntityRepository<T, PK> {

    /**
     * Creates a new {@link QueryDslJpaRepository} from the given domain class and {@link EntityManager}. This will use
     * the {@link SimpleEntityPathResolver} to translate the given domain class into an {@link EntityPath}.
     *
     * @param entityInformation must not be {@literal null}.
     * @param entityManager     must not be {@literal null}.
     */
    public BaseEntityRepositoryImpl(JpaEntityInformation<T, PK> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
    }


    /*
     * (non-Javadoc)
     *
     * @see cn.com.compass.data.service.IBaseEntityService#findPage(cn.com.compass.base.vo.
     * BaseRequestPageVo)
     */
    @Override
    public BaseResponsePcPageVo<T> findPcPage(BaseRequestPcPageVo pageVo, Specification<T> spec) {
        Assert.noNullElements(new Object[]{pageVo, spec}, "findPcPage->The given pageVo & spec not be null!");
        // do query page
        Pageable pageRequest = new PageRequest(pageVo.getPageNo() >= 1 ? pageVo.getPageNo() - 1 : 0,
                pageVo.getPageSize(), this.buildSort(pageVo.orders(), pageVo.isAsc()));
        // transform jpa page to frameWork pc-page
        return PageTransformUtil.transformJpaPage2PcPage(this.findAll(spec, pageRequest));
    }

    /**
     * 构造排序
     *
     * @param orders
     * @param isAsc
     * @return
     */
    private Sort buildSort(List<String> orders, boolean isAsc) {
        List<Order> ors = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(orders)) {
            orders.forEach(o -> {
                Order or = new Order((isAsc ? Direction.ASC : Direction.DESC), o);
                ors.add(or);
            });
        }
        return new Sort(ors);
    }

    @Override
    public BaseResponseAppPageVo<T> findAppPage(BaseRequestAppPageVo pageVo, Specification<T> spec) {
        Assert.noNullElements(new Object[]{pageVo, spec}, "findAppPage->The given pageVo & spec not be null!");
        // do query page
        Pageable pageRequest = new PageRequest(0, pageVo.getPageSize(), this.buildSort(pageVo.orders(), pageVo.isAsc()));
        // transform jpa page to frameWork app-page
        return PageTransformUtil.transformJpaPage2AppPage(this.findAll(spec, pageRequest), pageVo);
    }

}
