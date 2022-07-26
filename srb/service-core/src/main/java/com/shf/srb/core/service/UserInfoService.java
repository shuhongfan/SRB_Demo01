package com.shf.srb.core.service;

import com.shf.srb.core.pojo.entity.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shf.srb.core.pojo.vo.LoginVO;
import com.shf.srb.core.pojo.vo.RegisterVO;
import com.shf.srb.core.pojo.vo.UserInfoVO;

/**
 * <p>
 * 用户基本信息 服务类
 * </p>
 *
 * @author shuhongfan
 * @since 2022-07-23
 */
public interface UserInfoService extends IService<UserInfo> {

    /**
     * 会员注册
     * @param registerVO
     */
    void register(RegisterVO registerVO);

    /**
     * 会员登录
     * @param loginVO
     * @param ip
     * @return
     */
    UserInfoVO login(LoginVO loginVO, String ip);
}
