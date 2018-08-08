package cn.com.compass.starter.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.com.compass.base.constant.BaseConstant;
import cn.com.compass.base.entity.BaseEntity;
import cn.com.compass.base.exception.BaseException;
import cn.com.compass.base.vo.BaseDataX;
import cn.com.compass.base.vo.BaseRequestAppPageVo;
import cn.com.compass.base.vo.BaseRequestPcPageVo;
import cn.com.compass.base.vo.BaseVueTreeVo;
import cn.com.compass.base.vo.Page;
import cn.com.compass.data.repository.BaseEntityRepository;
import cn.com.compass.util.DataXUtil;
import cn.com.compass.web.controller.BaseController;

/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo rest controller 通用增删改查接口
 * @date 2018年6月6日 下午2:42:03
 * @since 1.0.7 优化为泛型实现类
 * @since 1.1.14 不在允许使用直接在Controller层进行后台数据操作，必须通过接口进行封装{@link cn.com.compass.data.service.BaseEntityServiceImpl<T>}
 */
@Deprecated
public class BaseRestController<T extends BaseEntity> extends BaseController {

	/**
	 * 实体orm
	 */
	private BaseEntityRepository<T> baseEntityRepository;
	/**
	 * 实体类
	 */
	private Class<T> domainClass;

	public void setBaseEntityRepository(BaseEntityRepository<T> baseEntityRepository) {
		this.baseEntityRepository = baseEntityRepository;
		this.domainClass = baseEntityRepository.domainClass();

	}

	public BaseEntityRepository<T> getBaseEntityRepository() {
		return baseEntityRepository;
	}

	/**
	 * 复制来源字段到目标字段中
	 * 
	 * @param source
	 * @param propertyMapping
	 * @return
	 */
	public T copyOne(Object source, Map<String, String> propertyMapping) {
		try {
			T target = domainClass.newInstance();
			DataXUtil.copyProperties(source, target, propertyMapping);
			return target;
		} catch (Exception e) {
			throw new BaseException(BaseConstant.ILLEGAL_ARGUMENT, e);
		}
	}

	/**
	 * 复制来源字段到目标字段中
	 * 
	 * @param sources
	 * @return
	 */
	public List<T> copyList(List<?> sources) {
		List<T> targets = new ArrayList<>();
		for (Object source : sources) {
			if (source instanceof BaseDataX) {
				BaseDataX dx = (BaseDataX) source;
				targets.add(this.copyOne(source, dx.source2targetProperties()));
			} else {
				targets.add(this.copyOne(source, null));
			}
		}
		return targets;
	}

	/**
	 * 新增一个
	 * 
	 * @param vo
	 * @return
	 */
	public T addOne(BaseDataX vo) {
		T entity = this.copyOne(vo, vo.source2targetProperties());
		return this.getBaseEntityRepository().saveOne(entity);
	}

	/**
	 * 删除一个
	 * 
	 * @param id
	 * @return
	 */
	public T deleteOne(Long id) {
		T oe = this.getOne(id);
		if(oe==null) {
			throw new BaseException(BaseConstant.ILLEGAL_ARGUMENT, "找不到id="+id+"对应的记录");
		}
		boolean del = this.getBaseEntityRepository().deleteById(id);
		return del ? oe : null;
	}

	/**
	 * 更新一个
	 * 
	 * @param id
	 * @param vo
	 * @return
	 */
	public T updateOne(Long id, BaseDataX vo) {
		T oe = this.getOne(id);
		if(oe==null) {
			throw new BaseException(BaseConstant.ILLEGAL_ARGUMENT, "找不到id="+id+"对应的记录");
		}
		T ne = this.copyOne(vo, vo.source2targetProperties());
		ne.setId(oe.getId());
		return this.getBaseEntityRepository().updateOne(ne);
	}

	/**
	 * 获取一个
	 * 
	 * @param id
	 * @return
	 */
	public T getOne(Long id) {
		return this.getBaseEntityRepository().findById(id);
	}

	/**
	 * pc分页查询
	 * 
	 * @param vo
	 * @return
	 */
	public Page<T> getPcPage(BaseRequestPcPageVo vo) {
		return this.getBaseEntityRepository().findPcPage(vo);
	}

	/**
	 * app分页查询
	 * 
	 * @param vo
	 * @return
	 */
	public Page<T> getAppPage(BaseRequestAppPageVo vo) {
		return null;
	}

	/**
	 * 获取vue树
	 * 
	 * @return
	 */
	public List<BaseVueTreeVo> getVueTree() {
		return null;
	}

}
