package cn.com.compass.base.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;

import cn.com.compass.base.entity.BaseLevelTreeEntity;
import cn.com.compass.base.vo.BaseLevelTreeResponseVo;
import cn.com.compass.util.DataXUtil;

/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 层级树转换工具类
 * @date 2018年8月14日 下午12:20:19
 *
 */
public class LevelTreeUtil {

	/**
	 * 转层级树实体为层级树响应vo
	 * 
	 * @param treeList
	 * @param treeRsClassz
	 * @return
	 * @throws Exception
	 */
	public static <T extends BaseLevelTreeEntity, V extends BaseLevelTreeResponseVo<V>> List<V> transform(
			List<T> treeList, Class<V> treeRsClassz) throws Exception {
		// 从root根节点一直循环到最底层
		V rootNode = treeRsClassz.newInstance();
		List<V> rootChild = new ArrayList<>();
		rootNode.setChild(rootChild);
		rootNode.setParentId(0L);
		rootNode.setLevel(0);
		rootNode.setSortNum(0);
		rootNode.setCode("root");
		rootNode.setName("根节点");
		// 循环获取
		if (CollectionUtils.isNotEmpty(treeList)) {
			push(rootNode, treeRsClassz, treeList);
		}
		return rootNode.getChild();
	}
	
	/**
	 * 
	 * @param node
	 * @param treeRsClassz
	 * @param treeList
	 * @throws Exception
	 */
	private static <T extends BaseLevelTreeEntity, V extends BaseLevelTreeResponseVo<V>> void push(V node, Class<V> treeRsClassz, List<T> treeList)
			throws Exception {
		// 获取父节点的child
		List<T> childTList = treeList.stream().filter(t->node.getId()==t.getParentId()).sorted(Comparator.comparing(T::getSortNum)).collect(Collectors.toList());
		// node 塞入child
		List<V> childVList = new ArrayList<>();
		for (T l : childTList) {
			V ltrsTemp = treeRsClassz.newInstance();
			DataXUtil.copyProperties(l, ltrsTemp, null);
			childVList.add(ltrsTemp);
			push(ltrsTemp, treeRsClassz, treeList);
		}
		node.setChild(childVList);
	}

}
