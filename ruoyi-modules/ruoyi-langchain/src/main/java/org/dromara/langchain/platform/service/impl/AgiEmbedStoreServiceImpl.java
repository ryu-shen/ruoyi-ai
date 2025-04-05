
package org.dromara.langchain.platform.service.impl;

import org.dromara.langchain.platform.domain.AgiEmbedStore;
import org.dromara.langchain.platform.mapper.AgiEmbedStoreMapper;
import org.dromara.langchain.platform.service.AgiEmbedStoreService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AgiEmbedStoreServiceImpl extends ServiceImpl<AgiEmbedStoreMapper, AgiEmbedStore>
		implements AgiEmbedStoreService {

}
