package com.shf.srb.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shf.common.exception.Assert;
import com.shf.common.result.ResponseEnum;
import com.shf.srb.core.enums.BorrowInfoStatusEnum;
import com.shf.srb.core.enums.BorrowerStatusEnum;
import com.shf.srb.core.enums.UserBindEnum;
import com.shf.srb.core.mapper.BorrowerMapper;
import com.shf.srb.core.mapper.IntegralGradeMapper;
import com.shf.srb.core.mapper.UserInfoMapper;
import com.shf.srb.core.pojo.entity.BorrowInfo;
import com.shf.srb.core.mapper.BorrowInfoMapper;
import com.shf.srb.core.pojo.entity.Borrower;
import com.shf.srb.core.pojo.entity.IntegralGrade;
import com.shf.srb.core.pojo.entity.UserInfo;
import com.shf.srb.core.pojo.vo.BorrowInfoApprovalVO;
import com.shf.srb.core.pojo.vo.BorrowerDetailVO;
import com.shf.srb.core.service.BorrowInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shf.srb.core.service.BorrowerService;
import com.shf.srb.core.service.DictService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 借款信息表 服务实现类
 * </p>
 *
 * @author shuhongfan
 * @since 2022-07-23
 */
@Service
public class BorrowInfoServiceImpl extends ServiceImpl<BorrowInfoMapper, BorrowInfo> implements BorrowInfoService {

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private IntegralGradeMapper integralGradeMapper;


    @Resource
    private DictService dictService;

    @Resource
    private BorrowerMapper borrowerMapper;

    @Resource
    private BorrowerService borrowerService;

    /**
     * 获取借款额度
     *
     * @param userId
     * @return
     */
    @Override
    public BigDecimal getBorrowAmount(Long userId) {
        // 获取用户积分
        UserInfo userInfo = userInfoMapper.selectById(userId);
        Assert.notNull(userInfo, ResponseEnum.LOGIN_MOBILE_ERROR);
        Integer integral = userInfo.getIntegral();

//        根据积分查询借款额度
        QueryWrapper<IntegralGrade> integralGradeQueryWrapper = new QueryWrapper<>();
        integralGradeQueryWrapper
                .le("integral_start", integral)
                .ge("integral_end", integral);

        IntegralGrade integralGradeConfig = integralGradeMapper.selectOne(integralGradeQueryWrapper);
        if (integralGradeConfig == null) {
            return new BigDecimal("0");
        }

        return integralGradeConfig.getBorrowAmount();
    }

    /**
     * 提交借款申请
     *
     * @param borrowInfo
     * @param userId
     */
    @Override
    public void saveBorrowInfo(BorrowInfo borrowInfo, Long userId) {
//        获取userInfo的用户数据
        UserInfo userInfo = userInfoMapper.selectById(userId);

//        判断用户绑定状态
//        USER_NO_BIND_ERROR(302, "用户未绑定"),
        Assert.isTrue(
                userInfo.getBindStatus().intValue() == UserBindEnum.BIND_OK.getStatus().intValue(),
                ResponseEnum.USER_NO_BIND_ERROR);

//        判断用户信息是否审批通过
//        USER_NO_AMOUNT_ERROR(303, "用户信息未审核"),
        Assert.isTrue(
                userInfo.getBorrowAuthStatus().intValue() == BorrowerStatusEnum.AUTH_OK.getStatus().intValue(),
                ResponseEnum.USER_NO_AMOUNT_ERROR);

//        判断借款额度是否足够
//        USER_AMOUNT_LESS_ERROR(304, "您的借款额度不足"),
        BigDecimal borrowAmount = this.getBorrowAmount(userId);
        Assert.isTrue(
                borrowInfo.getAmount().doubleValue() <= borrowAmount.doubleValue(),
                ResponseEnum.USER_AMOUNT_LESS_ERROR);

//        存储borrowInfo数据
        borrowInfo.setUserId(userId);

//        百分百转换成小数
        borrowInfo.setBorrowYearRate(borrowInfo.getBorrowYearRate().divide(new BigDecimal(100)));
//        设置借款申请的审核状态
        borrowInfo.setStatus(BorrowInfoStatusEnum.CHECK_RUN.getStatus());
        baseMapper.insert(borrowInfo);
    }

    /**
     * 获取借款申请审批状态
     *
     * @param userId
     * @return
     */
    @Override
    public Integer getStatusByUserId(Long userId) {
        QueryWrapper<BorrowInfo> borrowInfoQueryWrapper = new QueryWrapper<>();
        borrowInfoQueryWrapper
                .select("status")
                .eq("user_id", userId);

        List<Object> objects = baseMapper.selectObjs(borrowInfoQueryWrapper);
        if (objects.size() == 0) {
//            借款人尚未提交信息
            return BorrowInfoStatusEnum.NO_AUTH.getStatus();
        }

        Integer status = (Integer) objects.get(0);

        return status;
    }

    /**
     * 借款信息列表
     *
     * @param borrowInfoParam
     * @param keyword
     * @return
     */
    @Override
    public IPage<BorrowInfo> listPage(Page<BorrowInfo> borrowInfoParam, String keyword) {
        IPage<BorrowInfo> borrowInfoList = null;
        if (StringUtils.isEmpty(keyword)) {
            borrowInfoList = baseMapper.selectBorrowInfoList(borrowInfoParam, null);
        } else {
             borrowInfoList = baseMapper.selectBorrowInfoList(borrowInfoParam,keyword);
        }

        borrowInfoList.getRecords().forEach(borrowInfo -> {
            String returnMethod = dictService.getNameByParentDictCodeAndValue("returnMethod", borrowInfo.getReturnMethod());
            String moneyUse = dictService.getNameByParentDictCodeAndValue("moneyUse", borrowInfo.getMoneyUse());
            String status = BorrowInfoStatusEnum.getMsgByStatus(borrowInfo.getStatus());
            borrowInfo.getParam().put("returnMethod", returnMethod);
            borrowInfo.getParam().put("moneyUse", moneyUse);
            borrowInfo.getParam().put("status", status);
        });
        return borrowInfoList;
    }

    /**
     * 获取借款信息
     * @param id
     * @return
     */
    @Override
    public Map<String, Object> getBorrowInfoDetail(Long id) {
//        查询借款对象
        BorrowInfo borrowInfo = baseMapper.selectById(id);

//        组装数据
        String returnMethod = dictService.getNameByParentDictCodeAndValue("returnMethod", borrowInfo.getReturnMethod());
        String moneyUse = dictService.getNameByParentDictCodeAndValue("moneyUse", borrowInfo.getMoneyUse());
        String status = BorrowInfoStatusEnum.getMsgByStatus(borrowInfo.getStatus());
        borrowInfo.getParam().put("returnMethod", returnMethod);
        borrowInfo.getParam().put("moneyUse", moneyUse);
        borrowInfo.getParam().put("status", status);

//        根据user_id获取借款人对象
        QueryWrapper<Borrower> borrowerQueryWrapper = new QueryWrapper<>();
        borrowerQueryWrapper.eq("user_id", borrowInfo.getUserId());
        Borrower borrower = borrowerMapper.selectOne(borrowerQueryWrapper);

//        组装借款人对象
        BorrowerDetailVO borrowerDetailVO = borrowerService.getBorrowerDetailVOById(borrower.getId());

        //组装数据
        Map<String, Object> result = new HashMap<>();
        result.put("borrowInfo", borrowInfo);
        result.put("borrower", borrowerDetailVO);
        return result;
    }

    /**
     * 审批借款人信息
     * @param borrowInfoApprovalVO
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void approval(BorrowInfoApprovalVO borrowInfoApprovalVO) {
//        修改借款信息状态
        Long id = borrowInfoApprovalVO.getId();
        BorrowInfo borrowInfo = baseMapper.selectById(id);
        borrowInfo.setStatus(borrowInfoApprovalVO.getStatus());
        baseMapper.updateById(borrowInfo);

//        如果审核通过，则创建标的记录 lend
        if (borrowInfoApprovalVO.getStatus().intValue() == BorrowInfoStatusEnum.CHECK_OK.getStatus().intValue()) {
//            创建标的

        }
    }
}
