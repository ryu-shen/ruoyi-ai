package org.dromara.langchain.system.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.dromara.langchain.system.domain.AgiSysLog;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 系统日志表(Log)表数据库访问层
 *
 * 
 * 
 */
@Mapper
public interface AgiSysLogMapper extends BaseMapper<AgiSysLog> {

}
