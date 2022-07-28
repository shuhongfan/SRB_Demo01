package com.shf.srb.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shf.srb.core.enums.LendStatusEnum;
import com.shf.srb.core.mapper.BorrowerMapper;
import com.shf.srb.core.pojo.entity.BorrowInfo;
import com.shf.srb.core.pojo.entity.Borrower;
import com.shf.srb.core.pojo.entity.Lend;
import com.shf.srb.core.mapper.LendMapper;
import com.shf.srb.core.pojo.vo.BorrowInfoApprovalVO;
import com.shf.srb.core.pojo.vo.BorrowerDetailVO;
import com.shf.srb.core.service.BorrowerService;
import com.shf.srb.core.service.DictService;
import com.shf.srb.core.service.LendService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shf.srb.core.util.LendNoUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 标的准备表 服务实现类
 * </p>
 *
 * @author shuhongfan
 * @since 2022-07-23
 */
@Service
public class LendServiceImpl extends ServiceImpl<LendMapper, Lend> implements LendService {

    @Resource
    private BorrowerMapper borrowerMapper;

    @Resource
    private BorrowerService borrowerService;

    @Resource
    private DictService dictService;

    /**
     * 创建标的
     * @param borrowInfoApprovalVO
     * @param borrowInfo
     */
    @Override
    public void createLend(BorrowInfoApprovalVO borrowInfoApprovalVO, BorrowInfo borrowInfo) {
        Lend lend = new Lend();
        lend.setUserId(borrowInfo.getUserId());//借款用户id
        lend.setBorrowInfoId(borrowInfo.getId());
        lend.setLendNo(LendNoUtils.getLendNo());//生成编号
        lend.setTitle(borrowInfoApprovalVO.getTitle());
        lend.setAmount(borrowInfo.getAmount()); //借款金额
        lend.setPeriod(borrowInfo.getPeriod());//借款期限
        lend.setLendYearRate(borrowInfoApprovalVO.getLendYearRate().divide(new BigDecimal(100)));//年化利率，从审批对象中获取
        lend.setServiceRate(borrowInfoApprovalVO.getServiceRate().divide(new BigDecimal(100)));//服务费，从审批对象中获取
        lend.setReturnMethod(borrowInfo.getReturnMethod());//还款方式 1-等额本息 2-等额本金 3-每月还息一次还本 4-一次还本
        lend.setLowestAmount(new BigDecimal(100));//最低投资金额
        lend.setInvestAmount(new BigDecimal(0));//已投资金额
        lend.setInvestNum(0);// 已投人数
        lend.setPublishDate(LocalDateTime.now());

//        起息日期
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate lendStartDate = LocalDate.parse(borrowInfoApprovalVO.getLendStartDate(), dateTimeFormatter);
        lend.setLendStartDate(lendStartDate);

//        结束日期
        LocalDate lendEndDate = lendStartDate.plusMonths(borrowInfo.getPeriod());
        lend.setLendEndDate(lendEndDate);

//        平台预期收益 = 年化 / 12 * 期数
        BigDecimal monthRate = lend.getServiceRate().divide(new BigDecimal(12), 8, BigDecimal.ROUND_DOWN);
        BigDecimal expectAmountRate = monthRate.multiply(new BigDecimal(lend.getPeriod()));
//        平台收益 = 标的金额 * 月年化 * 期数
        BigDecimal expectAmount = expectAmountRate.multiply(lend.getAmount());
        lend.setExpectAmount(expectAmount);

//        实际收益率
        lend.setRealAmount(new BigDecimal(0));

//        状态
        lend.setStatus(LendStatusEnum.INVEST_RUN.getStatus());

//        审核时间
        lend.setCheckTime(LocalDateTime.now());

//        审核人
        lend.setCheckAdminId(1L);

        baseMapper.insert(lend);
    }

    /**
     * 标的列表
     * @param pageParam
     * @param keyword
     * @return
     */
    @Override
    public IPage<Lend> listPage(Page<Lend> pageParam, String keyword) {
        Page<Lend> lendPage = null;

        QueryWrapper<Lend> borrowerQueryWrapper = new QueryWrapper<>();
        borrowerQueryWrapper
                .like("lend_no", keyword)
                .or().like("amount", keyword)
                .orderByDesc("id");

        if (StringUtils.isEmpty(keyword)) {
            lendPage = baseMapper.selectPage(pageParam, null);
        } else {
            lendPage = baseMapper.selectPage(pageParam, borrowerQueryWrapper);
        }

        lendPage.getRecords().forEach(lend -> {
            String returnMethod = dictService.getNameByParentDictCodeAndValue("returnMethod", lend.getReturnMethod());
            String status = LendStatusEnum.getMsgByStatus(lend.getStatus());
            lend.getParam().put("returnMethod", returnMethod);
            lend.getParam().put("status", status);
        });
        return lendPage;
    }

    /**
     * 获取标的的信息
     * @param id
     * @return
     */
    @Override
    public Map<String, Object> getLendDetail(Long id) {
//        查询标的对象
        Lend lend = baseMapper.selectById(id);

//        组装数据
        String returnMethod = dictService.getNameByParentDictCodeAndValue("returnMethod", lend.getReturnMethod());
        String status = LendStatusEnum.getMsgByStatus(lend.getStatus());
        lend.getParam().put("returnMethod", returnMethod);
        lend.getParam().put("status", status);

//        根据user_id获取借款人对象
        QueryWrapper<Borrower> borrowerQueryWrapper = new QueryWrapper<>();
        borrowerQueryWrapper.eq("user_id", lend.getUserId());
        Borrower borrower = borrowerMapper.selectOne(borrowerQueryWrapper);

//        组装借款人对象
        BorrowerDetailVO borrowerDetailVO = borrowerService.getBorrowerDetailVOById(borrower.getId());

        //组装数据
        Map<String, Object> result = new HashMap<>();
        result.put("lend", lend);
        result.put("borrower", borrowerDetailVO);

        return result;
    }
}
