package org.dromara.langchain.upms.domain;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 用户角色关联表(UserRole)实体类
 *
 */
@Data
@TableName(value = "agi_sys_user_role", autoResultMap = true)
@Accessors(chain = true)
public class AgiSysUserRole implements Serializable {
	private static final long serialVersionUID = -24775118196826771L;

	/**
	 * 用户ID
	 */
	private String userId;

	/**
	 * 角色ID
	 */
	private String roleId;
}
