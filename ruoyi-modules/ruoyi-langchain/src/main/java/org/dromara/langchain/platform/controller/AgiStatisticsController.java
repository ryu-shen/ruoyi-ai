package org.dromara.langchain.platform.controller;

import org.dromara.langchain.app.mapper.AgiAppMapper;
import org.dromara.langchain.app.mapper.AgiMessageMapper;
import org.dromara.common.core.domain.Result;
import org.dromara.langchain.platform.mapper.AgiKnowledgeMapper;
import org.dromara.langchain.upms.mapper.AgiSysUserMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import cn.hutool.core.lang.Dict;
import lombok.AllArgsConstructor;

@RequestMapping("/agi/statistic")
@RestController
@AllArgsConstructor
public class AgiStatisticsController {

	private final AgiMessageMapper aigcMessageMapper;
	private final AgiSysUserMapper userMapper;
	private final AgiKnowledgeMapper aigcKnowledgeMapper;
	private final AgiAppMapper aigcAppMapper;

	@GetMapping("/requestBy30")
	public Result request30Chart() {
		return Result.ok(aigcMessageMapper.getReqChartBy30());
	}

	@GetMapping("/tokenBy30")
	public Result token30Chart() {
		return Result.ok(aigcMessageMapper.getTokenChartBy30());
	}

	@GetMapping("/token")
	public Result tokenChart() {
		return Result.ok(aigcMessageMapper.getTokenChart());
	}

	@GetMapping("/request")
	public Result requestChart() {
		return Result.ok(aigcMessageMapper.getReqChart());
	}

	@GetMapping("/home")
	public Result home() {
		Dict reqData = aigcMessageMapper.getCount();
		Dict totalData = aigcMessageMapper.getTotalSum();
		Dict userData = userMapper.getCount();
		Long totalKnowledge = aigcKnowledgeMapper.selectCount(Wrappers.query());
		Long totalPrompt = aigcAppMapper.selectCount(Wrappers.query());
		Dict result = Dict.create();
		result.putAll(reqData);
		result.putAll(totalData);
		result.putAll(userData);
		result.set("totalKnowledge", totalKnowledge.intValue()).set("totalPrompt", totalPrompt.intValue());
		return Result.ok(result);
	}
}
