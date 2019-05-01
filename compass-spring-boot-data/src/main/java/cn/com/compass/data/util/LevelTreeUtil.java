package cn.com.compass.data.util;

import cn.com.compass.base.util.DataXUtil;
import cn.com.compass.base.vo.BaseLevelTreeResponseVo;
import cn.com.compass.data.entity.BaseLevelTreeEntity;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
	 * @param treeList 实体树
	 * @param isSortAsc 是否升序排列
	 * @param treeRsClassz 树响应类名
	 * @return
	 * @throws Exception
	 */
	public static <T extends BaseLevelTreeEntity, V extends BaseLevelTreeResponseVo<V>> List<V> transform(
			V rootNode,List<T> treeList, boolean isSortAsc, Class<V> treeRsClassz) throws Exception {
		// 从root根节点一直循环到最底层
		if(rootNode==null) {
			rootNode = treeRsClassz.newInstance();
			List<V> rootChild = new ArrayList<>();
			rootNode.setChild(rootChild);
			rootNode.setParentId(0L);
			rootNode.setId(0L);
			rootNode.setLevel(0);
			rootNode.setSortNum(0);
			rootNode.setCode("root");
			rootNode.setName("根节点");
		}
		// 循环获取
		if (CollectionUtils.isNotEmpty(treeList)) {
			push(rootNode, treeRsClassz, isSortAsc, treeList);
		}
		return rootNode.getChild();
	}

	/**
	 * 循环迭代
	 * @param node 父节点
	 * @param treeRsClassz 树响应类名
	 * @param isSortAsc 是否升序
	 * @param treeList 实体树
	 * @throws Exception
	 */
	private static <T extends BaseLevelTreeEntity, V extends BaseLevelTreeResponseVo<V>> void push(V node,
			Class<V> treeRsClassz, boolean isSortAsc, List<T> treeList) throws Exception {
		// 获取父节点的child
		List<T> childTList = new ArrayList<>();
		if (isSortAsc) {
			childTList = treeList.stream().filter(t -> node.getId().compareTo(t.getParentId()) == 0)
			.sorted(Comparator.comparing(T::getSortNum)).collect(Collectors.toList());
		}else {
			childTList = treeList.stream().filter(t -> node.getId().compareTo(t.getParentId()) == 0)
			.sorted(Comparator.comparing(T::getSortNum).reversed()).collect(Collectors.toList());
		}
		// node 塞入child
		List<V> childVList = new ArrayList<>();
		for (T l : childTList) {
			V ltrsTemp = (V) DataXUtil.copyProperties(l, treeRsClassz, null,false);
			childVList.add(ltrsTemp);
			push(ltrsTemp, treeRsClassz, isSortAsc, treeList);
		}
		node.setChild(childVList);
	}

}
