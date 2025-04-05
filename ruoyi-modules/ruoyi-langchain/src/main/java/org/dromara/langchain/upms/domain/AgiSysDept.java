package org.dromara.langchain.upms.domain;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 部门表(Dept)实体类
 *
 * 
 * 
 */
@Data
@TableName(value = "agi_sys_dept", autoResultMap = true)
@Accessors(chain = true)
public class AgiSysDept implements Serializable {
	private static final long serialVersionUID = -94917153262781949L;

	/**
	 * 主键
	 */
	@TableId(type = IdType.ASSIGN_UUID)
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
