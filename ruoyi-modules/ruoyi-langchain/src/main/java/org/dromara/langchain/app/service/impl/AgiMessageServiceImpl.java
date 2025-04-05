
package org.dromara.langchain.app.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.dromara.langchain.app.domain.AgiConversation;
import org.dromara.langchain.app.domain.AgiMessage;
import org.dromara.langchain.app.mapper.AgiConversationMapper;
import org.dromara.langchain.app.mapper.AgiMessageMapper;
import org.dromara.langchain.app.service.AgiMessageService;
import org.dromara.langchain.common.domain.dto.QueryPage;
import org.dromara.langchain.upms.domain.AgiSysUser;
import org.dromara.langchain.upms.mapper.AgiSysUserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AgiMessageServiceImpl extends ServiceImpl<AgiMessageMapper, AgiMessage> implements AgiMessageService {
	private final AgiConversationMapper agiConversationMapper;
	private final AgiSysUserMapper userMapper;

	@Override
	public List<AgiConversation> conversations(String userId) {
		return agiConversationMapper.selectList(Wrappers.<AgiConversation>lambdaQuery()
				.eq(AgiConversation::getUserId, userId).orderByDesc(AgiConversation::getCreateTime));
	}

	@Override
	public IPage<AgiConversation> conversationPages(AgiConversation data, QueryPage queryPage) {
		Page<AgiConversation> page = new Page<>(queryPage.getPage(), queryPage.getLimit());
		Page<AgiConversation> iPage = agiConversationMapper.selectPage(page,
				Wrappers.<AgiConversation>lambdaQuery()
						.like(!StrUtil.isBlank(data.getTitle()), AgiConversation::getTitle, data.getTitle())
						.orderByDesc(AgiConversation::getCreateTime));

		if (!iPage.getRecords().isEmpty()) {
			Map<String, List<AgiSysUser>> map = userMapper.selectList(Wrappers.lambdaQuery()).stream()
					.collect(Collectors.groupingBy(AgiSysUser::getId));
			Set<String> ids = iPage.getRecords().stream().map(AgiConversation::getId).collect(Collectors.toSet());
			List<AgiMessage> messages = baseMapper.selectList(Wrappers.<AgiMessage>lambdaQuery()
					.in(AgiMessage::getConversationId, ids).orderByDesc(AgiMessage::getCreateTime));

			iPage.getRecords().forEach(i -> {
				List<AgiSysUser> list = map.get(i.getUserId());
				if (list != null && !list.isEmpty()) {
					i.setUsername(list.get(0).getUsername());
				}

				List<AgiMessage> messageList = messages.stream()
						.filter(m -> m.getConversationId() != null && m.getConversationId().equals(i.getId())).toList();
				if (!messageList.isEmpty()) {
					i.setChatTotal(messageList.size());
					i.setEndTime(messageList.get(0).getCreateTime());
					i.setTokenUsed(messageList.stream().filter(m -> m.getTokens() != null)
							.mapToInt(AgiMessage::getTokens).sum());
				}
			});
		}
		return iPage;
	}

	@Override
	@Transactional
	public AgiConversation addConversation(AgiConversation conversation) {
		conversation.setCreateTime(new Date());
		agiConversationMapper.insert(conversation);
		return conversation;
	}

	@Override
	@Transactional
	public void updateConversation(AgiConversation conversation) {
		agiConversationMapper
				.updateById(new AgiConversation().setId(conversation.getId()).setTitle(conversation.getTitle()));
	}

	@Override
	@Transactional
	public void delConversation(String conversationId) {
		agiConversationMapper.deleteById(conversationId);
		baseMapper.delete(Wrappers.<AgiMessage>lambdaQuery().eq(AgiMessage::getConversationId, conversationId));
	}

	@Override
	@Transactional
	public AgiMessage addMessage(AgiMessage message) {
		message.setCreateTime(new Date());
		baseMapper.insert(message);
		return message;
	}

	@Override
	@Transactional
	public void clearMessage(String conversationId) {
		baseMapper.delete(Wrappers.<AgiMessage>lambdaQuery().eq(AgiMessage::getConversationId, conversationId));
	}

	@Override
	public List<AgiMessage> getMessages(String conversationId) {
		// 避免页面渲染压力大，只截取最新的20条数据
		return baseMapper
				.selectPage(new Page<>(0, 20), Wrappers.<AgiMessage>lambdaQuery()
						.eq(AgiMessage::getConversationId, conversationId).orderByAsc(AgiMessage::getCreateTime))
				.getRecords();
	}

	@Override
	public List<AgiMessage> getMessages(String conversationId, String userId) {
		// 避免页面渲染压力大，只截取最新的100条数据
		return baseMapper.selectPage(new Page<>(0, 100),
				Wrappers.<AgiMessage>lambdaQuery().eq(AgiMessage::getConversationId, conversationId)
						.eq(AgiMessage::getUserId, userId).orderByAsc(AgiMessage::getCreateTime))
				.getRecords();
	}
}
