package com.shf.srb.core.controller.api;


import com.alibaba.fastjson.JSON;
import com.netflix.client.http.HttpHeaders;
import com.netflix.client.http.HttpRequest;
import com.shf.common.result.R;
import com.shf.srb.base.util.JwtUtils;
import com.shf.srb.core.hfb.RequestHelper;
import com.shf.srb.core.service.UserAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Map;

/**
 * <p>
 * 用户账户 前端控制器
 * </p>
 *
 * @author shuhongfan
 * @since 2022-07-23
 */

@Api(tags = "会员账户")
@RestController
@RequestMapping("/api/core/userAccount")
@Slf4j
public class UserAccountController {
    @Resource
    private UserAccountService userAccountService;

    @ApiOperation("充值")
    @PostMapping("/auth/commitCharge/{chargeAmt}")
    public R commitCharge(
            @ApiParam(value = "充值金额", required = true) @PathVariable BigDecimal chargeAmt,
            HttpServletRequest request) {
        String token = request.getHeader("token");
        Long userId = JwtUtils.getUserId(token);

        String formStr = userAccountService.commitCharge(chargeAmt, userId);
        return R.ok().data("formStr", formStr);
    }

    @ApiOperation(value = "用户充值异步回调")
    @PostMapping("/notify")
    public String notify(HttpServletRequest request) {
        Map<String, Object> paramMap = RequestHelper.switchMap(request.getParameterMap());
        log.info("用户充值异步回调：" + JSON.toJSONString(paramMap));

//        校验签名
        if (RequestHelper.isSignEquals(paramMap)) {
//            充值成功交易
            if ("0001".equals(paramMap.get("resultCode"))) {
                return userAccountService.notify(paramMap);
            } else {
                log.info("用户充值异步回调失败：" + JSON.toJSONString(paramMap));
                return "success";
            }
        } else {
            log.info("用户充值异步回调签名错误：" + JSON.toJSONString(paramMap));
            return "fail";
        }
    }

    @ApiOperation("查询账户余额")
    @GetMapping("/auth/getAccount")
    public R getAccount(HttpServletRequest request) {
        String token = request.getHeader("token");
        Long userId = JwtUtils.getUserId(token);
        BigDecimal account = userAccountService.getAccount(userId);
        return R.ok().data("account", account);
    }
}

