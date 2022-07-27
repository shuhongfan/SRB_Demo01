package com.shf.srb.core.service;

import com.shf.srb.core.pojo.entity.Borrower;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shf.srb.core.pojo.vo.BorrowerVO;

/**
 * <p>
 * 借款人 服务类
 * </p>
 *
 * @author shuhongfan
 * @since 2022-07-23
 */
public interface BorrowerService extends IService<Borrower> {

    /**
     * 保存借款人信息
     * @param borrowerVO
     * @param userId
     */
    void saveBorrowerVOByUserId(BorrowerVO borrowerVO, Long userId);

    /**
     * 获取借款人认证状态
     * @param userId
     * @return
     */
    Integer getStatusByUserId(Long userId);
}
