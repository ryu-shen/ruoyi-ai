package org.dromara.langchain.app.controller;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.dromara.langchain.app.component.AppChannelStore;
import org.dromara.langchain.app.domain.AgiAppApi;
import org.dromara.langchain.app.domain.constant.AppConst;
import org.dromara.langchain.app.service.AgiAppApiService;
import org.dromara.langchain.common.domain.dto.QueryPage;
import org.dromara.common.core.domain.Result;
import org.dromara.langchain.common.util.MybatisUtil;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/agi/app/api")
public class AgiAppApiController {

	private final AgiAppApiService appApiService;
	private final AppChannelStore appChannelStore;

	@GetMapping("/create/{id}/{channel}")
	public Result create(@PathVariable String id, @PathVariable String channel) {
		String uuid = AppConst.PREFIX + IdUtil.simpleUUID();
		AgiAppApi agiAppApi = new AgiAppApi();
		agiAppApi.setAppId(id);
		agiAppApi.setApiKey(uuid);
		agiAppApi.setChannel(channel);
		agiAppApi.setCreateTime(new Date());
		appApiService.save(agiAppApi);
		appChannelStore.init();
		return Result.ok();
	}

	@GetMapping("/list")
	public Result<List<AgiAppApi>> list(AgiAppApi data) {
		List<AgiAppApi> list = appApiService.list(Wrappers.<AgiAppApi>lambdaQuery()
				.eq(StrUtil.isNotBlank(data.getAppId()), AgiAppApi::getAppId, data.getAppId())
				.eq(StrUtil.isNotBlank(data.getChannel()), AgiAppApi::getChannel, data.getChannel())
				.orderByDesc(AgiAppApi::getCreateTime));
		return Result.ok(list);
	}

	@GetMapping("/page")
	public Result<Dict> page(AgiAppApi data, QueryPage queryPage) {
		IPage<AgiAppApi> iPage = appApiService.page(MybatisUtil.wrap(data, queryPage),
				Wrappers.<AgiAppApi>lambdaQuery()
						.like(StringUtils.isNotEmpty(data.getAppId()), AgiAppApi::getAppId, data.getAppId())
						.eq(StrUtil.isNotBlank(data.getChannel()), AgiAppApi::getChannel, data.getChannel())
						.orderByDesc(AgiAppApi::getCreateTime));
		return Result.ok(MybatisUtil.getData(iPage));
	}

	@GetMapping("/{id}")
	public Result<AgiAppApi> findById(@PathVariable String id) {
		AgiAppApi api = appApiService.getById(id);
		return Result.ok(api);
	}

	@PostMapping
	public Result add(@RequestBody AgiAppApi data) {
		data.setCreateTime(new Date());
		appApiService.save(data);
		appChannelStore.init();
		return Result.ok();
	}

	@PutMapping
	public Result update(@RequestBody AgiAppApi data) {
		appApiService.updateById(data);
		appChannelStore.init();
		return Result.ok();
	}

	@DeleteMapping("/{id}")
	public Result delete(@PathVariable String id) {
		appApiService.removeById(id);
		appChannelStore.init();
		return Result.ok();
	}
}