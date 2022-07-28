package com.shf.srb.core.service;

import com.shf.srb.core.pojo.entity.BorrowInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;

/**
 * <p>
 * 借款信息表 服务类
 * </p>
 *
 * @author shuhongfan
 * @since 2022-07-23
 */
public interface BorrowInfoService extends IService<BorrowInfo> {

    /**
     * 获取借款额度
     * @param userId
     * @return
     */
    BigDecimal getBorrowAmount(Long userId);

    /**
     * 提交借款申请
     * @param borrowInfo
     * @param userId
     */
    void saveBorrowInfo(BorrowInfo borrowInfo, Long userId);

    /**
     * 获取借款申请审批状态
     * @param userId
     * @return
     */
    Integer getStatusByUserId(Long userId);
}
