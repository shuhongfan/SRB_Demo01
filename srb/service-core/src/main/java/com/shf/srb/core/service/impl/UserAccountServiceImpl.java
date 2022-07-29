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

    /**
     * 充值
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
        if (isSave){
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
}
