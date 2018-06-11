package cn.com.compass.base.service;

import java.util.List;
import java.util.Map;

import cn.com.compass.base.entity.BaseEntity;
import cn.com.compass.base.vo.BaseRequestPageVo;
import cn.com.compass.base.vo.Page;

/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 基类增删改查接口
 * @date 2018年6月6日 下午2:48:03
 *
 */
public interface BaseService<T extends BaseEntity> {
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
	 * delete by params
	 * 
	 * @param params
	 * @return
	 */
	public boolean deletByParams(Map<String, Object> params);

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
	 * find list by ids
	 * 
	 * @param ids
	 * @return
	 */
	public List<T> findByIds(List<Long> ids);

	/**
	 * find list by params
	 * 
	 * @param params
	 * @return
	 */
	public List<T> findListByParams(Map<String, Object> params);

	/**
	 * find page
	 * 
	 * @param pageVo
	 * @return
	 */
	public Page<T> findPage(BaseRequestPageVo pageVo);
}
