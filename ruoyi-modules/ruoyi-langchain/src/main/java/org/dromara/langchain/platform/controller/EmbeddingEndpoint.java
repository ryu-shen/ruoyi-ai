package org.dromara.langchain.platform.controller;

import java.util.concurrent.Executors;

import org.dromara.common.core.domain.Result;
import org.dromara.common.core.exception.AgiServiceException;
import org.dromara.common.satoken.utils.AgiLoginHelperUtil;
import org.dromara.langchain.common.domain.dto.ChatReq;
import org.dromara.langchain.common.domain.dto.EmbeddingR;
import org.dromara.langchain.common.task.TaskManager;
import org.dromara.langchain.platform.domain.AgiDocs;
import org.dromara.langchain.platform.domain.AgiDocsSlice;
import org.dromara.langchain.platform.domain.constant.EmbedConst;
import org.dromara.langchain.platform.mapper.AgiDocsMapper;
import org.dromara.langchain.platform.service.AgiEmbeddingService;
import org.dromara.langchain.platform.service.AgiKnowledgeService;
import org.dromara.langchain.platform.service.EmbeddingService;
import org.dromara.langchain.system.domain.AgiSysOss;
import org.dromara.langchain.system.service.AgiSysOssService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/agi/embedding")
public class EmbeddingEndpoint {

	private final AgiEmbeddingService langEmbeddingService;
	private final AgiKnowledgeService agiKnowledgeService;
	private final AgiDocsMapper agiDocsMapper;
	private final AgiSysOssService agiOssService;
	private final EmbeddingService embeddingService;

	@PostMapping("/text")
	@SaCheckPermission("agi:embedding:text")
	public Result text(@RequestBody AgiDocs data) {
		if (StrUtil.isBlankIfStr(data.getContent())) {
			throw new AgiServiceException("文档内容不能为空");
		}
		if (StrUtil.isBlank(data.getId())) {
			agiKnowledgeService.addDocs(data);
		}
		data.setType(EmbedConst.ORIGIN_TYPE_INPUT).setSliceStatus(false);

		try {
			EmbeddingR embeddingR = langEmbeddingService.embeddingText(new ChatReq().setMessage(data.getContent())
					.setDocsName(data.getType()).setDocsId(data.getId()).setKnowledgeId(data.getKnowledgeId()));

			agiKnowledgeService.addDocsSlice(new AgiDocsSlice().setKnowledgeId(data.getKnowledgeId())
					.setDocsId(data.getId()).setVectorId(embeddingR.getVectorId()).setName(data.getName())
					.setContent(embeddingR.getText()));

			agiKnowledgeService.updateDocs(new AgiDocs().setId(data.getId()).setSliceStatus(true).setSliceNum(1));
		} catch (Exception e) {
			e.printStackTrace();
			agiKnowledgeService.removeSlicesOfDoc(data.getId());
		}
		return Result.ok();
	}

	@PostMapping("/docs/{knowledgeId}")
	@SaCheckPermission("agi:embedding:docs")
	public Result docs(MultipartFile file, @PathVariable String knowledgeId) {
		String userId = String.valueOf(AgiLoginHelperUtil.getUserId());
		AgiSysOss oss = agiOssService.upload(file, userId);
		AgiDocs data = new AgiDocs().setName(oss.getOriginalFilename()).setSliceStatus(false).setUrl(oss.getUrl())
				.setSize(oss.getSize()).setType(EmbedConst.ORIGIN_TYPE_UPLOAD).setKnowledgeId(knowledgeId);
		agiKnowledgeService.addDocs(data);
		TaskManager.submitTask(userId, Executors.callable(() -> {
			embeddingService.embedDocsSlice(data, oss.getUrl());
		}));
		return Result.ok();
	}

	@GetMapping("/re-embed/{docsId}")
	public Result reEmbed(@PathVariable String docsId) {
		String userId = String.valueOf(AgiLoginHelperUtil.getUserId());
		AgiDocs docs = agiDocsMapper.selectById(docsId);
		if (docs == null) {
			throw new AgiServiceException("没有查询到文档数据");
		}
		if (EmbedConst.ORIGIN_TYPE_INPUT.equals(docs.getType())) {
			text(docs);
		}
		if (EmbedConst.ORIGIN_TYPE_UPLOAD.equals(docs.getType())) {
			embeddingService.clearDocSlices(docsId);
			TaskManager.submitTask(userId, Executors.callable(() -> {
				embeddingService.embedDocsSlice(docs, docs.getUrl());
			}));
		}
		return Result.ok();
	}

	@PostMapping("/search")
	public Result search(@RequestBody AgiDocs data) {
		return Result.ok(embeddingService.search(data));
	}
}
