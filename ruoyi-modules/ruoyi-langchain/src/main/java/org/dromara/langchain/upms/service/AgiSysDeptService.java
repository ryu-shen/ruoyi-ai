package org.dromara.langchain.upms.service;

import java.util.List;

import org.dromara.langchain.upms.domain.AgiSysDept;

import com.baomidou.mybatisplus.extension.service.IService;

import cn.hutool.core.lang.tree.Tree;

/**
 * 部门表(Dept)表服务接口
 *
 * 
 * 
 */
public interface AgiSysDeptService extends IService<AgiSysDept> {

	List<AgiSysDept> list(AgiSysDept sysDept);

	List<Tree<Object>> tree(AgiSysDept sysDept);

	void delete(String id);

}
