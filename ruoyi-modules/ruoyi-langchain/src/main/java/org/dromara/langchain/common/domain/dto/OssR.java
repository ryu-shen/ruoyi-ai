package org.dromara.langchain.common.domain.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * OSS 文件对象
 *
 */
@Data
@Accessors(chain = true)
public class OssR implements Serializable {
	private static final long serialVersionUID = 5117927170776709434L;

	private String ossId;
	private String url;
	private Long size;
	private String filename;
	private String originalFilename;
	private String basePath;
	private String path;
	private String ext;
	private String contentType;
	private String platform;
	private Date createTime;
}
