package com.shf.srb.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shf.common.exception.Assert;
import com.shf.common.result.ResponseEnum;
import com.shf.srb.core.enums.TransTypeEnum;
import com.shf.srb.core.hfb.FormHelper;
import com.shf.srb.core.hfb.HfbConst;
import com.shf.srb.core.hfb.RequestHelper;
import com.shf.srb.core.mapper.UserInfoMapper;
import com.shf.srb.core.pojo.bo.TransFlowBO;
import com.shf.srb.core.pojo.entity.TransFlow;
import com.shf.srb.core.pojo.entity.UserAccount;
import com.shf.srb.core.mapper.UserAccountMapper;
import com.shf.srb.core.pojo.entity.UserInfo;
import com.shf.srb.core.service.TransFlowService;
import com.shf.srb.core.service.UserAccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shf.srb.core.service.UserBindService;
import com.shf.srb.core.util.LendNoUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 用户账户 服务实现类
 * </p>
 *
 * @author shuhongfan
 * @since 2022-07-23
 */
@Service
@Slf4j
public class UserAccountServiceImpl extends ServiceImpl<UserAccountMapper, UserAccount> implements UserAccountService {

    @Resource
    private TransFlowService transFlowService;

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private UserBindService userBindService;

    @Resource
    private UserAccountService userAccountService;


    /**
     * 充值
     *
     * @param chargeAmt
     * @param userId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String commitCharge(BigDecimal chargeAmt, Long userId) {
        UserInfo userInfo = userInfoMapper.selectById(userId);
        String bindCode = userInfo.getBindCode();

//        判断账户绑定状态
        Assert.notEmpty(bindCode, ResponseEnum.USER_NO_BIND_ERROR);

        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("agentId", HfbConst.AGENT_ID);
        paramMap.put("agentBillNo", LendNoUtils.getNo());
        paramMap.put("bindCode", bindCode);
        paramMap.put("chargeAmt", chargeAmt);
        paramMap.put("feeAmt", new BigDecimal("0"));
        paramMap.put("notifyUrl", HfbConst.RECHARGE_NOTIFY_URL);//检查常量是否正确
        paramMap.put("returnUrl", HfbConst.RECHARGE_RETURN_URL);
        paramMap.put("timestamp", RequestHelper.getTimestamp());

        String sign = RequestHelper.getSign(paramMap);
        paramMap.put("sign", sign);

//        构建自动充值提交表单
        String formStr = FormHelper.buildForm(HfbConst.RECHARGE_URL, paramMap);
        return formStr;
    }

    /**
     * 用户充值异步回调
     *
     * @param paramMap
     * @return
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public String notify(Map<String, Object> paramMap) {
        log.info("充值成功：" + JSONObject.toJSONString(paramMap));

//        判断交易流水是否存在
        String agentBillNo = (String) paramMap.get("agentBillNo");//商户充值订单号
        boolean isSave = transFlowService.isSaveTransFlow(agentBillNo);
        if (isSave) {
            log.warn("幂等性返回");
            return null;
        }

        String bindCode = (String) paramMap.get("bindCode"); //充值人绑定协议号
        String chargeAmt = (String) paramMap.get("chargeAmt"); //充值金额

        baseMapper.updateAccount(bindCode, new BigDecimal(chargeAmt), new BigDecimal(0));

//        增加交易流水
        TransFlowBO transFlowBO = new TransFlowBO(
                agentBillNo,
                bindCode,
                new BigDecimal(chargeAmt),
                TransTypeEnum.RECHARGE,
                "充值"
        );
        transFlowService.saveTransFlow(transFlowBO);

        return "success";
    }

    /**
     * 查询账户余额
     *
     * @param userId
     * @return
     */
    @Override
    public BigDecimal getAccount(Long userId) {
        QueryWrapper<UserAccount> userAccountQueryWrapper = new QueryWrapper<>();
        userAccountQueryWrapper.eq("user_id", userId);

//        根据userId查找用户账户
        UserAccount userAccount = baseMapper.selectOne(userAccountQueryWrapper);
        BigDecimal amount = userAccount.getAmount();
        return amount;
    }

    /**
     * 用户提现
     *
     * @param fetchAmt
     * @param userId
     * @return
     */
    @Override
    public String commitWithdraw(BigDecimal fetchAmt, Long userId) {
        //账户可用余额充足：当前用户的余额 >= 当前用户的提现金额
        BigDecimal amount = userAccountService.getAccount(userId);//获取当前用户的账户余额
//        NOT_SUFFICIENT_FUNDS_ERROR(307, "余额不足，请充值"),
        Assert.isTrue(amount.doubleValue() >= fetchAmt.doubleValue(), ResponseEnum.NOT_SUFFICIENT_FUNDS_ERROR);

        String bindCode = userBindService.getBindCodeByUserId(userId);

        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("agentId", HfbConst.AGENT_ID);
        paramMap.put("agentBillNo", LendNoUtils.getWithdrawNo());
        paramMap.put("bindCode", bindCode);
        paramMap.put("fetchAmt", fetchAmt);
        paramMap.put("feeAmt", new BigDecimal(0));
        paramMap.put("notifyUrl", HfbConst.WITHDRAW_NOTIFY_URL);
        paramMap.put("returnUrl", HfbConst.WITHDRAW_RETURN_URL);
        paramMap.put("timestamp", RequestHelper.getTimestamp());

        String sign = RequestHelper.getSign(paramMap);
        paramMap.put("sign", sign);

//        构建自动提交菜单
        String formStr = FormHelper.buildForm(HfbConst.WITHDRAW_URL, paramMap);

        return formStr;
    }

    /**
     * 用户提现异步回调
     * @param paramMap
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void notifyWithdraw(Map<String, Object> paramMap) {
        log.info("提现成功");
        String agentBillNo = (String) paramMap.get("agentBillNo");
        boolean res = transFlowService.isSaveTransFlow(agentBillNo);
        if (res) {
            log.warn("幂等性返回");
            return;
        }

        String bindCode = (String)paramMap.get("bindCode");
        String fetchAmt = (String)paramMap.get("fetchAmt");

//        根据账户修改账户金额
        baseMapper.updateAccount(bindCode, new BigDecimal("-" + fetchAmt), new BigDecimal(0));

//        增加交易流水
        TransFlowBO transFlowBO = new TransFlowBO(
                agentBillNo,
                bindCode,
                new BigDecimal(fetchAmt),
                TransTypeEnum.WITHDRAW,
                "提现"
        );

        transFlowService.saveTransFlow(transFlowBO);
    }
}
