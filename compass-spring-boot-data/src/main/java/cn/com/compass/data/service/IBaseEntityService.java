package cn.com.compass.data.service;

import cn.com.compass.base.vo.BaseRequestAppPageVo;
import cn.com.compass.base.vo.BaseRequestPcPageVo;
import cn.com.compass.base.vo.BaseResponseAppPageVo;
import cn.com.compass.base.vo.BaseResponsePcPageVo;
import cn.com.compass.data.entity.BaseEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 基类增删改查接口
 * @date 2018年6月6日 下午2:48:03
 *
 */
public interface IBaseEntityService<T extends BaseEntity,PK extends Serializable> {
	// save entity
	/**
	 * save one
	 * 
	 * @param entity
	 * @return
	 */
	public T saveOne(T entity);

	/**
	 * save batch
	 * 
	 * @param entities
	 * @return
	 */
	public List<T> saveBatch(List<T> entities);

	// delete entity
	/**
	 * delete one
	 * 
	 * @param entity
	 * @return
	 */
	public boolean deleteOne(T entity);

	/**
	 * delet one by id
	 * 
	 * @param id
	 * @return
	 */
	public boolean deleteById(PK id);
	
	/**
	 * delet by ids
	 * @param ids
	 * @return
	 */
	public boolean deleteByIds(List<PK> ids);

	/**
	 * delete batch
	 * 
	 * @param entities
	 * @return
	 */
	public boolean deleteBatch(List<T> entities);

	/**
	 * delete by spec
	 * 
	 * @param spec
	 * @return
	 */
	public boolean deletBySpec(Specification<T> spec);

	// update entity
	/**
	 * update one selective not null column
	 * 
	 * @param entity
	 * @return
	 */
	public T updateOne(T entity);

	/**
	 * update batch selective not null column
	 * 
	 * @param entities
	 * @return
	 */
	public List<T> updateBatch(List<T> entities);
	
	// find entity
	/**
	 * find one by id
	 * 
	 * @param id
	 * @return
	 */
	public T findById(PK id);
	
	/**
	 * find one by spec
	 * @param spec
	 * @return
	 */
	public T findOneBySpec(Specification<T> spec);

	/**
	 * find list by ids
	 * 
	 * @param ids
	 * @return
	 */
	public List<T> findByIds(List<PK> ids);
	
	/**
	 * find list by spec
	 * 
	 * @param spec
	 * @return
	 */
	public List<T> findListBySpec(Specification<T> spec);

	/**
	 *  find all
	 * @return
	 */
	public List<T> findAll();
	/**
	 * find pc page 按照pageNo pageSize分页
	 * @param pageVo
	 * @param spec
	 * @return
	 */
	public BaseResponsePcPageVo<T> findPcPage(BaseRequestPcPageVo pageVo, Specification<T> spec);
	/**
	 * find app page 按照dataId pageSize分页
	 * @param pageVo
	 * @param spec
	 * @return
	 */
	public BaseResponseAppPageVo<T> findAppPage(BaseRequestAppPageVo pageVo, Specification<T> spec);

	// count
	/**
	 * count by spec
	 * @param spec
	 * @return
	 */
	public Long countNum(Specification<T> spec);

	/**
	 * 领域模型类
	 * @return
	 */
	public Class<T> domainClass();
	/**
	 * 实体管理器
	 * @return
	 */
	public EntityManager entityManager();

	/**
	 * JPAQueryFactory
	 * @return
	 */
	public JPAQueryFactory jpaQueryFactory();

	// 扩展dto查询映射方法
}
