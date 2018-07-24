package cn.com.compass.base.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import cn.com.compass.base.vo.BaseVueTreeVo;

/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 
 * @date 2018年7月24日 上午10:27:19
 *
 */
public class VueTreeUtil {
	
	public static List<BaseVueTreeVo> build(Long rootNodeId,String rootlabel) {
		//根节点，默认为root -1
		List<BaseVueTreeVo> tree = new ArrayList<>();
		BaseVueTreeVo root = null;
		if(rootNodeId!=null&&StringUtils.isNotEmpty(rootlabel)) {
			 root = new BaseVueTreeVo(rootNodeId, rootlabel);
		}else {
			root = new BaseVueTreeVo(-1L, "root");
		}
		tree.add(root);
		return tree;
	}
}
