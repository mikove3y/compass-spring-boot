package cn.com.compass.demo.service;

import cn.com.compass.base.util.DataXUtil;
import cn.com.compass.base.vo.BaseResponseAppPageVo;
import cn.com.compass.base.vo.BaseResponsePcPageVo;
import cn.com.compass.base.vo.BaseResponseVo;
import cn.com.compass.data.repository.BaseEntityRepository;
import cn.com.compass.data.util.PageTransformUtil;
import cn.com.compass.demo.client.DemoClient;
import cn.com.compass.demo.dto.DemoDTO;
import cn.com.compass.demo.entity.Demo;
import cn.com.compass.demo.entity.QDemo;
import cn.com.compass.demo.mapper.DemoMapper;
import cn.com.compass.demo.vo.AppPageDemoRequestVo;
import cn.com.compass.demo.vo.NewDemoRequestVo;
import cn.com.compass.demo.vo.PcPageDemoRequestVo;
import cn.com.compass.demo.vo.UpdateDemoRequestVo;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.hibernate.annotations.SQLDelete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo
 * @date 2019/4/15 17:12
 */
@Service
@Transactional(readOnly = true)
public class DemoService implements DemoClient {

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
        SQLDelete sqlDelete = Demo.class.getAnnotation(SQLDelete.class);
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
                        QDemo.demo.time,
                        QDemo.demo.name,
                        QDemo.demo._super.createTime
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
                QDemo.demo.time,
                QDemo.demo.name,
                QDemo.demo._super.createTime
        )).from(QDemo.demo)
                .where(QDemo.demo.name.like("%" + vo.getName() + "%").and(QDemo.demo.id.gt((Number & Comparable<?>) vo.getDataId())))
                .orderBy(QDemo.demo.id.asc())
                .offset(0)
                .limit(vo.getPageSize()).fetchResults();
        return BaseResponseVo.success().setData(PageTransformUtil.transformJpaPage2AppPage(page));
    }

    @Override
    public BaseResponseVo<BaseResponsePcPageVo<DemoDTO>> pcPageDemo(PcPageDemoRequestVo vo) throws Exception {
        QueryResults<DemoDTO> page = jpaQueryFactory.select(Projections.fields(
                DemoDTO.class,
                QDemo.demo.id,
                QDemo.demo.business,
                QDemo.demo.time,
                QDemo.demo.name,
                QDemo.demo._super.createTime
        )).from(QDemo.demo)
                .where(QDemo.demo.name.like("%" + vo.getName() + "%"))
                .orderBy(QDemo.demo.id.desc())
                .offset((vo.getPageNo() - 1) * vo.getPageSize())
                .limit(vo.getPageSize())
                .fetchResults();
        return BaseResponseVo.success().setData(PageTransformUtil.transformJpaPage2PcPage(page));
    }
}
