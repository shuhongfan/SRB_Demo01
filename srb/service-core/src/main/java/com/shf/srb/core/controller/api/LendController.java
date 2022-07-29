package com.shf.srb.core.controller.api;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shf.common.result.R;
import com.shf.srb.core.pojo.entity.Lend;
import com.shf.srb.core.service.LendService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Map;

/**
 * <p>
 * 标的准备表 前端控制器
 * </p>
 *
 * @author shuhongfan
 * @since 2022-07-23
 */
@Api(tags = "标的")
@RestController
@RequestMapping("/api/core/lend")
@Slf4j
public class LendController {
    @Resource
    private LendService lendService;

    @ApiOperation("标的列表")
    @GetMapping("/list/{page}/{limit}")
    public R listPage(@ApiParam(value = "当前页码", required = true)
                      @PathVariable Long page,

                      @ApiParam(value = "每页记录数", required = true)
                      @PathVariable Long limit,

                      @ApiParam(value = "查询关键字", required = false)
                      @RequestParam String keyword) {
        Page<Lend> pageParam = new Page<>(page, limit);
        IPage<Lend> pageModel = lendService.listPage(pageParam, keyword);
        return R.ok().data("pageModel", pageModel);
    }

    @ApiOperation("获取标的信息")
    @GetMapping("/show/{id}")
    public R show(@ApiParam(value = "标的id", required = true) @PathVariable Long id) {
        Map<String, Object> lendDetail = lendService.getLendDetail(id);
        return R.ok().data("lendDetail", lendDetail);
    }

    @ApiOperation("计算投资收益")
    @GetMapping("/getInterestCount/{invest}/{yearRate}/{totalmonth}/{returnMethod}")
    public R getInterestCount(
            @ApiParam(value = "投资金额",required = true) @PathVariable("invest") BigDecimal invest,
            @ApiParam(value = "年化收益",required = true) @PathVariable("yearRate") BigDecimal yearRate,
            @ApiParam(value = "期数",required = true) @PathVariable("totalmonth") Integer totalmonth,
            @ApiParam(value = "还款方式",required = true) @PathVariable("returnMethod") Integer returnMethod
            ) {
        BigDecimal interestCount = lendService.getInterestCount(invest, yearRate, totalmonth, returnMethod);
        return R.ok().data("interestCount", interestCount);
    }
}

