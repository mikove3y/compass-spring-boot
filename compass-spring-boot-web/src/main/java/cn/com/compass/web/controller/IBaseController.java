package cn.com.compass.web.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import cn.com.compass.base.entity.BaseEntity;
import cn.com.compass.base.vo.Page;
import cn.com.compass.web.vo.BaseControllerRequestVo;

/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo feignClient 不支持多层接口实例化，client层统一在eip层级定义
 * @date 2018年7月1日 下午5:02:50
 *
 */
public interface IBaseController<T extends BaseEntity,Rv extends BaseControllerRequestVo> {
	
	
	@PostMapping
	public T addOne(@Valid @RequestBody Rv.AddOne vo);
	
	/**
	 * 批量新增,返回最新数据集
	 * @param entities
	 */
	@PostMapping("/batch")
	public List<T> addBatch(@Valid @RequestBody Rv.AddBatch vo);
	
	/**
	 * 单条删除
	 * @param id
	 */
	@DeleteMapping("/{id}")
	public T deleteOne(@NotNull(message="id can not be null") @PathVariable("id") Long id);
	
	/**
	 * 批量删除
	 * @param ids
	 */
	@DeleteMapping("/batch")
	public List<T> deleteBatch(@Valid @RequestBody Rv.DeleteBatch vo);
	
	/**
	 * 更新单条记录,返回最新数据
	 * @param entity
	 */
	@PutMapping
	public T updateOne(@Valid @RequestBody Rv.UpdateOne vo);
	
	/**
	 * 批量更新,返回最新数据集
	 * @param entities
	 */
	@PutMapping("/batch")
	public List<T> updateBatch(@Valid @RequestBody Rv.UpdateBatch vo);
	
	/**
	 * 查询一条记录
	 * @param id
	 */
	@GetMapping("/{id}")
	public T getOne(@NotNull(message="id can not be null") @PathVariable("id") Long id);
	
	/**
	 * 列表查询
	 * @param params
	 */
	@PostMapping("/list")
	public List<T> getList(@Valid @RequestBody Rv.GetList vo);
	
	/**
	 * 分页查询
	 * @param pageVo
	 */
	@PostMapping("/page")
	public Page<T> getPage(@Valid @RequestBody Rv.GetPage vo);

}
