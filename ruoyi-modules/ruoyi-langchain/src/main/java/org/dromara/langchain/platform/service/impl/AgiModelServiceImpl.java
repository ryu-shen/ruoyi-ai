package org.dromara.langchain.platform.service.impl;

import java.util.List;

import org.dromara.langchain.common.domain.dto.QueryPage;
import org.dromara.langchain.platform.domain.AgiModel;
import org.dromara.langchain.platform.domain.enums.ModelTypeEnum;
import org.dromara.langchain.platform.mapper.AgiModelMapper;
import org.dromara.langchain.platform.service.AgiModelService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AgiModelServiceImpl extends ServiceImpl<AgiModelMapper, AgiModel> implements AgiModelService {

	@Override
	public List<AgiModel> getChatModels() {
		List<AgiModel> list = baseMapper
				.selectList(Wrappers.<AgiModel>lambdaQuery().eq(AgiModel::getType, ModelTypeEnum.CHAT.name()));
		list.forEach(this::hide);
		return list;
	}

	@Override
	public List<AgiModel> getImageModels() {
		List<AgiModel> list = baseMapper
				.selectList(Wrappers.<AgiModel>lambdaQuery().eq(AgiModel::getType, ModelTypeEnum.TEXT_IMAGE.name()));
		list.forEach(this::hide);
		return list;
	}

	@Override
	public List<AgiModel> getEmbeddingModels() {
		List<AgiModel> list = baseMapper
				.selectList(Wrappers.<AgiModel>lambdaQuery().eq(AgiModel::getType, ModelTypeEnum.EMBEDDING.name()));
		list.forEach(this::hide);
		return list;
	}

	@Override
	public List<AgiModel> list(AgiModel data) {
		List<AgiModel> list = this.list(Wrappers.<AgiModel>lambdaQuery()
				.eq(StrUtil.isNotBlank(data.getType()), AgiModel::getType, data.getType())
				.eq(StrUtil.isNotBlank(data.getProvider()), AgiModel::getProvider, data.getProvider()));
		list.forEach(this::hide);
		return list;
	}

	@Override
	public Page<AgiModel> page(AgiModel data, QueryPage queryPage) {
		Page<AgiModel> page = new Page<>(queryPage.getPage(), queryPage.getLimit());
		Page<AgiModel> iPage = this.page(page,
				Wrappers.<AgiModel>lambdaQuery().eq(AgiModel::getProvider, data.getProvider()));
		iPage.getRecords().forEach(this::hide);
		return iPage;
	}

	@Override
	public AgiModel selectById(String id) {
		AgiModel model = this.getById(id);
		hide(model);
		return model;
	}

	private void hide(AgiModel model) {
		if (model == null || StrUtil.isBlank(model.getApiKey())) {
			return;
		}
		String key = StrUtil.hide(model.getApiKey(), 3, model.getApiKey().length() - 4);
		model.setApiKey(key);

		if (StrUtil.isBlank(model.getSecretKey())) {
			return;
		}
		String sec = StrUtil.hide(model.getSecretKey(), 3, model.getSecretKey().length() - 4);
		model.setSecretKey(sec);
	}
}
