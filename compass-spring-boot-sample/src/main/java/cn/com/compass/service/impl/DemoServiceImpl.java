package cn.com.compass.service.impl;

import cn.com.compass.base.util.DataXUtil;
import cn.com.compass.base.vo.BaseResponseAppPageVo;
import cn.com.compass.base.vo.BaseResponsePcPageVo;
import cn.com.compass.base.vo.BaseResponseVo;
import cn.com.compass.data.repository.BaseEntityRepository;
import cn.com.compass.dto.DemoDTO;
import cn.com.compass.entity.Demo;
import cn.com.compass.entity.QDemo;
import cn.com.compass.mapper.DemoMapper;
import cn.com.compass.service.DemoService;
import cn.com.compass.vo.AppPageDemoRequestVo;
import cn.com.compass.vo.NewDemoRequestVo;
import cn.com.compass.vo.PcPageDemoRequestVo;
import cn.com.compass.vo.UpdateDemoRequestVo;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo
 * @date 2019/4/15 17:12
 */
@Service
@Transactional(readOnly = true)
public class DemoServiceImpl  implements DemoService {

    @Autowired
    private DemoMapper mapper;
    @Autowired
    private BaseEntityRepository<Demo,Long> repository;
    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponseVo<DemoDTO> newDemo(NewDemoRequestVo vo) throws Exception {
        Demo demo = (Demo) DataXUtil.copyProperties(vo, Demo.class, vo.source2TargetProperties(), true);
        repository.save(demo);
        return BaseResponseVo.success().setData((DemoDTO) DataXUtil.copyProperties(demo, DemoDTO.class, null, true));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponseVo deleteDemo(Long id) throws Exception {
        repository.delete(id);
        return BaseResponseVo.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponseVo<DemoDTO> updateDemo(Long id, UpdateDemoRequestVo vo) throws Exception {
        Demo demo = repository.findOne(id);
        DataXUtil.copyProperties(vo, demo, vo.source2TargetProperties(), true);
        repository.save(demo);
        return BaseResponseVo.success().setData((DemoDTO) DataXUtil.copyProperties(demo, DemoDTO.class, null, true));
    }

    @Override
    public BaseResponseVo<DemoDTO> findOneDemo(Long id) throws Exception {
        // 参考: https://www.jianshu.com/p/5c416a780b3e
        // attention here!!!
        // 使用JpaQueryFactory DTO类不能使用lombok中的@Buidler注解 会出现 Class com.querydsl.core.types.QBean can not access a member of class cn.com.compass.dto.DemoDTO with modifiers private 错误
        DemoDTO dto = jpaQueryFactory
                .select(Projections.bean(
                        DemoDTO.class,
                        QDemo.demo.id,
                        QDemo.demo.business,
                        QDemo.demo.time
                ))
                .from(QDemo.demo)
                .where(QDemo.demo.id.eq(id))
                .fetchOne();
        return BaseResponseVo.success().setData(dto);
    }

    @Override
    public BaseResponseVo<BaseResponseAppPageVo<DemoDTO>> appPageDemo(AppPageDemoRequestVo vo) throws Exception {
        QueryResults<DemoDTO> page = jpaQueryFactory.select(Projections.fields(
                DemoDTO.class,
                QDemo.demo.id,
                QDemo.demo.business,
                QDemo.demo.time
        )).from(QDemo.demo)
                .where(QDemo.demo.name.like("%" + vo.getName() + "%").and(QDemo.demo.id.gt((Number & Comparable<?>) vo.getDataId())))
                .orderBy(QDemo.demo.id.asc())
                .offset(0)
                .limit(vo.getPageSize()).fetchResults();
        BaseResponseAppPageVo<DemoDTO> pageVo = new BaseResponseAppPageVo<>(Long.valueOf(page.getOffset()).intValue(), Long.valueOf(page.getLimit()).intValue(), page.getTotal(), page.getResults(), null);
        return BaseResponseVo.success().setData(pageVo);
    }

    @Override
    public BaseResponseVo<BaseResponsePcPageVo<DemoDTO>> pcPageDemo(PcPageDemoRequestVo vo) throws Exception {
        QueryResults<DemoDTO> page = jpaQueryFactory.select(Projections.fields(
                DemoDTO.class,
                QDemo.demo.id,
                QDemo.demo.business,
                QDemo.demo.time
        )).from(QDemo.demo)
                .where(QDemo.demo.name.like("%" + vo.getName() + "%"))
                .orderBy(QDemo.demo.id.desc())
                .offset((vo.getPageNo() - 1) * vo.getPageSize())
                .limit(vo.getPageSize())
                .fetchResults();
        BaseResponsePcPageVo<DemoDTO> pageVo = new BaseResponsePcPageVo<>(Long.valueOf(page.getOffset()).intValue(), Long.valueOf(page.getLimit()).intValue(), page.getTotal(), page.getResults(), null);
        return BaseResponseVo.success().setData(pageVo);
    }
}
