package com.shf.srb.core.service;

import com.shf.srb.core.pojo.entity.LendItemReturn;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 标的出借回款记录表 服务类
 * </p>
 *
 * @author shuhongfan
 * @since 2022-07-23
 */
public interface LendItemReturnService extends IService<LendItemReturn> {

    /**
     * 获取回款计划列表
     * @param lendId
     * @param userId
     * @return
     */
    List<LendItemReturn> selectByLendId(Long lendId, Long userId);
}
