package cn.com.compass.client;

import cn.com.compass.base.vo.BaseResponseAppPageVo;
import cn.com.compass.base.vo.BaseResponsePcPageVo;
import cn.com.compass.base.vo.BaseResponseVo;
import cn.com.compass.dto.DemoDTO;
import cn.com.compass.vo.AppPageDemoRequestVo;
import cn.com.compass.vo.NewDemoRequestVo;
import cn.com.compass.vo.PcPageDemoRequestVo;
import cn.com.compass.vo.UpdateDemoRequestVo;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo
 * @date 2019/4/15 17:33
 */
public interface DemoClient {

    public BaseResponseVo<DemoDTO> newDemo(NewDemoRequestVo vo) throws Exception;

    public BaseResponseVo deleteDemo(Long id) throws Exception;

    public BaseResponseVo<DemoDTO> updateDemo(Long id, UpdateDemoRequestVo vo) throws Exception;

    public BaseResponseVo<DemoDTO> findOneDemo(Long id) throws Exception;

    public BaseResponseVo<BaseResponseAppPageVo<DemoDTO>> appPageDemo(AppPageDemoRequestVo vo) throws Exception;

    public BaseResponseVo<BaseResponsePcPageVo<DemoDTO>> pcPageDemo(PcPageDemoRequestVo vo) throws Exception;
}
