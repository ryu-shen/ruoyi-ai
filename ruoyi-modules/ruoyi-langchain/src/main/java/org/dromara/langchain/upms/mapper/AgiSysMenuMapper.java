package org.dromara.langchain.upms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.dromara.langchain.upms.domain.AgiSysMenu;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 菜单表(Menu)表数据库访问层
 *
 * 
 * 
 */
@Mapper
public interface AgiSysMenuMapper extends BaseMapper<AgiSysMenu> {

	List<AgiSysMenu> build(@Param("roleIds") List<String> roleIds, @Param("type") String type);
}
