package cn.com.compass.starter.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import cn.com.compass.base.constant.BaseConstant;
import cn.com.compass.base.entity.BaseEntity;
import cn.com.compass.base.exception.BaseException;
import cn.com.compass.base.vo.BaseDataX;
import cn.com.compass.base.vo.Page;
import cn.com.compass.data.repository.BaseEntityRepository;
import cn.com.compass.util.DataXUtil;
import cn.com.compass.util.JacksonUtil;
import cn.com.compass.web.controller.BaseController;
import cn.com.compass.web.controller.IBaseController;
import cn.com.compass.web.vo.BaseAddBatchRequestVo;
import cn.com.compass.web.vo.BaseAddOneRequestVo;
import cn.com.compass.web.vo.BaseDeleteBatchRequestVo;
import cn.com.compass.web.vo.BaseGetListRequestVo;
import cn.com.compass.web.vo.BaseGetPageRequestVo;
import cn.com.compass.web.vo.BaseUpdateBatchRequestVo;
import cn.com.compass.web.vo.BaseUpdateOneRequestVo;
/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo rest controller 通用增删改查接口
 * @date 2018年6月6日 下午2:42:03
 * @since 1.0.7 优化为泛型实现类
 */
@Validated
public class BaseRestController<T extends BaseEntity, A extends BaseAddOneRequestVo, B extends BaseAddBatchRequestVo, C extends BaseUpdateOneRequestVo, D extends BaseUpdateBatchRequestVo, E extends BaseGetListRequestVo, F extends BaseGetPageRequestVo> extends BaseController implements IBaseController<T,A,B,C,D,E,F>{
	
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

	@Override
	@PostMapping
	public T addOne(@Valid @RequestBody A vo) {
		T entity = this.copyOne(vo,vo.source2targetProperties());
		return this.getBaseEntityRepository().saveOne(entity);
	}

	@Override
	@PostMapping("/batch")
	public List<T> addBatch(@Valid @RequestBody B vo) {
		List<T> entities = this.copyList(vo.getList());
		return this.getBaseEntityRepository().saveBatch(entities);
	}

	@Override
	@DeleteMapping("/{id}")
	public T deleteOne(@NotNull(message="id can not be null") @PathVariable("id") Long id) {
		T oe = this.getOne(id);
		boolean del = this.getBaseEntityRepository().deleteById(id);
		return del ? oe : null;
	}

	@Override
	@DeleteMapping("/batch")
	public List<T> deleteBatch(@Valid @RequestBody BaseDeleteBatchRequestVo vo) {
		List<Long> ids = vo.getIds();
		List<T> oes = this.getBaseEntityRepository().findByIds(ids);
		boolean del = this.getBaseEntityRepository().deleteByIds(ids);
		return del ? oes : null;
	}

	@Override
	@PutMapping
	public T updateOne(@Valid @RequestBody C vo) {
		T oe = this.getOne(vo.getId());
		T ne = this.copyOne(vo,vo.source2targetProperties());
		ne.setId(oe.getId());
		return this.getBaseEntityRepository().updateOne(ne);
	}

	@Override
	@PutMapping("/batch")
	public List<T> updateBatch(@Valid @RequestBody D vo) {
		List<BaseUpdateOneRequestVo> ul = vo.getList();
		List<T> ues = new ArrayList<>();
		for(BaseUpdateOneRequestVo uo : ul) {
			T oe = this.getOne(uo.getId());
			T ne = this.copyOne(uo,uo.source2targetProperties());
			ne.setId(oe.getId());
			ues.add(ne);
		}
		return this.getBaseEntityRepository().updateBatch(ues);
	}

	@Override
	@GetMapping("/{id}")
	public T getOne(@NotNull(message="id can not be null") @PathVariable("id") Long id) {
		return this.getBaseEntityRepository().findById(id);
	}

	@Override
	@PostMapping("/list")
	public List<T> getList(@Valid @RequestBody E vo) {
		try {
			T queryExample = copyOne(vo, vo.source2targetProperties());
			return this.getBaseEntityRepository().findListByParams(JacksonUtil.obj2MapIgnoreNull(queryExample));
		} catch (Exception e) {
			throw new BaseException(BaseConstant.ILLEGAL_ARGUMENT, e);
		}
	}

	@Override
	@PostMapping("/page")
	public Page<T> getPage(@Valid @RequestBody F vo) {
		return this.getBaseEntityRepository().findPage(vo);
	}
	
	


}
