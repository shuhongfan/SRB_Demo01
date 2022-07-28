package com.shf.srb.core.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shf.srb.core.pojo.entity.BorrowInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 借款信息表 Mapper 接口
 * </p>
 *
 * @author shuhongfan
 * @since 2022-07-23
 */
public interface BorrowInfoMapper extends BaseMapper<BorrowInfo> {

    IPage<BorrowInfo> selectBorrowInfoList(Page<BorrowInfo> page, @Param("keyword") String keyword);
}
