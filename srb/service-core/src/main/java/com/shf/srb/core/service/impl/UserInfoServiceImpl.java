package com.shf.srb.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shf.common.exception.Assert;
import com.shf.common.result.ResponseEnum;
import com.shf.common.util.MD5;
import com.shf.srb.base.util.JwtUtils;
import com.shf.srb.core.mapper.UserAccountMapper;
import com.shf.srb.core.mapper.UserLoginRecordMapper;
import com.shf.srb.core.pojo.entity.UserAccount;
import com.shf.srb.core.pojo.entity.UserInfo;
import com.shf.srb.core.mapper.UserInfoMapper;
import com.shf.srb.core.pojo.entity.UserLoginRecord;
import com.shf.srb.core.pojo.query.UserInfoQuery;
import com.shf.srb.core.pojo.vo.LoginVO;
import com.shf.srb.core.pojo.vo.RegisterVO;
import com.shf.srb.core.pojo.vo.UserIndexVO;
import com.shf.srb.core.pojo.vo.UserInfoVO;
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

    @Resource
    private UserLoginRecordMapper userLoginRecordMapper;

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
        Assert.isTrue(count == 0, ResponseEnum.MOBILE_EXIST_ERROR);

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

    /**
     * 会员登录
     *
     * @param loginVO
     * @param ip
     * @return
     */
    @Override
    public UserInfoVO login(LoginVO loginVO, String ip) {
        String mobile = loginVO.getMobile();
        String password = loginVO.getPassword();
        Integer userType = loginVO.getUserType();

//        获取会员
        QueryWrapper<UserInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        wrapper.eq("user_type", userType);
        UserInfo userInfo = baseMapper.selectOne(wrapper);

//        LOGIN_MOBILE_ERROR(208, "用户不存在")
        Assert.notNull(userInfo, ResponseEnum.LOGIN_MOBILE_ERROR);
//        LOGIN_PASSWORD_ERROR(209, "密码错误"),
        Assert.equals(MD5.encrypt(password), userInfo.getPassword(), ResponseEnum.LOGIN_PASSWORD_ERROR);
//        LOGIN_LOKED_ERROR(210, "用户被锁定"),
        Assert.equals(userInfo.getStatus(), UserInfo.STATUS_NORMAL, ResponseEnum.LOGIN_LOKED_ERROR);

//        记录登录日志
        UserLoginRecord userLoginRecord = new UserLoginRecord();
        userLoginRecord.setUserId(userInfo.getId());
        userLoginRecord.setIp(ip);
        userLoginRecordMapper.insert(userLoginRecord);

//        生成token
        String token = JwtUtils.createToken(userInfo.getId(), userInfo.getName());
        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setToken(token);
        userInfoVO.setName(userInfo.getNickName());
        userInfoVO.setNickName(userInfo.getNickName());
        userInfoVO.setHeadImg(userInfo.getHeadImg());
        userInfoVO.setMobile(userInfo.getMobile());
        userInfoVO.setUserType(userType);
        return userInfoVO;
    }

    /**
     * 获取会员分页列表
     *
     * @param pageParam
     * @param userInfoQuery
     * @return
     */
    @Override
    public IPage<UserInfo> listPage(Page<UserInfo> pageParam, UserInfoQuery userInfoQuery) {
        if (userInfoQuery == null) {
            return baseMapper.selectPage(pageParam, null);
        }

        String mobile = userInfoQuery.getMobile();
        Integer status = userInfoQuery.getStatus();
        Integer userType = userInfoQuery.getUserType();

        QueryWrapper<UserInfo> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(mobile), "mobile", mobile)
                .eq(status != null, "status", status)
                .eq(userType != null, "user_yupe", userType);
        return baseMapper.selectPage(pageParam, wrapper);
    }

    /**
     * 锁定和解锁
     *
     * @param id
     * @param status
     */
    @Override
    public void lock(Long id, Integer status) {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(id);
        userInfo.setStatus(status);

        baseMapper.updateById(userInfo);
    }

    /**
     * 校验手机号是否注册
     *
     * @param mobile
     * @return
     */
    @Override
    public Boolean checkMobile(String mobile) {
        QueryWrapper<UserInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);

        Integer count = baseMapper.selectCount(wrapper);
        return count > 0;
    }

    /**
     * 获取个人空间用户信息
     * @param userId
     * @return
     */
    @Override
    public UserIndexVO getIndexUserInfo(Long userId) {
//        用户信息
        UserInfo userInfo = baseMapper.selectById(userId);

//        账户信息
        QueryWrapper<UserAccount> userAccountQueryWrapper = new QueryWrapper<>();
        userAccountQueryWrapper.eq("user_id", userId);
        UserAccount userAccount = userAccountMapper.selectOne(userAccountQueryWrapper);

//        登录信息
        QueryWrapper<UserLoginRecord> userLoginRecordQueryWrapper = new QueryWrapper<>();
        userLoginRecordQueryWrapper
                .eq("user_id", userId)
                .orderByDesc("id")
                .last("limit 1");

        UserLoginRecord userLoginRecord = userLoginRecordMapper.selectOne(userLoginRecordQueryWrapper);


        //组装结果数据
        UserIndexVO userIndexVO = new UserIndexVO();
        userIndexVO.setUserId(userInfo.getId());
        userIndexVO.setUserType(userInfo.getUserType());
        userIndexVO.setName(userInfo.getName());
        userIndexVO.setNickName(userInfo.getNickName());
        userIndexVO.setHeadImg(userInfo.getHeadImg());
        userIndexVO.setBindStatus(userInfo.getBindStatus());
        userIndexVO.setAmount(userAccount.getAmount());
        userIndexVO.setFreezeAmount(userAccount.getFreezeAmount());
        userIndexVO.setLastLoginTime(userLoginRecord.getCreateTime());


        return userIndexVO;
    }

    /**
     * 根据bindCode获取手机号
     * @param bindCode
     * @return
     */
    @Override
    public String getMobileByBindCode(String bindCode) {
        QueryWrapper<UserInfo> userInfoQueryWrapper = new QueryWrapper<>();
        userInfoQueryWrapper.eq("bind_code", bindCode);
        UserInfo userInfo = baseMapper.selectOne(userInfoQueryWrapper);
        return userInfo.getMobile();
    }

}
