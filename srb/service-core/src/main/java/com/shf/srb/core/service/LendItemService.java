package com.shf.srb.core.service;

import com.shf.srb.core.pojo.entity.LendItem;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shf.srb.core.pojo.vo.InvestVO;

import java.util.Map;

/**
 * <p>
 * 标的出借记录表 服务类
 * </p>
 *
 * @author shuhongfan
 * @since 2022-07-23
 */
public interface LendItemService extends IService<LendItem> {

    /**
     * 会员投资提交数据
     * @param investVO
     * @return
     */
    String commitInvest(InvestVO investVO);

    /**
     * 会员投资异步回调
     * @param paramMap
     */
    void notify(Map<String, Object> paramMap);
}
