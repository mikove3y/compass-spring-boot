package cn.com.compass.service.impl;

import cn.com.compass.base.vo.BaseResponseAppPageVo;
import cn.com.compass.base.vo.BaseResponsePcPageVo;
import cn.com.compass.base.vo.BaseResponseVo;
import cn.com.compass.data.service.ActiveBaseEntityServiceImpl;
import cn.com.compass.dto.DemoDTO;
import cn.com.compass.entity.Demo;
import cn.com.compass.entity.QDemo;
import cn.com.compass.mapper.DemoMapper;
import cn.com.compass.repository.DemoRepository;
import cn.com.compass.service.DemoService;
import cn.com.compass.util.DataXUtil;
import cn.com.compass.vo.AppPageDemoRequestVo;
import cn.com.compass.vo.NewDemoRequestVo;
import cn.com.compass.vo.PcPageDemoRequestVo;
import cn.com.compass.vo.UpdateDemoRequestVo;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo
 * @date 2019/4/15 17:12
 */
@Service
@Transactional(readOnly = true)
public class DemoServiceImpl extends ActiveBaseEntityServiceImpl<Demo,Long> implements DemoService {

    @Autowired
    private DemoMapper mapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponseVo<DemoDTO> newDemo(NewDemoRequestVo vo) throws Exception {
        Demo demo = (Demo) DataXUtil.copyProperties(vo, Demo.class, vo.source2TargetProperties());
        this.saveOne(demo);
        return BaseResponseVo.success().setData((DemoDTO) DataXUtil.copyProperties(demo,DemoDTO.class,null));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponseVo deleteDemo(Long id) throws Exception {
        this.deleteById(id);
        return BaseResponseVo.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponseVo<DemoDTO> updateDemo(Long id,UpdateDemoRequestVo vo) throws Exception {
        Demo demo = this.findById(id);
        DataXUtil.copyProperties(vo,demo,vo.source2TargetProperties());
        this.updateOne(demo);
        return BaseResponseVo.success().setData((DemoDTO) DataXUtil.copyProperties(demo,DemoDTO.class,null));
    }

    @Override
    public BaseResponseVo<DemoDTO> findOneDemo(Long id) throws Exception {
        // 参考: https://www.jianshu.com/p/5c416a780b3e
        QDemo _Q_Demo = QDemo.demo;
        DemoDTO dto =  this.jpaQueryFactory()
                .select(Projections.bean(
                        DemoDTO.class,
                        _Q_Demo.id.as("xid"),
                        _Q_Demo.business,
                        _Q_Demo.time
                ))
                .from(_Q_Demo)
                .where(_Q_Demo.id.eq(id))
                .fetchOne();
       return BaseResponseVo.success().setData(dto);
    }

    @Override
    public BaseResponseVo<BaseResponseAppPageVo<DemoDTO>> appPageDemo(AppPageDemoRequestVo vo) throws Exception {
        Specification<Demo> spec = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                return cb.like(root.get("name"),"%"+vo.getName()+"%");
            }
        };
        return BaseResponseVo.success().setData(this.findAppPage(vo,spec));
    }

    @Override
    public BaseResponseVo<BaseResponsePcPageVo<DemoDTO>> pcPageDemo(PcPageDemoRequestVo vo) throws Exception {
        Specification<Demo> spec = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                return cb.like(root.get("name"),"%"+vo.getName()+"%");
            }
        };
        return BaseResponseVo.success().setData(this.findPcPage(vo,spec));
    }
}
