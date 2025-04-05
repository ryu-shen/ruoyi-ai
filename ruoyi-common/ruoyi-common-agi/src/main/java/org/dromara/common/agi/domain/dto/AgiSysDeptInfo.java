package org.dromara.common.agi.domain.dto;

import java.io.Serializable;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AgiSysDeptInfo implements Serializable {

	private static final long serialVersionUID = -94917153262781949L;

	private String id;

	/**
	 * 上级部门ID
	 */
	private String parentId;

	/**
	 * 部门名称
	 */
	private String name;

	/**
	 * 排序
	 */
	private Integer orderNo;

	/**
	 * 描述
	 */
	private String des;
}
