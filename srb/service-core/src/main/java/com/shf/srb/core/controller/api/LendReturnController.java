package com.shf.srb.core.controller.api;

import com.shf.common.result.R;
import com.shf.srb.base.util.JwtUtils;
import com.shf.srb.core.pojo.entity.LendItemReturn;
import com.shf.srb.core.pojo.entity.LendReturn;
import com.shf.srb.core.service.LendItemReturnService;
import com.shf.srb.core.service.LendReturnService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
}
