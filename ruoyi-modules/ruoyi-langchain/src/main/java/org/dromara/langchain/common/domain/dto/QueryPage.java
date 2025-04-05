package org.dromara.langchain.common.domain.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QueryPage implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 当前页
	 */
	private int page = 1;

	/**
	 * 每页的记录数
	 */
	private int limit = 10;
}
