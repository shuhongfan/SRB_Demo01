package com.shf.srb.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shf.common.exception.Assert;
import com.shf.common.result.ResponseEnum;
import com.shf.common.util.MD5;
import com.shf.srb.core.mapper.UserAccountMapper;
import com.shf.srb.core.pojo.entity.UserAccount;
import com.shf.srb.core.pojo.entity.UserInfo;
import com.shf.srb.core.mapper.UserInfoMapper;
import com.shf.srb.core.pojo.vo.RegisterVO;
import com.shf.srb.core.service.UserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 * 用户基本信息 服务实现类
 * </p>
 *
 * @author shuhongfan
 * @since 2022-07-23
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    @Resource
    private UserAccountMapper userAccountMapper;

    /**
     * 会员注册
     *
     * @param registerVO |
     */
    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void register(RegisterVO registerVO) {
//        判断用户是否注册
        QueryWrapper<UserInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", registerVO.getMobile());
        Integer count = baseMapper.selectCount(wrapper);
//        MOBILE_EXIST_ERROR(207, "手机号已被注册"),
        Assert.isTrue(count==0, ResponseEnum.MOBILE_EXIST_ERROR);

//        插入用户基本信息 user_info
        UserInfo userInfo = new UserInfo();
        userInfo.setUserType(registerVO.getUserType());
        userInfo.setNickName(registerVO.getMobile());
        userInfo.setName(registerVO.getMobile());
        userInfo.setMobile(registerVO.getMobile());
        userInfo.setPassword(MD5.encrypt(registerVO.getPassword()));
        userInfo.setStatus(UserInfo.STATUS_NORMAL);//正常状态
        userInfo.setHeadImg(UserInfo.USER_AVATAR);
        baseMapper.insert(userInfo);

//        创建会员账户
        UserAccount userAccount = new UserAccount();
        userAccount.setUserId(userInfo.getId());
        userAccountMapper.insert(userAccount);
    }
}
