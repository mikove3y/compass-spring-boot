package cn.com.compass.controller;

import cn.com.compass.base.vo.BaseResponseAppPageVo;
import cn.com.compass.base.vo.BaseResponsePcPageVo;
import cn.com.compass.base.vo.BaseResponseVo;
import cn.com.compass.client.DemoClient;
import cn.com.compass.dto.DemoDTO;
import cn.com.compass.service.DemoService;
import cn.com.compass.vo.AppPageDemoRequestVo;
import cn.com.compass.vo.NewDemoRequestVo;
import cn.com.compass.vo.PcPageDemoRequestVo;
import cn.com.compass.vo.UpdateDemoRequestVo;
import cn.com.compass.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo
 * @date 2019/4/15 17:11
 */
@RestController
@RequestMapping("/demo")
public class DemoController extends BaseController implements DemoClient {

    @Autowired
    private DemoService service;

    @Override
    @PostMapping
    public BaseResponseVo<DemoDTO> newDemo(@RequestBody NewDemoRequestVo vo) throws Exception {
        return service.newDemo(vo);
    }

    @Override
    @DeleteMapping("/{id}")
    public BaseResponseVo deleteDemo(@PathVariable Long id) throws Exception {
        return service.deleteDemo(id);
    }

    @Override
    @PutMapping("/{id}")
    public BaseResponseVo<DemoDTO> updateDemo(@PathVariable Long id, @RequestBody UpdateDemoRequestVo vo) throws Exception {
        return service.updateDemo(id,vo);
    }

    @Override
    @GetMapping("/{id}")
    public BaseResponseVo<DemoDTO> findOneDemo(@PathVariable Long id) throws Exception {
        return service.findOneDemo(id);
    }

    @Override
    @PostMapping("/page/app")
    public BaseResponseVo<BaseResponseAppPageVo<DemoDTO>> appPageDemo(@RequestBody AppPageDemoRequestVo vo) throws Exception {
        return service.appPageDemo(vo);
    }

    @Override
    @PostMapping("/page/pc")
    public BaseResponseVo<BaseResponsePcPageVo<DemoDTO>> pcPageDemo(@RequestBody PcPageDemoRequestVo vo) throws Exception {
        return service.pcPageDemo(vo);
    }
}
