package cn.com.compass.service.impl;

import cn.com.compass.base.vo.BaseResponseAppPageVo;
import cn.com.compass.base.vo.BaseResponsePcPageVo;
import cn.com.compass.base.vo.BaseResponseVo;
import cn.com.compass.dto.DemoDTO;
import cn.com.compass.entity.Demo;
import cn.com.compass.mapper.DemoMapper;
import cn.com.compass.repository.DemoRepository;
import cn.com.compass.service.DemoService;
import cn.com.compass.util.DataXUtil;
import cn.com.compass.vo.AppPageDemoRequestVo;
import cn.com.compass.vo.NewDemoRequestVo;
import cn.com.compass.vo.PcPageDemoRequestVo;
import cn.com.compass.vo.UpdateDemoRequestVo;
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
public class DemoServiceImpl implements DemoService {

    @Autowired
    private DemoMapper mapper;
    @Autowired
    private DemoRepository repository;
    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponseVo<DemoDTO> newDemo(NewDemoRequestVo vo) throws Exception {
        Demo demo = (Demo) DataXUtil.copyProperties(vo, Demo.class, vo.source2TargetProperties());
        demo.persist();
//        BaseResponseVo.success().setData(demo);
//        repository.saveOne(demo);
        return BaseResponseVo.success().setData((DemoDTO) DataXUtil.copyProperties(demo,DemoDTO.class,null));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponseVo deleteDemo(Long id) throws Exception {
        repository.deleteById(id);
        return BaseResponseVo.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponseVo<DemoDTO> updateDemo(Long id,UpdateDemoRequestVo vo) throws Exception {
        Demo demo = repository.findById(id);
        DataXUtil.copyProperties(vo,demo,vo.source2TargetProperties());
        repository.updateOne(demo);
        return BaseResponseVo.success().setData((DemoDTO) DataXUtil.copyProperties(demo,DemoDTO.class,null));
    }

    @Override
    public BaseResponseVo<DemoDTO> findOneDemo(Long id) throws Exception {
        return BaseResponseVo.success().setData((DemoDTO) DataXUtil.copyProperties(repository.findById(id),DemoDTO.class,null));
    }

    @Override
    public BaseResponseVo<BaseResponseAppPageVo<DemoDTO>> appPageDemo(AppPageDemoRequestVo vo) throws Exception {
        Specification<Demo> spec = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                return cb.like(root.get("name"),"%"+vo.getName()+"%");
            }
        };
        return BaseResponseVo.success().setData(repository.findAppPage(vo,spec));
    }

    @Override
    public BaseResponseVo<BaseResponsePcPageVo<DemoDTO>> pcPageDemo(PcPageDemoRequestVo vo) throws Exception {
        Specification<Demo> spec = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                return cb.like(root.get("name"),"%"+vo.getName()+"%");
            }
        };
        return BaseResponseVo.success().setData(repository.findPcPage(vo,spec));
    }
}
