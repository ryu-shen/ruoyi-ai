package org.dromara.langchain.platform.service;

import java.util.List;

import org.dromara.langchain.common.domain.dto.QueryPage;
import org.dromara.langchain.platform.domain.AgiModel;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface AgiModelService extends IService<AgiModel> {

	List<AgiModel> getChatModels();

	List<AgiModel> getImageModels();

	List<AgiModel> getEmbeddingModels();

	List<AgiModel> list(AgiModel data);

	Page<AgiModel> page(AgiModel data, QueryPage queryPage);

	AgiModel selectById(String id);
}
