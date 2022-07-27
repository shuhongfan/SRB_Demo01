package com.shf.srb.core.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shf.srb.core.pojo.entity.Borrower;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shf.srb.core.pojo.vo.BorrowerApprovalVO;
import com.shf.srb.core.pojo.vo.BorrowerDetailVO;
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

    /**
     * 获取借款人分页列表
     * @param pageParam
     * @param keyword
     * @return
     */
    IPage<Borrower> listPage(Page<Borrower> pageParam, String keyword);

    /**
     * 获取借款人信息
     * @param id
     * @return
     */
    BorrowerDetailVO getBorrowerDetailVOById(Long id);

    /**
     * 借款额度审批
     * @param borrowerApprovalVO
     */
    void approval(BorrowerApprovalVO borrowerApprovalVO);
}
