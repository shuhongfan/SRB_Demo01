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
}

