package com.shf.srb.core.service;

import com.shf.srb.core.pojo.entity.LendItemReturn;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

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

    /**
     * 还款明细
     * @param lendReturnId
     * @return
     */
    List<Map<String, Object>> addReturnDetail(Long lendReturnId);

    /**
     * 根据还款计划id获取对应的回款计划列表
     * @param lendReturnId
     * @return
     */
    List<LendItemReturn> selectLendItemReturnList(Long lendReturnId);
}
