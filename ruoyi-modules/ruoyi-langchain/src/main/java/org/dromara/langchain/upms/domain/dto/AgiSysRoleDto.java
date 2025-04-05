package org.dromara.langchain.upms.domain.dto;

import java.util.List;

import org.dromara.langchain.upms.domain.AgiSysRole;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * SysRole DTO封装
 *
 * 
 * 
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class AgiSysRoleDto extends AgiSysRole {
	private static final long serialVersionUID = -5792577217091151552L;

	/**
	 * 菜单ID集合
	 */
	private List<String> menuIds;
}
