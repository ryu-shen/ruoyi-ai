package org.dromara.common.agi.domain.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AgiSysUserInfo implements Serializable {

	private static final long serialVersionUID = -94827981963832107L;

	/**
	 * 主键
	 */
	private String id;

	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 真实姓名
	 */
	private String realName;

	/**
	 * 性别
	 */
	private String sex;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 部门ID
	 */
	private String deptId;

	/**
	 * 头像
	 */
	private String avatar;

	/**
	 * 手机
	 */
	private String phone;

	/**
	 * 状态 0锁定 1有效
	 */
	private Boolean status;

	/**
	 * 创建时间
	 */
	private Date createTime;
}
