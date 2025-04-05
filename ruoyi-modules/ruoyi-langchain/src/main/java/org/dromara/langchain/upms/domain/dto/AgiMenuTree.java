package org.dromara.langchain.upms.domain.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 按照前端路由格式封装
 *
 * 
 * 
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AgiMenuTree<T> implements Serializable {
	private static final long serialVersionUID = 547891924677981054L;

	/**
	 * 节点ID
	 */
	private String id;

	/**
	 * 父节点ID
	 */
	private String parentId;

	/**
	 * 路由名称
	 */
	private String name;

	/**
	 * 路由地址
	 */
	private String path;

	/**
	 * 类型
	 */
	private String type;

	/**
	 * 组件路径
	 */
	private String component;

	/**
	 * 权限标识
	 */
	private String perms;

	/**
	 * 重定向地址
	 */
	private String redirect;

	/**
	 * icon && title 信息
	 */
	private MenuMeta meta;

	/**
	 * 排序
	 */
	private Integer orderNo;

	/**
	 * 是否禁用
	 */
	private Boolean disabled;

	/**
	 * 是否外链
	 */
	private Boolean isExt;

	/**
	 * 是否缓存
	 */
	private Boolean keepalive;

	/**
	 * 是否缓存
	 */
	private Boolean show;

	/**
	 * 子节点
	 */
	private List<AgiMenuTree<T>> children = new ArrayList<>();

	@Data
	public static class MenuMeta {
		private String title;
		private String icon;

		public MenuMeta(String title, String icon) {
			this.title = title;
			this.icon = icon;
		}
	}
}
