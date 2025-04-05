package org.dromara.langchain.upms.domain;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 用户表(User)实体类
 *
 * 
 * 
 */
@Data
@TableName(value = "agi_sys_user", autoResultMap = true)
@Accessors(chain = true)
public class AgiSysUser implements Serializable {
	private static final long serialVersionUID = -94827981963832107L;

	/**
	 * 主键
	 */
	@TableId(type = IdType.ASSIGN_UUID)
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
