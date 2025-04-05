package org.dromara.system.config.mapper;

import java.util.List;

import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;
import org.dromara.system.config.domain.SysDictData;
import org.dromara.system.config.domain.vo.SysDictDataVo;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

/**
 * 字典表 数据层
 *
 * @author Lion Li
 */
public interface SysDictDataMapper extends BaseMapperPlus<SysDictData, SysDictDataVo> {

	/**
	 * 根据字典类型查询字典数据列表
	 *
	 * @param dictType 字典类型
	 * @return 符合条件的字典数据列表
	 */
	default List<SysDictDataVo> selectDictDataByType(String dictType) {
		return selectVoList(new LambdaQueryWrapper<SysDictData>().eq(SysDictData::getDictType, dictType)
				.orderByAsc(SysDictData::getDictSort));
	}
}
