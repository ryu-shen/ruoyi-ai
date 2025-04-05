package org.dromara.langchain.upms.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.dromara.langchain.upms.domain.AgiSysDept;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 部门表(Dept)表数据库访问层
 *
 * 
 * 
 */
@Mapper
public interface AgiSysDeptMapper extends BaseMapper<AgiSysDept> {

}
