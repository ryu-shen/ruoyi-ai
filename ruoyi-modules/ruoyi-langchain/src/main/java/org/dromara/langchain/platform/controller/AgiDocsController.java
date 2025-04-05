package org.dromara.langchain.platform.controller;

import java.util.List;

import org.dromara.langchain.common.domain.dto.QueryPage;
import org.dromara.common.core.domain.Result;
import org.dromara.langchain.common.util.MybatisUtil;
import org.dromara.langchain.platform.domain.AgiDocs;
import org.dromara.langchain.platform.mapper.AgiDocsMapper;
import org.dromara.langchain.platform.service.EmbeddingService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.dev33.satoken.annotation.SaCheckPermission;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/agi/docs")
public class AgiDocsController {

	private final AgiDocsMapper docsMapper;
	private final EmbeddingService embeddingService;

	@GetMapping("/list")
	public Result<List<AgiDocs>> list(AgiDocs data) {
		return Result.ok(docsMapper.selectList(Wrappers.<AgiDocs>lambdaQuery().orderByDesc(AgiDocs::getCreateTime)));
	}

	@GetMapping("/page")
	public Result list(AgiDocs data, QueryPage queryPage) {
		Page<AgiDocs> page = new Page<>(queryPage.getPage(), queryPage.getLimit());
		return Result.ok(MybatisUtil.getData(docsMapper.selectPage(page,
				Wrappers.<AgiDocs>lambdaQuery()
						.eq(data.getKnowledgeId() != null, AgiDocs::getKnowledgeId, data.getKnowledgeId())
						.eq(data.getSliceStatus() != null, AgiDocs::getSliceStatus, data.getSliceStatus())
						.orderByDesc(AgiDocs::getCreateTime))));
	}

	@GetMapping("/{id}")
	public Result<AgiDocs> findById(@PathVariable String id) {
		return Result.ok(docsMapper.selectById(id));
	}

	@PostMapping
	@SaCheckPermission("agi:docs:add")
	public Result add(@RequestBody AgiDocs data) {
		docsMapper.insert(data);
		return Result.ok();
	}

	@PutMapping
	@SaCheckPermission("agi:docs:update")
	public Result update(@RequestBody AgiDocs data) {
		docsMapper.updateById(data);
		return Result.ok();
	}

	@DeleteMapping("/{id}")
	@SaCheckPermission("agi:docs:delete")
	@Transactional
	public Result delete(@PathVariable String id) {
		// 删除切面数据
		embeddingService.clearDocSlices(id);

		// 删除文档
		docsMapper.deleteById(id);
		return Result.ok();
	}
}
