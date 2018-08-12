package cn.com.compass.data.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.domain.Specification;

import cn.com.compass.base.entity.BaseEntity;
import cn.com.compass.base.vo.AppPage;
import cn.com.compass.base.vo.BaseRequestAppPageVo;
import cn.com.compass.base.vo.BaseRequestPcPageVo;
import cn.com.compass.base.vo.PcPage;

/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 基类增删改查接口
 * @date 2018年6月6日 下午2:48:03
 *
 */
public interface IBaseEntityService<T extends BaseEntity> {
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
	public boolean deleteById(Long id);
	
	/**
	 * delet by ids
	 * @param ids
	 * @return
	 */
	public boolean deleteByIds(List<Long> ids);

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
	
	/**
	 * update one by params
	 * 
	 * @param id
	 * @param params
	 * @return
	 */
	public T updateOneByParams(Long id, Map<String, Object> params);

	/**
	 * update batch by params
	 * @param ids
	 * @param params
	 * @return
	 */
	public List<T> updateBatchByParams(List<Long> ids, List<Map<String, Object>> params);

	// find entity
	/**
	 * find one by id
	 * 
	 * @param id
	 * @return
	 */
	public T findById(Long id);
	
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
	public List<T> findByIds(List<Long> ids);
	
	/**
	 * find list by spec
	 * 
	 * @param spec
	 * @return
	 */
	public List<T> findListBySpec(Specification<T> spec);
	/**
	 * find pc page 按照pageNo pageSize分页
	 * @param pageVo
	 * @param matcher
	 * @return
	 */
	public PcPage<T> findPcPage(BaseRequestPcPageVo pageVo,Specification<T> spec);
	/**
	 * find app page 按照dataId pageSize分页
	 * @param pageVo
	 * @param matcher
	 * @return
	 */
	public AppPage<T> findAppPage(BaseRequestAppPageVo pageVo,Specification<T> spec);
}
