package cn.com.compass.data.util;

import cn.com.compass.base.constant.BaseConstant;
import cn.com.compass.base.exception.BaseException;
import cn.com.compass.base.vo.BaseRequestAppPageVo;
import cn.com.compass.base.vo.BaseResponseAppPageVo;
import cn.com.compass.base.vo.BaseResponsePcPageVo;
import com.querydsl.core.QueryResults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 分页转换工具
 * @date 2018年8月12日 下午3:40:57
 */
@Slf4j
public class PageTransformUtil {

    /**
     * jpa page 转换为AppPage
     *
     * @param jpaPage
     * @return
     */
    public static <T> BaseResponseAppPageVo<T> transformJpaPage2AppPage(org.springframework.data.domain.Page<T> jpaPage) {
        try {
            BaseResponseAppPageVo<T> result = new BaseResponseAppPageVo<>();
            result.setTotal(jpaPage.getTotalElements());
            List<T> records = jpaPage.getContent();
            result.setRecords(jpaPage.getContent());
            Integer size = jpaPage.getSize();
            result.setSize(size);
            setDataId(result, records, size);
            return result;
        } catch (Exception e) {
            log.error("transformJpaPage2AppPage erro:{}", e);
            throw new BaseException(BaseConstant.INNER_ERRO, "transformJpaPage2AppPage erro :" + e.getMessage());
        }
    }

    /**
     * jpa page 转换为PcPage
     *
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
     * jpa page 转换为AppPage
     *
     * @param queryResults
     * @return
     */
    public static <T> BaseResponseAppPageVo<T> transformJpaPage2AppPage(QueryResults<T> queryResults) {
        try {
            BaseResponseAppPageVo<T> result = new BaseResponseAppPageVo<>();
            result.setTotal(queryResults.getTotal());
            List<T> records = queryResults.getResults();
            result.setRecords(records);
            Integer size = Long.valueOf(queryResults.getLimit()).intValue();
            result.setSize(size);
            setDataId(result, records, size);
            return result;
        } catch (Exception e) {
            log.error("transformJpaPage2AppPage erro:{}", e);
            throw new BaseException(BaseConstant.INNER_ERRO, "transformJpaPage2AppPage erro :" + e.getMessage());
        }
    }


    /**
     * jpa page 转换为PcPage
     *
     * @param queryResults
     * @return
     */
    public static <T> BaseResponsePcPageVo<T> transformJpaPage2PcPage(QueryResults<T> queryResults) {
        BaseResponsePcPageVo<T> result = new BaseResponsePcPageVo<>();
        result.setTotal(queryResults.getTotal());
        result.setRecords(queryResults.getResults());
        result.setCurrent(Long.valueOf(queryResults.getOffset()).intValue());
        result.setSize(Long.valueOf(queryResults.getLimit()).intValue());
        return result;
    }

    /**
     * mybatis pageHelper 转换为appPage
     *
     * @param mybatisPage
     * @return
     */
    public static <T> BaseResponseAppPageVo<T> transformMybtaisPage2AppPage(
            com.github.pagehelper.Page<T> mybatisPage, BaseRequestAppPageVo pageVo) {
        try {
            BaseResponseAppPageVo<T> result = new BaseResponseAppPageVo<>();
            List<T> records = mybatisPage.getResult();
            result.setRecords(mybatisPage.getResult());
            Integer size = mybatisPage.getPageSize();
            result.setSize(size);
            result.setTotal(mybatisPage.getTotal());
            setDataId(result, records, size);
            return result;
        } catch (Exception e) {
            log.error("transformMybtaisPage2AppPage erro:{}", e);
            throw new BaseException(BaseConstant.INNER_ERRO, "transformJpaPage2AppPage erro :" + e.getMessage());
        }
    }

    /**
     * mybatis pageHelper 转换为pcPage
     *
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

    /**
     * 设置DataId
     *
     * @param pageVo
     * @param records
     * @param size
     * @param <T>
     * @throws Exception
     */
    private static <T> void setDataId(BaseResponseAppPageVo<T> pageVo, List<T> records, Integer size) throws Exception {
        if (CollectionUtils.isNotEmpty(records)) {
            Object o = records.get(records.size() - 1);
            String id = BeanUtilsBean2.getInstance().getProperty(o, "id");
            if (id == null) {
                id = BeanUtilsBean2.getInstance().getProperty(o, "dataId");
            }
            pageVo.setDataId(id == null ? -1 : id);
        }
        if (CollectionUtils.isEmpty(records) || records.size() < size) {
            pageVo.setDataId(-1);
        }
    }
}
