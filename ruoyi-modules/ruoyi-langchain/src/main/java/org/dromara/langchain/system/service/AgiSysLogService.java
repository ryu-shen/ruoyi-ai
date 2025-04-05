package org.dromara.langchain.system.service;

import org.dromara.langchain.common.domain.dto.QueryPage;
import org.dromara.langchain.system.domain.AgiSysLog;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 系统日志表(Log)表服务接口
 *
 * 
 * 
 */
public interface AgiSysLogService extends IService<AgiSysLog> {

	/**
	 * 分页、条件查询
	 */
	IPage<AgiSysLog> list(AgiSysLog sysLog, QueryPage queryPage);

	/**
	 * 新增
	 */
	void add(AgiSysLog sysLog);

	/**
	 * 删除
	 */
	void delete(String id);
}
