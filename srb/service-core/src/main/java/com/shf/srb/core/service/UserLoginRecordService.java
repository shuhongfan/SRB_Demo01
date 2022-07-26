package com.shf.srb.core.service;

import com.shf.srb.core.pojo.entity.UserLoginRecord;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户登录记录表 服务类
 * </p>
 *
 * @author shuhongfan
 * @since 2022-07-23
 */
public interface UserLoginRecordService extends IService<UserLoginRecord> {

    /**
     * 获取会员登录日志列表
     * @param userId
     * @return
     */
    List<UserLoginRecord> listTop50(Long userId);
}
