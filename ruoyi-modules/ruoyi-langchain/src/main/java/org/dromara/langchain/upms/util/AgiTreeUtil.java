package org.dromara.langchain.upms.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.dromara.langchain.upms.domain.AgiSysMenu;
import org.dromara.langchain.upms.domain.dto.AgiMenuTree;

/**
 * 
 * 
 */
public class AgiTreeUtil {

	private static List<AgiMenuTree<AgiSysMenu>> init(List<AgiSysMenu> list) {
		List<AgiMenuTree<AgiSysMenu>> treeList = new ArrayList<>();
		list.forEach(menu -> {
			AgiMenuTree<AgiSysMenu> tree = new AgiMenuTree<>();
			tree.setId(menu.getId());
			tree.setParentId(menu.getParentId());
			tree.setName(menu.getName());
			tree.setPath(menu.getPath());
			tree.setType(menu.getType());
			tree.setComponent(menu.getComponent());
			tree.setPerms(menu.getPerms());
			tree.setMeta(new AgiMenuTree.MenuMeta(menu.getName(), menu.getIcon()));
			tree.setOrderNo(menu.getOrderNo());
			tree.setDisabled(menu.getIsDisabled());
			tree.setIsExt(menu.getIsExt());
			tree.setKeepalive(menu.getIsKeepalive());
			tree.setShow(menu.getIsShow());
			treeList.add(tree);
		});
		return treeList.stream().sorted(Comparator.comparing(AgiMenuTree::getOrderNo)).collect(Collectors.toList());
	}

	public static List<AgiMenuTree<AgiSysMenu>> build(List<AgiSysMenu> list) {
		List<AgiMenuTree<AgiSysMenu>> nodes = init(list);
		List<AgiMenuTree<AgiSysMenu>> tree = new ArrayList<>();
		nodes.forEach(node -> {
			String pid = node.getParentId();
			if (node.getIsExt()) {
				node.setComponent("Layout");
				node.setName(node.getPath());
			}
			if (pid == null || pid.equals("0")) {
				// 父级节点
				tree.add(node);
				return;
			}
			for (AgiMenuTree<AgiSysMenu> c : nodes) {
				String id = c.getId();
				if (id != null && id.equals(pid)) {
					c.getChildren().add(node);
					return;
				}
			}
		});
		return tree;
	}
}
