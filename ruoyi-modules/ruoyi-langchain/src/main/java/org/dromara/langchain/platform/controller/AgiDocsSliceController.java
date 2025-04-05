package org.dromara.langchain.platform.controller;

import java.util.Date;
import java.util.List;

import org.dromara.langchain.common.domain.dto.QueryPage;
import org.dromara.common.core.domain.Result;
import org.dromara.langchain.common.util.MybatisUtil;
import org.dromara.langchain.platform.domain.AgiDocsSlice;
import org.dromara.langchain.platform.mapper.AgiDocsSliceMapper;
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
@RequestMapping("/agi/docs/slice")
public class AgiDocsSliceController {

	private final AgiDocsSliceMapper docsSliceMapper;

	@GetMapping("/list")
	public Result<List<AgiDocsSlice>> list(AgiDocsSlice data) {
		return Result.ok(docsSliceMapper
				.selectList(Wrappers.<AgiDocsSlice>lambdaQuery().orderByDesc(AgiDocsSlice::getCreateTime)));
	}

	@GetMapping("/page")
	public Result list(AgiDocsSlice data, QueryPage queryPage) {
		Page<AgiDocsSlice> page = new Page<>(queryPage.getPage(), queryPage.getLimit());
		return Result.ok(MybatisUtil.getData(docsSliceMapper.selectPage(page,
				Wrappers.<AgiDocsSlice>lambdaQuery()
						.eq(data.getKnowledgeId() != null, AgiDocsSlice::getKnowledgeId, data.getKnowledgeId())
						.eq(data.getDocsId() != null, AgiDocsSlice::getDocsId, data.getDocsId())
						.orderByDesc(AgiDocsSlice::getCreateTime))));
	}

	@GetMapping("/{id}")
	public Result<AgiDocsSlice> findById(@PathVariable String id) {
		return Result.ok(docsSliceMapper.selectById(id));
	}

	@PostMapping
	@SaCheckPermission("agi:docs:slice:add")
	public Result add(@RequestBody AgiDocsSlice data) {
		data.setCreateTime(new Date());
		docsSliceMapper.insert(data);
		return Result.ok();
	}

	@PutMapping
	@SaCheckPermission("agi:docs:slice:update")
	public Result update(@RequestBody AgiDocsSlice data) {
		docsSliceMapper.updateById(data);
		return Result.ok();
	}

	@DeleteMapping("/{id}")
	@SaCheckPermission("agi:docs:slice:delete")
	public Result delete(@PathVariable String id) {
		docsSliceMapper.deleteById(id);
		return Result.ok();
	}
}
