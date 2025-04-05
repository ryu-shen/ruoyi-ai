package org.dromara.langchain.system.controller;

import java.util.List;

import org.dromara.common.core.domain.Result;
import org.dromara.common.satoken.utils.AgiLoginHelperUtil;
import org.dromara.langchain.system.domain.AgiSysOss;
import org.dromara.langchain.system.service.AgiSysOssService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import lombok.AllArgsConstructor;

@RequestMapping("/system/oss")
@RestController
@AllArgsConstructor
public class AgiSysOssController {

	private final AgiSysOssService agiOssService;

	@GetMapping("/list")
	public Result list() {
		List<AgiSysOss> list = agiOssService.list(Wrappers.<AgiSysOss>lambdaQuery()
				.eq(AgiSysOss::getUserId, AgiLoginHelperUtil.getUserId()).orderByDesc(AgiSysOss::getCreateTime));
		return Result.ok(list);
	}

	@PostMapping("/upload")
	public Result upload(MultipartFile file) {
		return Result.ok(agiOssService.upload(file, String.valueOf(AgiLoginHelperUtil.getUserId())));
	}

	@PutMapping
	public Result update(@RequestBody AgiSysOss data) {
		agiOssService.updateById(data);
		return Result.ok();
	}

	@DeleteMapping("/{id}")
	public Result delete(@PathVariable String id) {
		agiOssService.removeById(id);
		return Result.ok();
	}
}
