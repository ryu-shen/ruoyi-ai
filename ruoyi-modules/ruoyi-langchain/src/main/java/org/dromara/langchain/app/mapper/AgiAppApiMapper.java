package org.dromara.langchain.app.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.dromara.langchain.app.domain.AgiAppApi;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

@Mapper
public interface AgiAppApiMapper extends BaseMapper<AgiAppApi> {
}
