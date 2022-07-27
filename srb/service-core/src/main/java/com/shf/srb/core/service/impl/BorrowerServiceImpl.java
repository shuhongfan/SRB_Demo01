package com.shf.srb.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shf.srb.core.enums.BorrowerStatusEnum;
import com.shf.srb.core.mapper.BorrowerAttachMapper;
import com.shf.srb.core.mapper.UserInfoMapper;
import com.shf.srb.core.pojo.entity.Borrower;
import com.shf.srb.core.mapper.BorrowerMapper;
import com.shf.srb.core.pojo.entity.BorrowerAttach;
import com.shf.srb.core.pojo.entity.UserInfo;
import com.shf.srb.core.pojo.vo.BorrowerVO;
import com.shf.srb.core.service.BorrowerAttachService;
import com.shf.srb.core.service.BorrowerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 借款人 服务实现类
 * </p>
 *
 * @author shuhongfan
 * @since 2022-07-23
 */
@Service
public class BorrowerServiceImpl extends ServiceImpl<BorrowerMapper, Borrower> implements BorrowerService {

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private BorrowerAttachMapper borrowerAttachMapper;

    /**
     * 保存借款人信息
     * @param borrowerVO
     * @param userId
     */
    @Override
    public void saveBorrowerVOByUserId(BorrowerVO borrowerVO, Long userId) {
//        获取用户基本信息
        UserInfo userInfo = userInfoMapper.selectById(userId);

        // 保存借款人信息
        Borrower borrower = new Borrower();
        BeanUtils.copyProperties(borrowerVO, borrower);
        borrower.setUserId(userId);
        borrower.setName(userInfo.getName());
        borrower.setIdCard(userInfo.getIdCard());
        borrower.setMobile(userInfo.getMobile());
        borrower.setStatus(BorrowerStatusEnum.AUTH_RUN.getStatus());//认证中
        baseMapper.insert(borrower);

//        保存附件
        List<BorrowerAttach> borrowerAttachList = borrowerVO.getBorrowerAttachList();
        borrowerAttachList.forEach(borrowerAttach -> {
            borrowerAttach.setBorrowerId(borrower.getId());
            borrowerAttachMapper.insert(borrowerAttach);
        });

        //更新userInfo中的借款人状态,更新为认证中
        userInfo.setBorrowAuthStatus(BorrowerStatusEnum.AUTH_RUN.getStatus());
        userInfoMapper.updateById(userInfo);
    }

    /**
     * 获取借款人认证状态
     * @param userId
     * @return
     */
    @Override
    public Integer getStatusByUserId(Long userId) {
        QueryWrapper<Borrower> borrowerQueryWrapper = new QueryWrapper<>();
        borrowerQueryWrapper.select("status").eq("user_id", userId);
        List<Object> objects = baseMapper.selectObjs(borrowerQueryWrapper);

        if (objects.size() == 0) {
//            借款人尚未提交信息
            return BorrowerStatusEnum.NO_AUTH.getStatus();
        }
        Integer status = (Integer) objects.get(0);
        return status;
    }
}
