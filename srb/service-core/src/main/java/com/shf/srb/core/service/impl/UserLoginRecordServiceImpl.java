package com.shf.srb.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shf.srb.core.pojo.entity.UserLoginRecord;
import com.shf.srb.core.mapper.UserLoginRecordMapper;
import com.shf.srb.core.service.UserLoginRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户登录记录表 服务实现类
 * </p>
 *
 * @author shuhongfan
 * @since 2022-07-23
 */
@Service
public class UserLoginRecordServiceImpl extends ServiceImpl<UserLoginRecordMapper, UserLoginRecord> implements UserLoginRecordService {
    /**
     * 获取会员登录日志列表
     * @param userId
     * @return
     */
    @Override
    public List<UserLoginRecord> listTop50(Long userId) {
        QueryWrapper<UserLoginRecord> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId)
                .orderByDesc("id")
                .last("limit 50");

        List<UserLoginRecord> userLoginRecordList = baseMapper.selectList(wrapper);
        return userLoginRecordList;
    }
}
