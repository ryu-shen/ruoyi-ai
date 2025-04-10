package org.dromara.langchain.common.util;

import org.dromara.langchain.common.domain.dto.QueryPage;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.hutool.core.lang.Dict;

public class MybatisUtil {

	/**
	 * 分页查询：格式化响应数据结构
	 *
	 * @param page 分页数据
	 * @return 格式化后的Map对象
	 */
	public static Dict getData(IPage<?> page) {
		return Dict.create().set("rows", page.getRecords()).set("total", (int) page.getTotal());
	}

	/**
	 * QueryPage对象转换为Page对象
	 */
	public static <T> IPage<T> wrap(T t, QueryPage query) {
		return new Page<T>(query.getPage(), query.getLimit());
	}
}
