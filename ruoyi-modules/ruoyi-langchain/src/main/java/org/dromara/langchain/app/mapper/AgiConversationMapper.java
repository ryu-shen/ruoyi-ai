package org.dromara.langchain.app.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.dromara.langchain.app.domain.AgiConversation;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

@Mapper
public interface AgiConversationMapper extends BaseMapper<AgiConversation> {

}
