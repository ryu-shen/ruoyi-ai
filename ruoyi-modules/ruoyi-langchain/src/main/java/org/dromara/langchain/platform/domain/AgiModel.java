package org.dromara.langchain.platform.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "agi_model", autoResultMap = true)
@Accessors(chain = true)
public class AgiModel extends BaseEntity {
	private static final long serialVersionUID = -19545329638997333L;

	@TableId(type = IdType.ASSIGN_UUID)
	private String id;

	private String type;
	private String model;
	private String provider;
	private String name;
	private Integer responseLimit;
	private Double temperature = 0.2;
	private Double topP = 0.0;
	private String apiKey;
	private String secretKey;
	private String baseUrl;
	private String endpoint;
	private String geminiLocation;
	private String geminiProject;
	private String azureDeploymentName;
	private String imageSize;
	private String imageQuality;
	private String imageStyle;
	private Integer dimension;
}
