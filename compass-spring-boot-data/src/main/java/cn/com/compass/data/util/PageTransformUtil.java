package cn.com.compass.data.util;

import org.apache.commons.collections.CollectionUtils;

import cn.com.compass.base.entity.BaseEntity;
import cn.com.compass.base.vo.AppPage;
import cn.com.compass.base.vo.PcPage;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 分页转换工具
 * @date 2018年8月12日 下午3:40:57
 *
 */
public class PageTransformUtil {
	
	/**
	 * jpa page 转换为AppPage
	 * @param jpaPage
	 * @return
	 */
	public static <T extends BaseEntity> AppPage<T> transformJpaPage2AppPage(
			org.springframework.data.domain.Page<T> jpaPage) {
		AppPage<T> result = new AppPage<>();
		result.setTotal(jpaPage.getTotalElements());
		result.setRecords(jpaPage.getContent());
		result.setSize(jpaPage.getSize());
		if (CollectionUtils.isNotEmpty(jpaPage.getContent())) {
			result.setDataId(jpaPage.getContent().get(0).getId());
		}
		return result;
	}
	
	/**
	 * jpa page 转换为PcPage
	 * @param jpaPage
	 * @return
	 */
	public static <T extends BaseEntity> PcPage<T> transformJpaPage2PcPage(
			org.springframework.data.domain.Page<T> jpaPage) {
		PcPage<T> result = new PcPage<>();
		result.setTotal(jpaPage.getTotalElements());
		result.setRecords(jpaPage.getContent());
		result.setCurrent(jpaPage.getNumber());
		result.setSize(jpaPage.getSize());
		return result;
	}
	
	/**
	 * mybatis pageHelper 转换为appPage
	 * @param mybatisPage
	 * @return
	 */
	public static <T extends BaseEntity> AppPage<T> transformMybtaisPage2AppPage(
			com.github.pagehelper.Page<T> mybatisPage) {
		AppPage<T> result = new AppPage<>();
		result.setRecords(mybatisPage.getResult());
		if(CollectionUtils.isNotEmpty(mybatisPage.getResult())) {
			result.setDataId(mybatisPage.getResult().get(0).getId());
		}
		result.setSize(mybatisPage.getPageSize());
		result.setTotal(mybatisPage.getTotal());
		return result;
	}
	
	/**
	 * mybatis pageHelper 转换为pcPage
	 * @param mybatisPage
	 * @return
	 */
	public static <T extends BaseEntity> PcPage<T> transformMybtaisPage2PcPage(
			com.github.pagehelper.Page<T> mybatisPage) {
		PcPage<T> result = new PcPage<>();
		result.setRecords(mybatisPage.getResult());
		result.setCurrent(mybatisPage.getPageNum());
		result.setSize(mybatisPage.getPageSize());
		result.setTotal(mybatisPage.getTotal());
		return result;
	}
}
