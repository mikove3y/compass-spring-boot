package cn.com.compass.demo.client;

import cn.com.compass.base.constant.BaseConstant;
import cn.com.compass.base.exception.BaseException;
import cn.com.compass.base.vo.BaseResponseAppPageVo;
import cn.com.compass.base.vo.BaseResponsePcPageVo;
import cn.com.compass.base.vo.BaseResponseVo;
import cn.com.compass.constant.CommonConstant;
import cn.com.compass.demo.dto.DemoDTO;
import cn.com.compass.demo.vo.AppPageDemoRequestVo;
import cn.com.compass.demo.vo.NewDemoRequestVo;
import cn.com.compass.demo.vo.PcPageDemoRequestVo;
import cn.com.compass.demo.vo.UpdateDemoRequestVo;
import org.springframework.stereotype.Component;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo
 * @date 2019/5/2 20:46
 */
@Component
public class DemoClientFallback implements DemoClient {
    @Override
    public BaseResponseVo<DemoDTO> newDemo(NewDemoRequestVo vo) throws Exception {
        throw new BaseException(BaseConstant.SERVER_NOT_AVAILABLE,String.format("server of %s is not available", CommonConstant.SERVER_ID));
    }

    @Override
    public BaseResponseVo deleteDemo(Long id) throws Exception {
        throw new BaseException(BaseConstant.SERVER_NOT_AVAILABLE,String.format("server of %s is not available", CommonConstant.SERVER_ID));
    }

    @Override
    public BaseResponseVo<DemoDTO> updateDemo(Long id, UpdateDemoRequestVo vo) throws Exception {
        throw new BaseException(BaseConstant.SERVER_NOT_AVAILABLE,String.format("server of %s is not available", CommonConstant.SERVER_ID));
    }

    @Override
    public BaseResponseVo<DemoDTO> findOneDemo(Long id) throws Exception {
        return BaseResponseVo.success();
    }

    @Override
    public BaseResponseVo<BaseResponseAppPageVo<DemoDTO>> appPageDemo(AppPageDemoRequestVo vo) throws Exception {
        return BaseResponseVo.success();
    }

    @Override
    public BaseResponseVo<BaseResponsePcPageVo<DemoDTO>> pcPageDemo(PcPageDemoRequestVo vo) throws Exception {
        return BaseResponseVo.success();
    }
}
