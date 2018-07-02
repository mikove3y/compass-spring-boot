package cn.com.compass.starter.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.com.compass.base.constant.BaseConstant;
import cn.com.compass.base.entity.BaseEntity;
import cn.com.compass.base.exception.BaseException;
import cn.com.compass.base.vo.BaseDataX;
import cn.com.compass.base.vo.Page;
import cn.com.compass.data.repository.BaseEntityRepository;
import cn.com.compass.util.DataXUtil;
import cn.com.compass.util.JacksonUtil;
import cn.com.compass.web.controller.BaseController;
import cn.com.compass.web.vo.BaseControllerRequestVo;
import cn.com.compass.web.vo.BaseControllerRequestVo.AddBatch;
import cn.com.compass.web.vo.BaseControllerRequestVo.AddOne;
import cn.com.compass.web.vo.BaseControllerRequestVo.DeleteBatch;
import cn.com.compass.web.vo.BaseControllerRequestVo.GetList;
import cn.com.compass.web.vo.BaseControllerRequestVo.GetPage;
import cn.com.compass.web.vo.BaseControllerRequestVo.UpdateBatch;
import cn.com.compass.web.vo.BaseControllerRequestVo.UpdateOne;
/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo rest controller 通用增删改查接口
 * @date 2018年6月6日 下午2:42:03
 * @since 1.0.7 优化为泛型实现类
 */
public class BaseRestController<T extends BaseEntity,Rv extends BaseControllerRequestVo> extends BaseController {
	
	private BaseEntityRepository<T> baseEntityRepository;
	
	private Class<T> domainClass;
	
	public void setBaseEntityRepository(BaseEntityRepository<T> baseEntityRepository) {
		this.baseEntityRepository = baseEntityRepository;
		this.domainClass = baseEntityRepository.domainClass();
		
	}
	
	public BaseEntityRepository<T> getBaseEntityRepository() {
		return baseEntityRepository;
	}
	
	
	private T copyOne(Object source,Map<String, String> propertyMapping){
		try {
			T target = domainClass.newInstance();
			DataXUtil.copyProperties(source, target, propertyMapping);
			return target;
		} catch (Exception e) {
			throw new BaseException(BaseConstant.ILLEGAL_ARGUMENT, e);
		}
	}
	
	private List<T> copyList(List<?> sources) {
		List<T> targets = new ArrayList<>();
		for (Object source : sources) {
			if(source instanceof BaseDataX) {
				BaseDataX dx = (BaseDataX) source;
				targets.add(this.copyOne(source,dx.source2targetProperties()));
			}else {
				targets.add(this.copyOne(source,null));
			}
		}
		return targets;
	}
	
	
	public T addOne(AddOne vo) {
		T entity = this.copyOne(vo,vo.source2targetProperties());
		return this.getBaseEntityRepository().saveOne(entity);
	}

	
	public List<T> addBatch(AddBatch vo) {
		List<T> entities = this.copyList(vo.getList());
		return this.getBaseEntityRepository().saveBatch(entities);
	}
	
	public T deleteOne(Long id) {
		T oe = this.getOne(id);
		boolean del = this.getBaseEntityRepository().deleteById(id);
		return del ? oe : null;
	}
	
	public List<T> deleteBatch(DeleteBatch vo) {
		List<Long> ids = vo.getIds();
		List<T> oes = this.getBaseEntityRepository().findByIds(ids);
		boolean del = this.getBaseEntityRepository().deleteByIds(ids);
		return del ? oes : null;
	}
	
	public T updateOne(UpdateOne vo) {
		T oe = this.getOne(vo.getId());
		T ne = this.copyOne(vo,vo.source2targetProperties());
		ne.setId(oe.getId());
		return this.getBaseEntityRepository().updateOne(ne);
	}

	public List<T> updateBatch(UpdateBatch vo) {
		List<UpdateOne> ul = vo.getList();
		List<T> ues = new ArrayList<>();
		for(UpdateOne uo : ul) {
			T oe = this.getOne(uo.getId());
			T ne = this.copyOne(uo,uo.source2targetProperties());
			ne.setId(oe.getId());
			ues.add(ne);
		}
		return this.getBaseEntityRepository().updateBatch(ues);
	}

	public T getOne(Long id) {
		return this.getBaseEntityRepository().findById(id);
	}

	public List<T> getList(GetList vo) {
		try {
			T queryExample = copyOne(vo, vo.source2targetProperties());
			return this.getBaseEntityRepository().findListByParams(JacksonUtil.obj2MapIgnoreNull(queryExample));
		} catch (Exception e) {
			throw new BaseException(BaseConstant.ILLEGAL_ARGUMENT, e);
		}
	}

	public Page<T> getPage(GetPage vo) {
		return this.getBaseEntityRepository().findPage(vo);
	}


}
