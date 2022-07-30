package com.shf.srb.core.service;

import com.shf.srb.core.pojo.entity.UserAccount;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;
import java.util.Map;

/**
 * <p>
 * 用户账户 服务类
 * </p>
 *
 * @author shuhongfan
 * @since 2022-07-23
 */
public interface UserAccountService extends IService<UserAccount> {

    /**
     * 充值
     * @param chargeAmt
     * @param userId
     * @return
     */
    String commitCharge(BigDecimal chargeAmt, Long userId);

    /**
     * 用户充值异步回调
     * @param paramMap
     * @return
     */
    String notify(Map<String, Object> paramMap);

    /**
     * 查询账户余额
     * @param userId
     * @return
     */
    BigDecimal getAccount(Long userId);

    /**
     * 用户提现
     * @param fetchAmt
     * @param userId
     * @return
     */
    String commitWithdraw(BigDecimal fetchAmt, Long userId);

    /**
     * 用户提现异步回调
     * @param paramMap
     */
    void notifyWithdraw(Map<String, Object> paramMap);
}
