
package org.dromara.langchain.system.domain;

import org.dromara.langchain.common.domain.dto.OssR;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "agi_sys_oss", autoResultMap = true)
@Accessors(chain = true)
public class AgiSysOss extends OssR {
	private static final long serialVersionUID = -250127374910520163L;

	/**
	 * 主键
	 */
	@TableId(type = IdType.ASSIGN_UUID)
	private String id;

	/**
	 * 用户ID
	 */
	private String userId;
}
