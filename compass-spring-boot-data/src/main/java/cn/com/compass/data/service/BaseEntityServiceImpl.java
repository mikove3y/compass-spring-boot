package cn.com.compass.data.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import cn.com.compass.base.entity.BaseEntity;
import cn.com.compass.base.service.IBaseService;
import cn.com.compass.base.vo.BaseRequestAppPageVo;
import cn.com.compass.base.vo.BaseRequestPcPageVo;
import cn.com.compass.base.vo.Page;
import cn.com.compass.data.repository.BaseEntityRepository;

/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 基类服务实现类免注入实体Repository
 * @date 2018年8月8日 下午5:02:27
 *
 */
@Transactional(readOnly = true)
public class BaseEntityServiceImpl<T extends BaseEntity> implements IBaseService<T>{
	
	@Autowired
	private BaseEntityRepository<T> repository;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public T saveOne(T entity) {
		return repository.saveOne(entity);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<T> saveBatch(List<T> entities) {
		return repository.saveBatch(entities);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean deleteOne(T entity) {
		return repository.deleteOne(entity);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean deleteById(Long id) {
		return repository.deleteById(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean deleteByIds(List<Long> ids) {
		return repository.deleteByIds(ids);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean deleteBatch(List<T> entities) {
		return repository.deleteBatch(entities);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean deletByParams(Map<String, Object> params) {
		return repository.deletByParams(params);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public T updateOne(T entity) {
		return repository.updateOne(entity);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<T> updateBatch(List<T> entities) {
		return repository.updateBatch(entities);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public T updateOneByParams(Long id, Map<String, Object> params) {
		return repository.updateOneByParams(id, params);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<T> updateBatchByParams(List<Long> ids, List<Map<String, Object>> params) {
		return repository.updateBatchByParams(ids, params);
	}

	@Override
	public T findById(Long id) {
		return repository.findById(id);
	}

	@Override
	public List<T> findByIds(List<Long> ids) {
		return repository.findByIds(ids);
	}

	@Override
	public List<T> findListByParams(Map<String, Object> params) {
		return repository.findListByParams(params);
	}

	@Override
	public Page<T> findPcPage(BaseRequestPcPageVo pageVo) {
		return repository.findPcPage(pageVo);
	}

	@Override
	public Page<T> findAppPage(BaseRequestAppPageVo pageVo) {
		return repository.findAppPage(pageVo);
	}

}
