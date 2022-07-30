package com.shf.srb.core.controller.api;

import com.alibaba.fastjson.JSON;
import com.shf.common.result.R;
import com.shf.srb.base.util.JwtUtils;
import com.shf.srb.core.hfb.RequestHelper;
import com.shf.srb.core.pojo.entity.LendItemReturn;
import com.shf.srb.core.pojo.entity.LendReturn;
import com.shf.srb.core.service.LendItemReturnService;
import com.shf.srb.core.service.LendReturnService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 还款记录表 前端控制器
 * </p>
 *
 * @author shuhongfan
 * @since 2022-07-23
 */

@Api(tags = "还款计划")
@RestController
@RequestMapping("/api/core/lendReturn")
@Slf4j
public class LendReturnController {

    @Resource
    private LendReturnService lendReturnService;

    @Resource
    private LendItemReturnService lendItemReturnService;

    @ApiOperation("获取还款计划列表")
    @GetMapping("/list/{lendId}")
    public R list(@ApiParam(value = "标的id", required = true) @PathVariable Long lendId) {
        List<LendReturn> lendReturnList = lendReturnService.selectByLendId(lendId);
        return R.ok().data("list", lendReturnList);
    }

    @ApiOperation("获取回款计划列表")
    @GetMapping("/auth/list/{lendId}")
    public R list(@ApiParam(value = "标的id", required = true) @PathVariable Long lendId,
                  HttpServletRequest request) {
        String token = request.getHeader("token");
        Long userId = JwtUtils.getUserId(token);
        List<LendItemReturn> list = lendItemReturnService.selectByLendId(lendId, userId);
        return R.ok().data("list", list);
    }

    @ApiOperation("用户还款")
    @PostMapping("/auth/commitReturn/{lendReturnId}")
    public R commitReturn(
            @ApiParam(value = "还款计划id", required = true)
            @PathVariable Long lendReturnId,
            HttpServletRequest request
    ) {
        String token = request.getHeader("token");
        Long userId = JwtUtils.getUserId(token);

        String formStr = lendReturnService.commitReturn(lendReturnId, userId);
        return R.ok().data("formStr", formStr);
    }

    @ApiOperation("还款异步回调")
    @PostMapping("/notifyUrl")
    public String notifyUrl(HttpServletRequest request) {
        Map<String, Object> paramMap = RequestHelper.switchMap(request.getParameterMap());
        log.info("还款异步回调：" + JSON.toJSONString(paramMap));

//        校验签名
        if (RequestHelper.isSignEquals(paramMap)) {
            if ("0001".equals(paramMap.get("resultCode"))) {
                lendReturnService.notify(paramMap);
            } else {
                log.info("还款异步回调失败：" + JSON.toJSONString(paramMap));
                return "fail";
            }
        } else {
            log.info("还款异步回调签名错误：" + JSON.toJSONString(paramMap));
            return "fail";
        }
        return "success";
    }
}
