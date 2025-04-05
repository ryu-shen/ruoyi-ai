
package org.dromara.langchain.platform.service.impl;

import java.util.Date;
import java.util.List;

import org.dromara.langchain.platform.domain.AgiDocs;
import org.dromara.langchain.platform.domain.AgiDocsSlice;
import org.dromara.langchain.platform.domain.AgiKnowledge;
import org.dromara.langchain.platform.mapper.AgiDocsMapper;
import org.dromara.langchain.platform.mapper.AgiDocsSliceMapper;
import org.dromara.langchain.platform.mapper.AgiKnowledgeMapper;
import org.dromara.langchain.platform.service.AgiKnowledgeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AgiKnowledgeServiceImpl extends ServiceImpl<AgiKnowledgeMapper, AgiKnowledge>
		implements AgiKnowledgeService {

	private final AgiDocsMapper agiDocsMapper;
	private final AgiDocsSliceMapper agiDocsSliceMapper;

	@Override
	@Transactional
	public void addDocs(AgiDocs data) {
		data.setCreateTime(new Date());
		agiDocsMapper.insert(data);
	}

	@Override
	@Transactional
	public void updateDocs(AgiDocs data) {
		agiDocsMapper.updateById(data);
	}

	@Override
	@Transactional
	public void addDocsSlice(AgiDocsSlice data) {
		data.setCreateTime(new Date());
		data.setWordNum(data.getContent().length());
		data.setStatus(true);
		agiDocsSliceMapper.insert(data);
	}

	@Override
	@Transactional
	public void updateDocsSlice(AgiDocsSlice data) {
		agiDocsSliceMapper.updateById(data);
	}

	@Override
	public List<String> listSliceVectorIdsOfDoc(String docsId) {
		LambdaQueryWrapper<AgiDocsSlice> selectWrapper = Wrappers.<AgiDocsSlice>lambdaQuery()
				.select(AgiDocsSlice::getVectorId).eq(AgiDocsSlice::getDocsId, docsId);
		List<String> vectorIds = agiDocsSliceMapper.selectList(selectWrapper).stream().map(AgiDocsSlice::getVectorId)
				.toList();
		log.debug("slices of doc: [{}], count: [{}]", docsId, vectorIds.size());
		return vectorIds;
	}

	@Override
	public List<AgiDocs> getDocsByKb(String knowledgeId) {
		return agiDocsMapper.selectList(Wrappers.<AgiDocs>lambdaQuery().eq(AgiDocs::getKnowledgeId, knowledgeId));
	}

	@Override
	@Transactional
	public void removeKnowledge(String knowledgeId) {
		baseMapper.deleteById(knowledgeId);
		// del docs & docsSlice
		List<String> docsIds = getDocsByKb(knowledgeId).stream().map(AgiDocs::getId).toList();
		docsIds.forEach(this::removeSlicesOfDoc);
	}

	@Override
	@Transactional
	public void removeSlicesOfDoc(String docsId) {
		LambdaQueryWrapper<AgiDocsSlice> deleteWrapper = Wrappers.<AgiDocsSlice>lambdaQuery()
				.eq(AgiDocsSlice::getDocsId, docsId);
		int count = agiDocsSliceMapper.delete(deleteWrapper);
		log.debug("remove all slices of doc: [{}], count: [{}]", docsId, count);
	}
}
