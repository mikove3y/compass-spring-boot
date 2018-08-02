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
import cn.com.compass.base.vo.BaseDataX;
import cn.com.compass.base.vo.BaseRequestAppPageVo;
import cn.com.compass.base.vo.BaseRequestPcPageVo;
import cn.com.compass.base.vo.Page;
import cn.com.compass.web.vo.BaseAddBatchRequestVo;
import cn.com.compass.web.vo.BaseAddOneRequestVo;
import cn.com.compass.web.vo.BaseDeleteBatchRequestVo;
import cn.com.compass.web.vo.BaseUpdateBatchRequestVo;
import cn.com.compass.web.vo.BaseUpdateOneRequestVo;

/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo feignClient 不支持多层接口实例化，client层统一在eip层级定义
 * @date 2018年7月1日 下午5:02:50
 *
 */
public interface IBaseController<T extends BaseEntity> {
	
	
	@PostMapping
	public <A extends BaseAddOneRequestVo> T addOne(@Valid @RequestBody A vo);
	
	/**
	 * 批量新增,返回最新数据集
	 * @param entities
	 */
	@PostMapping("/batch")
	public <B extends BaseAddBatchRequestVo<BaseAddOneRequestVo>> List<T> addBatch(@Valid @RequestBody B vo);
	
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
	public List<T> deleteBatch(@Valid @RequestBody BaseDeleteBatchRequestVo vo);
	
	/**
	 * 更新单条记录,返回最新数据
	 * @param entity
	 */
	@PutMapping
	public <C extends BaseUpdateOneRequestVo> T updateOne(@Valid @RequestBody C vo);
	
	/**
	 * 批量更新,返回最新数据集
	 * @param entities
	 */
	@PutMapping("/batch")
	public <D extends BaseUpdateBatchRequestVo<BaseUpdateOneRequestVo>> List<T> updateBatch(@Valid @RequestBody D vo);
	
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
	public <E extends BaseDataX> List<T> getList(@Valid @RequestBody E vo);
	
	/**
	 * pc分页查询
	 * @param pageVo
	 */
	@PostMapping("/page/pc")
	public <F extends BaseRequestPcPageVo> Page<T> getPcPage(@Valid @RequestBody F vo);
	
	/**
	 * app分页查询
	 * @param pageVo
	 */
	@PostMapping("/page/app")
	public <G extends BaseRequestAppPageVo> Page<T> getAppPage(@Valid @RequestBody G vo);

}
