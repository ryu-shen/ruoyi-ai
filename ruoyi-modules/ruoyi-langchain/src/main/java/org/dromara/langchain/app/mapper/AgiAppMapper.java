package org.dromara.langchain.app.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.dromara.langchain.app.domain.AgiApp;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

@Mapper
public interface AgiAppMapper extends BaseMapper<AgiApp> {
}
