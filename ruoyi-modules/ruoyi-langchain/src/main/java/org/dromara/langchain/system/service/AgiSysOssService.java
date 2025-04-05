
package org.dromara.langchain.system.service;

import org.dromara.langchain.system.domain.AgiSysOss;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.extension.service.IService;

public interface AgiSysOssService extends IService<AgiSysOss> {

	AgiSysOss upload(MultipartFile file, String userId);
}
