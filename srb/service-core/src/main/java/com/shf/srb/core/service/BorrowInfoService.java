package com.shf.srb.core.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shf.srb.core.pojo.entity.BorrowInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shf.srb.core.pojo.vo.BorrowInfoApprovalVO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

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


    /**
     * 借款信息列表
     * @param borrowInfoParam
     * @param keyword
     * @return
     */
    IPage<BorrowInfo> listPage(Page<BorrowInfo> borrowInfoParam, String keyword);

    /**
     * 获取借款信息
     * @param id
     * @return
     */
    Map<String, Object> getBorrowInfoDetail(Long id);

    /**
     * 审批借款人信息
     * @param borrowInfoApprovalVO
     */
    void approval(BorrowInfoApprovalVO borrowInfoApprovalVO);
}
