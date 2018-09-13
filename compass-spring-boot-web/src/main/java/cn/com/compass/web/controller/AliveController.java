/**
 * 
 */
package cn.com.compass.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 检查是否服务可用
 * @date 2018年9月12日 下午3:01:29
 * 
 */
@RestController
@RequestMapping("")
public class AliveController {
	
	@GetMapping
	public String imAlive() {
		return "I'm alive!";
	}
	
}
