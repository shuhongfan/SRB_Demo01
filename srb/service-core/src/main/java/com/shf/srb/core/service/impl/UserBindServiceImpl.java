package com.shf.srb.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shf.common.exception.Assert;
import com.shf.common.result.ResponseEnum;
import com.shf.srb.core.enums.UserBindEnum;
import com.shf.srb.core.hfb.FormHelper;
import com.shf.srb.core.hfb.HfbConst;
import com.shf.srb.core.hfb.RequestHelper;
import com.shf.srb.core.mapper.UserInfoMapper;
import com.shf.srb.core.pojo.entity.UserBind;
import com.shf.srb.core.mapper.UserBindMapper;
import com.shf.srb.core.pojo.entity.UserInfo;
import com.shf.srb.core.pojo.vo.UserBindVO;
import com.shf.srb.core.service.UserBindService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 用户绑定表 服务实现类
 * </p>
 *
 * @author shuhongfan
 * @since 2022-07-23
 */
@Service
public class UserBindServiceImpl extends ServiceImpl<UserBindMapper, UserBind> implements UserBindService {

    @Resource
    private UserInfoMapper userInfoMapper;

    /**
     * 账户绑定提交到托管平台的数据
     *
     * @param userBindVO
     * @param userId
     * @return
     */
    @Override
    public String commitBindUser(UserBindVO userBindVO, Long userId) {
//        不同的user_id，相同的身份证，如果存在，则不允许
//        查询身份证是否绑定
        QueryWrapper<UserBind> userBindQueryWrapper = new QueryWrapper<>();
        userBindQueryWrapper
                .eq("id_card", userBindVO.getIdCard())
                .ne("user_id", userId);
        UserBind userBind = baseMapper.selectOne(userBindQueryWrapper);
//        USER_BIND_IDCARD_EXIST_ERROR(-301, "身份证号码已绑定"),
        Assert.isNull(userBind, ResponseEnum.USER_BIND_IDCARD_EXIST_ERROR);

        //查询用户绑定信息
        userBindQueryWrapper = new QueryWrapper<>();
        userBindQueryWrapper.eq("user_id", userId);
        userBind = baseMapper.selectOne(userBindQueryWrapper);

//        相同的user_id，相同的身份证，如果存在，那么就取出数据做更新
        //判断是否有绑定记录
        if (userBind == null) {
//            如果未创建绑定记录，则创建一条记录
            userBind = new UserBind();
            BeanUtils.copyProperties(userBindVO, userBind);
            userBind.setUserId(userId);
            userBind.setStatus(UserBindEnum.NO_BIND.getStatus());
            baseMapper.insert(userBind);
        } else {
//            曾经跳转到托管平台，但是未操作完成，此时将用户最新填写的数据同步到userBind对象
            BeanUtils.copyProperties(userBindVO, userBind);
            baseMapper.updateById(userBind);
        }

//        组装自动提交表单的参数
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("agentId", HfbConst.AGENT_ID); //给商户分配的唯一标识
        paramMap.put("agentUserId", userId); //商户的个人会员ID。由数字、字母组成。
        paramMap.put("idCard", userBindVO.getIdCard()); //身份证号
        paramMap.put("personalName", userBindVO.getName()); //真实姓名。由2~5个汉字组成。
        paramMap.put("bankType", userBindVO.getBankType()); //银行卡类型
        paramMap.put("bankNo", userBindVO.getBankNo()); //银行卡
        paramMap.put("mobile", userBindVO.getMobile()); //银行卡预留手机
        paramMap.put("returnUrl", HfbConst.USERBIND_RETURN_URL); //绑定完成后同步返回商户的完整地址。
        paramMap.put("notifyUrl", HfbConst.USERBIND_NOTIFY_URL); //绑定完成后异步通知商户的完整地址。
        paramMap.put("timestamp", RequestHelper.getTimestamp()); //时间戳。从1970-01-01 00:00:00算起的毫秒数
        paramMap.put("sign", RequestHelper.getSign(paramMap)); //验签参数。

//        生成动态表单字符串
        String formStr = FormHelper.buildForm(HfbConst.USERBIND_URL, paramMap);
        return formStr;
    }

    /**
     * 修改绑定状态
     *
     * @param paramMap
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void notify(Map<String, Object> paramMap) {
        String bindCode = (String) paramMap.get("bindCode");
        String agentUserId = (String) paramMap.get("agentUserId"); //会员id

//        根据user_id查询user_bind记录
        QueryWrapper<UserBind> userBindQueryWrapper = new QueryWrapper<>();
        userBindQueryWrapper.eq("user_id", agentUserId);

//        更新用户绑定表
        UserBind userBind = baseMapper.selectOne(userBindQueryWrapper);
        userBind.setBindCode(bindCode);
        userBind.setStatus(UserBindEnum.BIND_OK.getStatus());
        baseMapper.updateById(userBind);

//        更新用户表
        UserInfo userInfo = userInfoMapper.selectById(agentUserId);
        userInfo.setBindCode(bindCode);
        userInfo.setName(userBind.getName());
        userInfo.setIdCard(userBind.getIdCard());
        userInfo.setBindStatus(UserBindEnum.BIND_OK.getStatus());
        userInfoMapper.updateById(userInfo);
    }

    /**
     * 根据userId获取用户绑定账号
     *
     * @param investUserId
     * @return
     */
    @Override
    public String getBindCodeByUserId(Long investUserId) {
        QueryWrapper<UserBind> userBindQueryWrapper = new QueryWrapper<>();
        userBindQueryWrapper.eq("user_id", investUserId);
        UserBind userBind = baseMapper.selectOne(userBindQueryWrapper);
        String bindCode = userBind.getBindCode();
        return bindCode;
    }
}
