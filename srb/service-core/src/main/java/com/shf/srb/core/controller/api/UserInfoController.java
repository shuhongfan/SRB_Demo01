package com.shf.srb.core.controller.api;


import com.shf.common.exception.Assert;
import com.shf.common.result.R;
import com.shf.common.result.ResponseEnum;
import com.shf.common.util.RegexValidateUtils;
import com.shf.srb.core.pojo.vo.RegisterVO;
import com.shf.srb.core.service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 用户基本信息 前端控制器
 * </p>
 *
 * @author shuhongfan
 * @since 2022-07-23
 */
@Api(tags = "会员接口")
@RestController
@RequestMapping("/api/core/userInfo")
@Slf4j
@CrossOrigin
public class UserInfoController {

    @Resource
    private UserInfoService userInfoService;

    @Resource
    private RedisTemplate redisTemplate;

    @ApiOperation("会员注册")
    @PostMapping("/register")
    public R register(@RequestBody RegisterVO registerVO) {
        String mobile = registerVO.getMobile();
        String password = registerVO.getPassword();
        String code = registerVO.getCode();

//        MOBILE_NULL_ERROR(-202, "手机号码不能为空"),
        Assert.notEmpty(mobile, ResponseEnum.MOBILE_NULL_ERROR);
//        MOBILE_ERROR(-203, "手机号码不正确"),
        Assert.isTrue(RegexValidateUtils.checkCellphone(mobile), ResponseEnum.MOBILE_ERROR);
//        PASSWORD_NULL_ERROR(204, "密码不能为空"),
        Assert.notEmpty(password, ResponseEnum.PASSWORD_NULL_ERROR);
//        CODE_NULL_ERROR(205, "验证码不能为空"),
        Assert.notEmpty(code, ResponseEnum.CODE_NULL_ERROR);

//        校验验证码
        String codeGen = (String) redisTemplate.opsForValue().get("srb:sms:code:" + mobile);

//        CODE_ERROR(206, "验证码错误"),
        Assert.equals(code, codeGen, ResponseEnum.CODE_ERROR);

//        注册
        userInfoService.register(registerVO);
        return R.ok().message("注册成功");
    }

}

