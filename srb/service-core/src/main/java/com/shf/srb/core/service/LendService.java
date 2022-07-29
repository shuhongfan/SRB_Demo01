package com.shf.srb.core.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shf.srb.core.pojo.entity.BorrowInfo;
import com.shf.srb.core.pojo.entity.Lend;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shf.srb.core.pojo.vo.BorrowInfoApprovalVO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 标的准备表 服务类
 * </p>
 *
 * @author shuhongfan
 * @since 2022-07-23
 */
public interface LendService extends IService<Lend> {
    /**
     * 创建标的
     * @param borrowInfoApprovalVO
     * @param borrowInfo
     */
    void createLend(BorrowInfoApprovalVO borrowInfoApprovalVO, BorrowInfo borrowInfo);

    /**
     * 标的列表
     * @param pageParam
     * @param keyword
     * @return
     */
    IPage<Lend> listPage(Page<Lend> pageParam, String keyword);

    /**
     * 获取标的的信息
     * @param id
     * @return
     */
    Map<String, Object> getLendDetail(Long id);

    /**
     * 计算投资收益
     * @param invest
     * @param yearRate
     * @param totalmonth
     * @param returnMethod
     * @return
     */
    BigDecimal getInterestCount(BigDecimal invest, BigDecimal yearRate, Integer totalmonth, Integer returnMethod);
}
