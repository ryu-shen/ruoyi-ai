package org.dromara.langchain.app.service;

import java.util.List;

import org.dromara.langchain.app.domain.AgiApp;

import com.baomidou.mybatisplus.extension.service.IService;

public interface AgiAppService extends IService<AgiApp> {

	List<AgiApp> list(AgiApp data);

	AgiApp getById(String id);
}
