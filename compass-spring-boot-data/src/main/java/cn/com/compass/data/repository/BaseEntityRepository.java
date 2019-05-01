package cn.com.compass.data.repository;

import cn.com.compass.base.vo.BaseRequestAppPageVo;
import cn.com.compass.base.vo.BaseRequestPcPageVo;
import cn.com.compass.base.vo.BaseResponseAppPageVo;
import cn.com.compass.base.vo.BaseResponsePcPageVo;
import cn.com.compass.data.entity.BaseEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 基类jpa repository
 * @date 2018年6月5日 下午4:02:54
 */
@NoRepositoryBean
public interface BaseEntityRepository<T extends BaseEntity, PK extends Serializable> extends JpaRepository<T, PK>,
        JpaSpecificationExecutor<T>, QueryDslPredicateExecutor<T> {

    /**
     * find pc page 按照pageNo pageSize分页
     *
     * @param pageVo
     * @param spec
     * @return
     */
    public BaseResponsePcPageVo<T> findPcPage(BaseRequestPcPageVo pageVo, Specification<T> spec);

    /**
     * find app page 按照dataId pageSize分页
     *
     * @param pageVo
     * @param spec
     * @return
     */
    public BaseResponseAppPageVo<T> findAppPage(BaseRequestAppPageVo pageVo, Specification<T> spec);

}
