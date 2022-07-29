package com.shf.srb.core.service;

import com.shf.srb.core.pojo.entity.UserBind;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shf.srb.core.pojo.vo.UserBindVO;

import java.util.Map;

/**
 * <p>
 * 用户绑定表 服务类
 * </p>
 *
 * @author shuhongfan
 * @since 2022-07-23
 */
public interface UserBindService extends IService<UserBind> {

    /**
     * 账户绑定提交到托管平台的数据
     * @param userBindVO
     * @param userId
     * @return
     */
    String commitBindUser(UserBindVO userBindVO, Long userId);

    /**
     * 修改绑定状态
     * @param paramMap
     */
    void notify(Map<String, Object> paramMap);

    /**
     * 根据userId获取用户绑定账号
     * @param investUserId
     * @return
     */
    String getBindCodeByUserId(Long investUserId);
}
