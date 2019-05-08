package cn.com.compass.demo.client;

import cn.com.compass.base.vo.BaseResponseAppPageVo;
import cn.com.compass.base.vo.BaseResponsePcPageVo;
import cn.com.compass.base.vo.BaseResponseVo;
import cn.com.compass.constant.CommonConstant;
import cn.com.compass.demo.dto.DemoDTO;
import cn.com.compass.demo.vo.AppPageDemoRequestVo;
import cn.com.compass.demo.vo.NewDemoRequestVo;
import cn.com.compass.demo.vo.PcPageDemoRequestVo;
import cn.com.compass.demo.vo.UpdateDemoRequestVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo
 * @date 2019/4/15 17:33
 */
@FeignClient(value = CommonConstant.SERVER_ID,path = "/demo",fallback = DemoClientFallback.class)
public interface DemoClient {

    @PostMapping
    public BaseResponseVo<DemoDTO> newDemo(@RequestBody NewDemoRequestVo vo) throws Exception;

    @DeleteMapping("/{id}")
    public BaseResponseVo deleteDemo(@PathVariable Long id) throws Exception;

    @PutMapping("/{id}")
    public BaseResponseVo<DemoDTO> updateDemo(@PathVariable Long id, @RequestBody UpdateDemoRequestVo vo) throws Exception;

    @GetMapping("/{id}")
    public BaseResponseVo<DemoDTO> findOneDemo(@PathVariable Long id) throws Exception;

    @PostMapping("/page/app")
    public BaseResponseVo<BaseResponseAppPageVo<DemoDTO>> appPageDemo(@RequestBody AppPageDemoRequestVo vo) throws Exception;

    @PostMapping("/page/pc")
    public BaseResponseVo<BaseResponsePcPageVo<DemoDTO>> pcPageDemo(@RequestBody PcPageDemoRequestVo vo) throws Exception;
}
