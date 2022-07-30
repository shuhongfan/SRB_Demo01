package com.shf.srb.core.service;

import com.shf.srb.core.pojo.bo.TransFlowBO;
import com.shf.srb.core.pojo.entity.TransFlow;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 交易流水表 服务类
 * </p>
 *
 * @author shuhongfan
 * @since 2022-07-23
 */
public interface TransFlowService extends IService<TransFlow> {

    /**
     * 增加交易流水
     * @param transFlowBO
     */
    void saveTransFlow(TransFlowBO transFlowBO);

    /**
     * 判断交易流水是否存在
     * @param agentBillNo
     * @return
     */
    boolean isSaveTransFlow(String agentBillNo);

    /**
     * 获取资金列表
     * @param userId
     * @return
     */
    List<TransFlow> selectByUserId(Long userId);
}
