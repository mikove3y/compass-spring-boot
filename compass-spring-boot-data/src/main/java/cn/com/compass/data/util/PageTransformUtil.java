package cn.com.compass.data.util;

import cn.com.compass.base.constant.BaseConstant;
import cn.com.compass.base.exception.BaseException;
import cn.com.compass.base.vo.BaseRequestAppPageVo;
import cn.com.compass.base.vo.BaseResponseAppPageVo;
import cn.com.compass.base.vo.BaseResponsePcPageVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 分页转换工具
 * @date 2018年8月12日 下午3:40:57
 *
 */
@Slf4j
public class PageTransformUtil {
	
	/**
	 * jpa page 转换为AppPage
	 * @param jpaPage
	 * @return
	 */
	public static <T> BaseResponseAppPageVo<T> transformJpaPage2AppPage(
			org.springframework.data.domain.Page<T> jpaPage,BaseRequestAppPageVo pageVo) {
		try {
			BaseResponseAppPageVo<T> result = new BaseResponseAppPageVo<>();
			result.setTotal(jpaPage.getTotalElements());
			result.setRecords(jpaPage.getContent());
			result.setSize(jpaPage.getSize());
			if (CollectionUtils.isNotEmpty(jpaPage.getContent())) {
				Object o = jpaPage.getContent().get(jpaPage.getContent().size()-1);
				String id = BeanUtilsBean2.getInstance().getProperty(o, "id");
				if(id==null) {
					id = BeanUtilsBean2.getInstance().getProperty(o, "dataId");
				}
				result.setDataId(NumberUtils.isCreatable(id)?Long.valueOf(id):-1L);
			}
			if(jpaPage.getContent()==null||jpaPage.getContent().size()<pageVo.getPageSize()) {
				result.setDataId(-1L);
			}
			return result;
		} catch (Exception e) {
			log.error("transformJpaPage2AppPage erro:{}", e);
			throw new BaseException(BaseConstant.INNER_ERRO, "transformJpaPage2AppPage erro :" +e.getMessage());
		}
	}
	
	/**
	 * jpa page 转换为PcPage
	 * @param jpaPage
	 * @return
	 */
	public static <T> BaseResponsePcPageVo<T> transformJpaPage2PcPage(
			org.springframework.data.domain.Page<T> jpaPage) {
		BaseResponsePcPageVo<T> result = new BaseResponsePcPageVo<>();
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
	public static <T> BaseResponseAppPageVo<T> transformMybtaisPage2AppPage(
			com.github.pagehelper.Page<T> mybatisPage,BaseRequestAppPageVo pageVo) {
		try {
			BaseResponseAppPageVo<T> result = new BaseResponseAppPageVo<>();
			result.setRecords(mybatisPage.getResult());
			if(CollectionUtils.isNotEmpty(mybatisPage.getResult())) {
				Object o = mybatisPage.getResult().get(mybatisPage.getResult().size()-1);
				String id = BeanUtilsBean2.getInstance().getProperty(o, "id");
				if(id==null) {
					id = BeanUtilsBean2.getInstance().getProperty(o, "dataId");
				}
				result.setDataId(NumberUtils.isCreatable(id)?Long.valueOf(id):-1L);
			}
			if(mybatisPage.getResult()==null||mybatisPage.getResult().size()<pageVo.getPageSize()) {
				result.setDataId(-1L);
			}
			result.setSize(mybatisPage.getPageSize());
			result.setTotal(mybatisPage.getTotal());
			return result;
		}catch (Exception e) {
			log.error("transformMybtaisPage2AppPage erro:{}", e);
			throw new BaseException(BaseConstant.INNER_ERRO, "transformJpaPage2AppPage erro :" +e.getMessage());
		}
	}
	
	/**
	 * mybatis pageHelper 转换为pcPage
	 * @param mybatisPage
	 * @return
	 */
	public static <T> BaseResponsePcPageVo<T> transformMybtaisPage2PcPage(
			com.github.pagehelper.Page<T> mybatisPage) {
		BaseResponsePcPageVo<T> result = new BaseResponsePcPageVo<>();
		result.setRecords(mybatisPage.getResult());
		result.setCurrent(mybatisPage.getPageNum());
		result.setSize(mybatisPage.getPageSize());
		result.setTotal(mybatisPage.getTotal());
		return result;
	}
}
