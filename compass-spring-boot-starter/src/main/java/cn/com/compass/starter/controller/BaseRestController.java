package cn.com.compass.starter.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import cn.com.compass.base.entity.BaseEntity;
import cn.com.compass.base.vo.BaseRequestPageVo;
import cn.com.compass.base.vo.BaseRequestVo;
import cn.com.compass.data.repository.BaseEntityRepository;
import cn.com.compass.web.controller.BaseController;
/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo rest controller 通用增删改查接口
 * @date 2018年6月6日 下午2:42:03
 *
 */
@Validated
public abstract class BaseRestController<T extends BaseEntity> extends BaseController {
	
	private BaseEntityRepository<T> baseEntityRepository;
	
	public void setBaseEntityRepository(BaseEntityRepository<T> baseEntityRepository) {
		this.baseEntityRepository = baseEntityRepository;
	}
	
	public BaseEntityRepository<T> getBaseEntityRepository() {
		return baseEntityRepository;
	}
	
	/**
	 * 单条新增,返回最新数据
	 * @param entity
	 */
	@PostMapping("/one")
	public void add(@Valid @RequestBody T entity) {
		this.printResonpseJson(this.getBaseEntityRepository().saveOne(entity));
	}
	
	/**
	 * 批量新增,返回最新数据集
	 * @param entities
	 */
	@PostMapping("/batch")
	public void addBatch(@Valid @RequestBody List<T> entities) {
		this.printResonpseJson(this.getBaseEntityRepository().saveBatch(entities));
	}
	
	/**
	 * 单条删除
	 * @param id
	 */
	@DeleteMapping("/one/{id}")
	public void delete(@NotNull(message="id不能为空") @PathVariable(value="id",required=true) Long id) {
		this.printResonpseJson(this.getBaseEntityRepository().deleteById(id));
	}
	
	/**
	 * 批量删除
	 * @param ids
	 */
	@DeleteMapping("/batch")
	public void deleteBatch(@Valid @RequestBody List<Long> ids) {
		this.printResonpseJson(this.getBaseEntityRepository().deleteByIds(ids));
	}
	
	/**
	 * 更新单条记录,返回最新数据
	 * @param entity
	 */
	@PutMapping("/one")
	public void update(@Valid @RequestBody T entity) {
		this.printResonpseJson(this.getBaseEntityRepository().updateOne(entity));
	}
	
	/**
	 * 批量更新,返回最新数据集
	 * @param entities
	 */
	@PutMapping("/batch")
	public void updateBatch(@Valid @RequestBody List<T> entities) {
		this.printResonpseJson(this.getBaseEntityRepository().updateBatch(entities));
	}
	
	/**
	 * 查询一条记录
	 * @param id
	 */
	@GetMapping("/one/{id}")
	public void getOne(@NotNull(message="id不能为空") @PathVariable(value="id",required=true) Long id) {
		this.printResonpseJson(this.getBaseEntityRepository().findById(id));
	}
	
	/**
	 * 列表查询
	 * @param params
	 */
	@PostMapping("/list")
	public void getList(@Valid @RequestBody BaseRequestVo requestVo) {
		this.printResonpseJson(this.getBaseEntityRepository().findListByParams(requestVo.getConditions()));
	}
	
	/**
	 * 分页查询
	 * @param pageVo
	 */
	@PostMapping("/page")
	public void getPage(@Valid @RequestBody BaseRequestPageVo pageVo) {
		this.printResonpseJson(this.getBaseEntityRepository().findPage(pageVo));
	}

}
