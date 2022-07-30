package com.shf.srb.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shf.common.exception.Assert;
import com.shf.srb.core.mapper.LendItemMapper;
import com.shf.srb.core.mapper.LendMapper;
import com.shf.srb.core.mapper.LendReturnMapper;
import com.shf.srb.core.pojo.entity.Lend;
import com.shf.srb.core.pojo.entity.LendItem;
import com.shf.srb.core.pojo.entity.LendItemReturn;
import com.shf.srb.core.mapper.LendItemReturnMapper;
import com.shf.srb.core.pojo.entity.LendReturn;
import com.shf.srb.core.service.LendItemReturnService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shf.srb.core.service.UserAccountService;
import com.shf.srb.core.service.UserBindService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 标的出借回款记录表 服务实现类
 * </p>
 *
 * @author shuhongfan
 * @since 2022-07-23
 */
@Service
public class LendItemReturnServiceImpl extends ServiceImpl<LendItemReturnMapper, LendItemReturn> implements LendItemReturnService {
    @Resource
    private UserBindService userBindService;

    @Resource
    private LendItemMapper lendItemMapper;

    @Resource
    private LendMapper lendMapper;

    @Resource
    private LendReturnMapper lendReturnMapper;

    /**
     * 获取回款计划列表
     *
     * @param lendId
     * @param userId
     * @return
     */
    @Override
    public List<LendItemReturn> selectByLendId(Long lendId, Long userId) {
        QueryWrapper<LendItemReturn> lendItemReturnQueryWrapper = new QueryWrapper<>();
        lendItemReturnQueryWrapper
                .eq("lend_id", lendId)
                .eq("invest_user_id", userId)
                .orderByAsc("current_period");

        return baseMapper.selectList(lendItemReturnQueryWrapper);
    }

    /**
     * 还款明细
     *
     * @param lendReturnId
     * @return
     */
    @Override
    public List<Map<String, Object>> addReturnDetail(Long lendReturnId) {
//        获取还款记录
        LendReturn lendReturn = lendReturnMapper.selectById(lendReturnId);

//        获取标的信息
        Lend lend = lendMapper.selectById(lendReturn.getLendId());

//        根据还款id获取回款列表
        List<LendItemReturn> lendItemReturnList = this.selectLendItemReturnList(lendReturnId);
        List<Map<String, Object>> lendItemReturnDetailList = new ArrayList<>();
        lendItemReturnList.forEach(lendItemReturn -> {
            LendItem lendItem = lendItemMapper.selectById(lendItemReturn.getLendReturnId());
            String bindCode = userBindService.getBindCodeByUserId(lendItem.getInvestUserId());

            Map<String, Object> map = new HashMap<>();
            //项目编号
            map.put("agentProjectCode", lend.getLendNo());
            //出借编号
            map.put("voteBillNo", lendItem.getLendItemNo());
            //收款人（出借人）
            map.put("toBindCode", bindCode);
            //还款金额
            map.put("transitAmt", lendItemReturn.getTotal());
            //还款本金
            map.put("baseAmt", lendItemReturn.getPrincipal());
            //还款利息
            map.put("benifitAmt", lendItemReturn.getInterest());
            //商户手续费
            map.put("feeAmt", new BigDecimal("0"));

            lendItemReturnDetailList.add(map);
        });

        return lendItemReturnDetailList;
    }

    /**
     * 根据还款计划id获取对应的回款计划列表
     * @param lendReturnId
     * @return
     */
    @Override
    public List<LendItemReturn> selectLendItemReturnList(Long lendReturnId) {
        QueryWrapper<LendItemReturn> lendItemReturnQueryWrapper = new QueryWrapper<>();
        lendItemReturnQueryWrapper.eq("lend_return_id", lendReturnId);
        List<LendItemReturn> lendItemReturnList = baseMapper.selectList(lendItemReturnQueryWrapper);
        return lendItemReturnList;
    }

}
