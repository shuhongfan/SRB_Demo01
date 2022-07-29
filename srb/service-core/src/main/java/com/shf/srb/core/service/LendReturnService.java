package com.shf.srb.core.service;

import com.shf.srb.core.pojo.entity.LendReturn;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 还款记录表 服务类
 * </p>
 *
 * @author shuhongfan
 * @since 2022-07-23
 */
public interface LendReturnService extends IService<LendReturn> {
    /**
     * 获取还款记录列表
     * @param lendId
     * @return
     */
    List<LendReturn> selectByLendId(Long lendId);
}
