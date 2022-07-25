package com.shf.srb.core.mapper;

import com.shf.srb.core.pojo.dto.ExcelDictDTO;
import com.shf.srb.core.pojo.entity.Dict;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 数据字典 Mapper 接口
 * </p>
 *
 * @author shuhongfan
 * @since 2022-07-23
 */
public interface DictMapper extends BaseMapper<Dict> {

    void insertBatch(@Param("list") List<ExcelDictDTO> list);
}
