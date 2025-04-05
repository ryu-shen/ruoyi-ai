package org.dromara.langchain.upms.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.dromara.common.core.exception.AgiServiceException;
import org.dromara.common.satoken.utils.AgiLoginHelperUtil;
import org.dromara.langchain.upms.domain.AgiSysMenu;
import org.dromara.langchain.upms.domain.AgiSysRole;
import org.dromara.langchain.upms.domain.constant.MenuConst;
import org.dromara.langchain.upms.domain.dto.AgiMenuTree;
import org.dromara.langchain.upms.mapper.AgiSysMenuMapper;
import org.dromara.langchain.upms.service.AgiSysMenuService;
import org.dromara.langchain.upms.service.AgiSysRoleMenuService;
import org.dromara.langchain.upms.util.AgiTreeUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import lombok.RequiredArgsConstructor;

/**
 * 菜单表(Menu)表服务实现类
 * 
 */
@Service
@RequiredArgsConstructor
public class AgiSysMenuServiceImpl extends ServiceImpl<AgiSysMenuMapper, AgiSysMenu> implements AgiSysMenuService {

	private final AgiSysRoleMenuService sysRoleMenuService;

	@Override
	public List<AgiMenuTree<AgiSysMenu>> tree(AgiSysMenu sysMenu) {
		List<AgiSysMenu> list = baseMapper.selectList(
				new LambdaQueryWrapper<AgiSysMenu>().ne(sysMenu.getId() != null, AgiSysMenu::getId, sysMenu.getId())
						.eq(sysMenu.getIsDisabled() != null, AgiSysMenu::getIsDisabled, sysMenu.getIsDisabled()));
		return AgiTreeUtil.build(list);
	}

	@Override
//    @Cacheable(value = CacheConst.MENU_DETAIL_KEY, key = "#userId")
	public List<AgiMenuTree<AgiSysMenu>> build(String userId) {
		List<String> roleIds = AgiLoginHelperUtil.getRoleIds();
		if (AgiLoginHelperUtil.getRoleNames().contains(AgiLoginHelperUtil.ADMINISTRATOR)) {
			// 超级管理员，不做权限过滤
			roleIds.clear();
		} else {
			if (roleIds.isEmpty()) {
				return new ArrayList<>();
			}
		}
		List<AgiSysMenu> sysMenuList = baseMapper.build(roleIds, MenuConst.MENU_TYPE_MENU);
		List<AgiMenuTree<AgiSysMenu>> build = AgiTreeUtil.build(sysMenuList);
		build.forEach(i -> {
			if (i.getChildren() == null || i.getChildren().isEmpty()) {
				// 对没有children的路由单独处理，前端要求至少有一个children，当children.length=1时自动转换成跟路由
				AgiMenuTree<AgiSysMenu> child = new AgiMenuTree<AgiSysMenu>().setPath("index").setName(i.getName())
						.setComponent(i.getComponent()).setMeta(i.getMeta());
				i.setChildren(Collections.singletonList(child));
				i.setComponent(MenuConst.LAYOUT);
			}
		});
		return build;
	}

	@Override
	public List<AgiSysMenu> getUserMenuList(List<AgiSysRole> sysRoleList) {
		List<String> roleIds = sysRoleList.stream().map(AgiSysRole::getId).collect(Collectors.toList());
		return baseMapper.build(roleIds, null);
	}

	@Override
	public List<AgiSysMenu> list(AgiSysMenu sysMenu) {
		return baseMapper.selectList(new LambdaQueryWrapper<AgiSysMenu>()
				.like(sysMenu.getName() != null, AgiSysMenu::getName, sysMenu.getName())
				.eq(sysMenu.getIsDisabled() != null, AgiSysMenu::getIsDisabled, sysMenu.getIsDisabled())
				.orderByAsc(AgiSysMenu::getOrderNo));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
//    @CacheEvict(value = CacheConst.MENU_DETAIL_KEY, allEntries = true)
	public void add(AgiSysMenu sysMenu) {
		this.format(sysMenu);
		baseMapper.insert(sysMenu);
	}

	private void format(AgiSysMenu sysMenu) {
		if (MenuConst.MENU_TYPE_MENU.equals(sysMenu.getType())) {
			if (sysMenu.getPath() == null) {
				throw new AgiServiceException("[path]参数不能为空");
			}
			if (sysMenu.getIcon() == null)
				sysMenu.setIcon(MenuConst.MENU_ICON);
			if (sysMenu.getIsDisabled() == null)
				sysMenu.setIsDisabled(false);
			if (sysMenu.getIsExt() == null)
				sysMenu.setIsExt(false);
			if (sysMenu.getIsKeepalive() == null)
				sysMenu.setIsKeepalive(true);
			if (sysMenu.getIsShow() == null)
				sysMenu.setIsShow(true);
			if (sysMenu.getParentId() == null)
				sysMenu.setParentId("0");

			if (sysMenu.getComponent().contains("Layout")) {
				sysMenu.setParentId("0");
			}

			if (sysMenu.getParentId() == null || sysMenu.getParentId().equals("0")) {
				// 父级节点
				if (sysMenu.getComponent() == null) {
					sysMenu.setComponent(MenuConst.LAYOUT);
				}
				if (!sysMenu.getPath().toLowerCase().startsWith("http") && !sysMenu.getPath().startsWith("/")) {
					sysMenu.setPath("/" + sysMenu.getPath());
				}
			} else {
				// 子节点
				if (sysMenu.getPath().startsWith("/")) {
					sysMenu.setPath(sysMenu.getPath().substring(1));
				}
				if (!sysMenu.getIsExt() && !sysMenu.getComponent().startsWith("/")) {
					sysMenu.setComponent("/" + sysMenu.getComponent());
				}
			}
		}
		if (MenuConst.MENU_TYPE_BUTTON.equals(sysMenu.getType())) {
			sysMenu.setPath(null);
			sysMenu.setIcon(null);
			sysMenu.setComponent(null);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
//    @CacheEvict(value = CacheConst.MENU_DETAIL_KEY, allEntries = true)
	public void update(AgiSysMenu sysMenu) {
		this.format(sysMenu);
		baseMapper.updateById(sysMenu);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
//    @CacheEvict(value = CacheConst.MENU_DETAIL_KEY, allEntries = true)
	public void delete(String id) {
		List<AgiSysMenu> list = baseMapper
				.selectList(new LambdaQueryWrapper<AgiSysMenu>().eq(AgiSysMenu::getParentId, id));
		if (!list.isEmpty()) {
			throw new AgiServiceException("该菜单包含子节点，不能删除");
		}
		sysRoleMenuService.deleteRoleMenusByMenuId(id);
		baseMapper.deleteById(id);
	}
}
