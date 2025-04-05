
package org.dromara.langchain.app.service.impl;

import org.dromara.langchain.app.domain.AgiAppApi;
import org.dromara.langchain.app.mapper.AgiAppApiMapper;
import org.dromara.langchain.app.service.AgiAppApiService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

@Service
public class AgiAppApiServiceImpl extends ServiceImpl<AgiAppApiMapper, AgiAppApi> implements AgiAppApiService {

}
