package com.shf.srb.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shf.common.exception.BusinessException;
import com.shf.srb.core.enums.LendStatusEnum;
import com.shf.srb.core.enums.ReturnMethodEnum;
import com.shf.srb.core.enums.TransTypeEnum;
import com.shf.srb.core.hfb.HfbConst;
import com.shf.srb.core.hfb.RequestHelper;
import com.shf.srb.core.mapper.BorrowerMapper;
import com.shf.srb.core.mapper.UserAccountMapper;
import com.shf.srb.core.mapper.UserInfoMapper;
import com.shf.srb.core.pojo.bo.TransFlowBO;
import com.shf.srb.core.pojo.entity.*;
import com.shf.srb.core.mapper.LendMapper;
import com.shf.srb.core.pojo.vo.BorrowInfoApprovalVO;
import com.shf.srb.core.pojo.vo.BorrowerDetailVO;
import com.shf.srb.core.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shf.srb.core.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 标的准备表 服务实现类
 * </p>
 *
 * @author shuhongfan
 * @since 2022-07-23
 */
@Service
@Slf4j
public class LendServiceImpl extends ServiceImpl<LendMapper, Lend> implements LendService {

    @Resource
    private BorrowerMapper borrowerMapper;

    @Resource
    private BorrowerService borrowerService;

    @Resource
    private DictService dictService;

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private UserAccountMapper userAccountMapper;

    @Resource
    private LendItemService lendItemService;

    @Resource
    private TransFlowService transFlowService;

    @Resource
    private LendReturnService lendReturnService;

    @Resource
    private LendItemReturnService lendItemReturnService;

    /**
     * 创建标的
     *
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
     *
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
     *
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

    /**
     * 计算投资收益
     *
     * @param invest
     * @param yearRate
     * @param totalmonth
     * @param returnMethod
     * @return
     */
    @Override
    public BigDecimal getInterestCount(BigDecimal invest, BigDecimal yearRate, Integer totalmonth, Integer returnMethod) {
        BigDecimal interestCount;

//        计算总利息
        if (returnMethod.intValue() == ReturnMethodEnum.ONE.getMethod()) {
            interestCount = Amount1Helper.getInterestCount(invest, yearRate, totalmonth);
        } else if (returnMethod.intValue() == ReturnMethodEnum.TWO.getMethod()) {
            interestCount = Amount2Helper.getInterestCount(invest, yearRate, totalmonth);
        } else if (returnMethod.intValue() == ReturnMethodEnum.THREE.getMethod()) {
            interestCount = Amount3Helper.getInterestCount(invest, yearRate, totalmonth);
        } else {
            interestCount = Amount4Helper.getInterestCount(invest, yearRate, totalmonth);
        }

        return interestCount;
    }

    /**
     * 满标放款
     *
     * （1）标的状态和标的平台收益
     * （2）给借款账号转入金额
     * （3）增加借款交易流水
     * （4）解冻并扣除投资人资金
     * （5）增加投资人交易流水
     * （6）生成借款人还款计划和出借人回款计划
     * @param lendId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void makeLoan(Long lendId) {
//        获取标的信息
        Lend lend = baseMapper.selectById(lendId);

        //调用汇付宝放款接口调用
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("agentId", HfbConst.AGENT_ID);//给商户分配的唯一标识
        paramMap.put("agentProjectCode", lend.getLendNo());//标的编号
        String agentBillNo = LendNoUtils.getLoanNo();//放款编号
        paramMap.put("agentBillNo", agentBillNo);

        //平台收益，放款扣除，借款人借款实际金额=借款金额-平台收益
        //月年化
        BigDecimal monthRate = lend.getServiceRate().divide(new BigDecimal(12), 8, BigDecimal.ROUND_DOWN);
        //平台实际收益 = 已投金额 * 月年化 * 标的期数
        BigDecimal realAmount = lend.getInvestAmount().multiply(monthRate).multiply(new BigDecimal(lend.getPeriod()));

        paramMap.put("mchFee", realAmount); //商户手续费(平台实际收益)
        paramMap.put("timestamp", RequestHelper.getTimestamp());
        String sign = RequestHelper.getSign(paramMap);
        paramMap.put("sign", sign);

        log.info("放款参数：" + JSONObject.toJSONString(paramMap));

        //发送同步远程调用
        JSONObject result = RequestHelper.sendRequest(paramMap, HfbConst.MAKE_LOAD_URL);
        log.info("放款结果：" + result.toJSONString());

        //放款失败
        if (!"0000".equals(result.getString("resultCode"))) {
            throw new BusinessException(result.getString("resultMsg"));
        }

//        放款成功
        //1.更新标的信息（标的的状态和标的的平台收益）
        lend.setRealAmount(realAmount); // 平台收益
        lend.setStatus(LendStatusEnum.PAY_RUN.getStatus());
        lend.setPaymentTime(LocalDateTime.now());
        baseMapper.updateById(lend);

        //获取借款人信息
        Long userId = lend.getUserId();
        UserInfo userInfo = userInfoMapper.selectById(userId);
        String bindCode = userInfo.getBindCode();

        //2. 给借款账号转入金额
        BigDecimal total = new BigDecimal(result.getString("voteAmt"));
        userAccountMapper.updateAccount(bindCode, total, new BigDecimal(0));

        //3. 新增借款人交易流水
        TransFlowBO transFlowBO = new TransFlowBO(
                agentBillNo,
                bindCode,
                total,
                TransTypeEnum.BORROW_BACK,
                "借款放款到账，编号：" + lend.getLendNo());//项目编号
        transFlowService.saveTransFlow(transFlowBO);

        //获取投资列表信息
        List<LendItem> lendItemList = lendItemService.selectByLendId(lendId, 1); //根据lendId获取投资记录
        lendItemList.forEach(item -> {
            //获取投资人信息
            Long investUserId = item.getInvestUserId();
            UserInfo investUserInfo = userInfoMapper.selectById(investUserId);
            String investBindCode = investUserInfo.getBindCode();

            //4. 投资人账号冻结金额转出
            BigDecimal investAmount = item.getInvestAmount(); //投资金额
            userAccountMapper.updateAccount(investBindCode, new BigDecimal(0), investAmount.negate());

            //5. 新增投资人交易流水
            TransFlowBO investTransFlowBO = new TransFlowBO(
                    LendNoUtils.getTransNo(),
                    investBindCode,
                    investAmount,
                    TransTypeEnum.INVEST_UNLOCK,
                    "冻结资金转出，出借放款，编号：" + lend.getLendNo());//项目编号
            transFlowService.saveTransFlow(investTransFlowBO);
        });

        //6. 放款成功生成借款人还款计划和投资人回款计划
        this.repaymentPlan(lend);
    }

    /**
     * 还款计划
     *
     * @param lend
     */
    private void repaymentPlan(Lend lend) {

        //还款计划列表
        List<LendReturn> lendReturnList = new ArrayList<>();

        //按还款时间生成还款计划
        int len = lend.getPeriod().intValue();
        for (int i = 1; i <= len; i++) {

            //创建还款计划对象
            LendReturn lendReturn = new LendReturn();
            lendReturn.setReturnNo(LendNoUtils.getReturnNo());
            lendReturn.setLendId(lend.getId());
            lendReturn.setBorrowInfoId(lend.getBorrowInfoId());
            lendReturn.setUserId(lend.getUserId());
            lendReturn.setAmount(lend.getAmount());
            lendReturn.setBaseAmount(lend.getInvestAmount());
            lendReturn.setLendYearRate(lend.getLendYearRate());
            lendReturn.setCurrentPeriod(i);//当前期数
            lendReturn.setReturnMethod(lend.getReturnMethod());
            //说明：还款计划中的这三项 = 回款计划中对应的这三项和：因此需要先生成对应的回款计划
            //			lendReturn.setPrincipal();
            //			lendReturn.setInterest();
            //			lendReturn.setTotal();

            lendReturn.setFee(new BigDecimal(0));
            lendReturn.setReturnDate(lend.getLendStartDate().plusMonths(i)); //第二个月开始还款
            lendReturn.setOverdue(false);
            if (i == len) { //最后一个月
                //标识为最后一次还款
                lendReturn.setLast(true);
            } else {
                lendReturn.setLast(false);
            }
            lendReturn.setStatus(0);
            lendReturnList.add(lendReturn);
        }
        //批量保存
        lendReturnService.saveBatch(lendReturnList);

        //获取lendReturnList中还款期数与还款计划id对应map
        Map<Integer, Long> lendReturnMap = lendReturnList.stream().collect(
                Collectors.toMap(LendReturn::getCurrentPeriod, LendReturn::getId)
        );

        //======================================================
        //=============获取所有投资者，生成回款计划===================
        //======================================================
        //回款计划列表
        List<LendItemReturn> lendItemReturnAllList = new ArrayList<>();
        //获取投资成功的投资记录
        List<LendItem> lendItemList = lendItemService.selectByLendId(lend.getId(), 1);
        for (LendItem lendItem : lendItemList) {

            //创建回款计划列表
            List<LendItemReturn> lendItemReturnList = this.returnInvest(lendItem.getId(), lendReturnMap, lend);
            lendItemReturnAllList.addAll(lendItemReturnList);
        }
        //更新还款计划中的相关金额数据
        for (LendReturn lendReturn : lendReturnList) {

            BigDecimal sumPrincipal = lendItemReturnAllList.stream()
                    //过滤条件：当回款计划中的还款计划id == 当前还款计划id的时候
                    .filter(item -> item.getLendReturnId().longValue() == lendReturn.getId().longValue())
                    //将所有回款计划中计算的每月应收本金相加
                    .map(LendItemReturn::getPrincipal)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal sumInterest = lendItemReturnAllList.stream()
                    .filter(item -> item.getLendReturnId().longValue() == lendReturn.getId().longValue())
                    .map(LendItemReturn::getInterest)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal sumTotal = lendItemReturnAllList.stream()
                    .filter(item -> item.getLendReturnId().longValue() == lendReturn.getId().longValue())
                    .map(LendItemReturn::getTotal)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            lendReturn.setPrincipal(sumPrincipal); //每期还款本金
            lendReturn.setInterest(sumInterest); //每期还款利息
            lendReturn.setTotal(sumTotal); //每期还款本息
        }
        lendReturnService.updateBatchById(lendReturnList);
    }

    /**
     * 回款计划
     *
     * @param lendItemId
     * @param lendReturnMap 还款期数与还款计划id对应map
     * @param lend
     * @return
     */
    public List<LendItemReturn> returnInvest(Long lendItemId, Map<Integer, Long> lendReturnMap, Lend lend) {

        //投标信息
        LendItem lendItem = lendItemService.getById(lendItemId);

        //投资金额
        BigDecimal amount = lendItem.getInvestAmount();
        //年化利率
        BigDecimal yearRate = lendItem.getLendYearRate();
        //投资期数
        int totalMonth = lend.getPeriod();

        Map<Integer, BigDecimal> mapInterest = null;  //还款期数 -> 利息
        Map<Integer, BigDecimal> mapPrincipal = null; //还款期数 -> 本金

        //根据还款方式计算本金和利息
        if (lend.getReturnMethod().intValue() == ReturnMethodEnum.ONE.getMethod()) {
            //利息
            mapInterest = Amount1Helper.getPerMonthInterest(amount, yearRate, totalMonth);
            //本金
            mapPrincipal = Amount1Helper.getPerMonthPrincipal(amount, yearRate, totalMonth);
        } else if (lend.getReturnMethod().intValue() == ReturnMethodEnum.TWO.getMethod()) {
            mapInterest = Amount2Helper.getPerMonthInterest(amount, yearRate, totalMonth);
            mapPrincipal = Amount2Helper.getPerMonthPrincipal(amount, yearRate, totalMonth);
        } else if (lend.getReturnMethod().intValue() == ReturnMethodEnum.THREE.getMethod()) {
            mapInterest = Amount3Helper.getPerMonthInterest(amount, yearRate, totalMonth);
            mapPrincipal = Amount3Helper.getPerMonthPrincipal(amount, yearRate, totalMonth);
        } else {
            mapInterest = Amount4Helper.getPerMonthInterest(amount, yearRate, totalMonth);
            mapPrincipal = Amount4Helper.getPerMonthPrincipal(amount, yearRate, totalMonth);
        }

        //创建回款计划列表
        List<LendItemReturn> lendItemReturnList = new ArrayList<>();
        for (Map.Entry<Integer, BigDecimal> entry : mapInterest.entrySet()) {
            Integer currentPeriod = entry.getKey();
            //根据还款期数获取还款计划的id
            Long lendReturnId = lendReturnMap.get(currentPeriod);

            LendItemReturn lendItemReturn = new LendItemReturn();
            lendItemReturn.setLendReturnId(lendReturnId);
            lendItemReturn.setLendItemId(lendItemId);
            lendItemReturn.setInvestUserId(lendItem.getInvestUserId());
            lendItemReturn.setLendId(lendItem.getLendId());
            lendItemReturn.setInvestAmount(lendItem.getInvestAmount());
            lendItemReturn.setLendYearRate(lend.getLendYearRate());
            lendItemReturn.setCurrentPeriod(currentPeriod);
            lendItemReturn.setReturnMethod(lend.getReturnMethod());
            //最后一次本金计算
            if (lendItemReturnList.size() > 0 && currentPeriod.intValue() == lend.getPeriod().intValue()) {
                //最后一期本金 = 本金 - 前几次之和
                BigDecimal sumPrincipal = lendItemReturnList.stream()
                        .map(LendItemReturn::getPrincipal)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                //最后一期应还本金 = 用当前投资人的总投资金额 - 除了最后一期前面期数计算出来的所有的应还本金
                BigDecimal lastPrincipal = lendItem.getInvestAmount().subtract(sumPrincipal);
                lendItemReturn.setPrincipal(lastPrincipal);
            } else {
                lendItemReturn.setPrincipal(mapPrincipal.get(currentPeriod));
                lendItemReturn.setInterest(mapInterest.get(currentPeriod));
            }
            lendItemReturn.setTotal(lendItemReturn.getPrincipal().add(lendItemReturn.getInterest()));
            lendItemReturn.setFee(new BigDecimal("0"));
            lendItemReturn.setReturnDate(lend.getLendStartDate().plusMonths(currentPeriod));
            //是否逾期，默认未逾期
            lendItemReturn.setOverdue(false);
            lendItemReturn.setStatus(0);

            lendItemReturnList.add(lendItemReturn);
        }
        lendItemReturnService.saveBatch(lendItemReturnList);

        return lendItemReturnList;
    }
}
