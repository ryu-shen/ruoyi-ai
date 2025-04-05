
package org.dromara.langchain.system.service.impl;

import java.util.Date;

import org.dromara.langchain.system.domain.AgiSysOss;
import org.dromara.langchain.system.mapper.AgiSysOssMapper;
import org.dromara.langchain.system.service.AgiSysOssService;
import org.dromara.system.oss.domain.vo.SysOssVo;
import org.dromara.system.oss.service.ISysOssService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AgiSysOssServiceImpl extends ServiceImpl<AgiSysOssMapper, AgiSysOss> implements AgiSysOssService {

	private final ISysOssService ossService;

	@Override
	public AgiSysOss upload(MultipartFile file, String userId) {
		log.info(">>>>>>>>>>>>>> OSS文件上传开始： {}", file.getOriginalFilename());
		SysOssVo oss = ossService.upload(file);
		log.info(">>>>>>>>>>>>>> OSS文件上传结束： {} - {}", oss.getFileName(), oss.getUrl());

		AgiSysOss agiSysOss = new AgiSysOss();
		agiSysOss.setOssId(oss.getOssId().toString());
		agiSysOss.setUrl(oss.getUrl());
		agiSysOss.setSize(file.getSize());
		agiSysOss.setFilename(oss.getFileName());
		agiSysOss.setOriginalFilename(oss.getOriginalName());
		agiSysOss.setBasePath(null);
		agiSysOss.setPath(null);
		agiSysOss.setExt(oss.getFileSuffix());
		agiSysOss.setContentType(null);
		agiSysOss.setPlatform(oss.getService());
		agiSysOss.setUserId(userId);
		agiSysOss.setCreateTime(new Date());

		this.save(agiSysOss);
		return agiSysOss;
	}
}
