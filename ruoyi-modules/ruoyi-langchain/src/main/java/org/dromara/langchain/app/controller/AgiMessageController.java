package org.dromara.langchain.app.controller;

import org.dromara.langchain.app.domain.AgiMessage;
import org.dromara.langchain.app.service.AgiMessageService;
import org.dromara.langchain.common.domain.dto.QueryPage;
import org.dromara.common.core.domain.Result;
import org.dromara.langchain.common.util.MybatisUtil;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;

@RequestMapping("/agi/message")
@RestController
@AllArgsConstructor
public class AgiMessageController {

	private final AgiMessageService agiMessageService;

	@GetMapping("/page")
	public Result list(AgiMessage data, QueryPage queryPage) {
		LambdaQueryWrapper<AgiMessage> queryWrapper = Wrappers.<AgiMessage>lambdaQuery()
				.like(!StrUtil.isBlank(data.getMessage()), AgiMessage::getMessage, data.getMessage())
				.like(!StrUtil.isBlank(data.getUsername()), AgiMessage::getUsername, data.getUsername())
				.eq(!StrUtil.isBlank(data.getRole()), AgiMessage::getRole, data.getRole())
				.orderByDesc(AgiMessage::getCreateTime);
		IPage<AgiMessage> iPage = agiMessageService.page(MybatisUtil.wrap(data, queryPage), queryWrapper);
		return Result.ok(MybatisUtil.getData(iPage));
	}

	@GetMapping("/{id}")
	public Result getById(@PathVariable String id) {
		return Result.ok(agiMessageService.getById(id));
	}

	@DeleteMapping("/{id}")
	@SaCheckPermission("agi:message:delete")
	public Result del(@PathVariable String id) {
		return Result.ok(agiMessageService.removeById(id));
	}

}
