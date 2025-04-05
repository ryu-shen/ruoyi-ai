package org.dromara.langchain.system.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.dromara.langchain.common.domain.dto.QueryPage;
import org.dromara.langchain.common.util.MybatisUtil;
import org.dromara.langchain.system.domain.AgiSysLog;
import org.dromara.langchain.system.mapper.AgiSysLogMapper;
import org.dromara.langchain.system.service.AgiSysLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import lombok.RequiredArgsConstructor;

/**
 * 系统日志表(Log)表服务实现类
 *
 */
@Service
@RequiredArgsConstructor
public class AgiSysLogServiceImpl extends ServiceImpl<AgiSysLogMapper, AgiSysLog> implements AgiSysLogService {

	@Override
	public IPage<AgiSysLog> list(AgiSysLog sysLog, QueryPage queryPage) {
		return baseMapper.selectPage(MybatisUtil.wrap(sysLog, queryPage), Wrappers.<AgiSysLog>lambdaQuery()
				.eq(sysLog.getType() != null, AgiSysLog::getType, sysLog.getType())
				.like(StringUtils.isNotEmpty(sysLog.getOperation()), AgiSysLog::getOperation, sysLog.getOperation())
				.orderByDesc(AgiSysLog::getCreateTime));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void add(AgiSysLog sysLog) {
		baseMapper.insert(sysLog);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delete(String id) {
		baseMapper.deleteById(id);
	}
}
