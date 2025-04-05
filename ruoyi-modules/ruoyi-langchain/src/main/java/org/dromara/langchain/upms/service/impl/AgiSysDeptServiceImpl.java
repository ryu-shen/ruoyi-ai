package org.dromara.langchain.upms.service.impl;

import java.util.List;

import org.dromara.common.core.exception.AgiServiceException;
import org.dromara.langchain.upms.domain.AgiSysDept;
import org.dromara.langchain.upms.mapper.AgiSysDeptMapper;
import org.dromara.langchain.upms.service.AgiSysDeptService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import lombok.RequiredArgsConstructor;

/**
 * 部门表(Dept)表服务实现类
 *
 * 
 * 
 */
@Service
@RequiredArgsConstructor
public class AgiSysDeptServiceImpl extends ServiceImpl<AgiSysDeptMapper, AgiSysDept> implements AgiSysDeptService {

	@Override
	public List<AgiSysDept> list(AgiSysDept sysDept) {
		return baseMapper.selectList(new LambdaQueryWrapper<AgiSysDept>().orderByAsc(AgiSysDept::getOrderNo));
	}

	@Override
	public List<Tree<Object>> tree(AgiSysDept sysDept) {
		List<AgiSysDept> sysDeptList = baseMapper.selectList(
				new LambdaQueryWrapper<AgiSysDept>().ne(sysDept.getId() != null, AgiSysDept::getId, sysDept.getId()));

		// 构建树形结构
		List<TreeNode<Object>> nodeList = CollUtil.newArrayList();
		sysDeptList.forEach(t -> {
			TreeNode<Object> node = new TreeNode<>(t.getId(), t.getParentId(), t.getName(), 0);
			node.setExtra(Dict.create().set("orderNo", t.getOrderNo()).set("des", t.getDes()));
			nodeList.add(node);
		});
		return TreeUtil.build(nodeList, "0");
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delete(String id) {
		List<AgiSysDept> list = baseMapper
				.selectList(new LambdaQueryWrapper<AgiSysDept>().eq(AgiSysDept::getParentId, id));
		if (!list.isEmpty()) {
			throw new AgiServiceException("该部门包含子节点，不能删除");
		}
		baseMapper.deleteById(id);
	}
}
