package com.shf.srb.core.controller.admin;

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
import java.util.List;
import java.util.Map;

@Api(tags = "标的管理")
@RestController
@RequestMapping("/admin/core/lend")
@Slf4j
public class AdminLendController {

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

    @ApiOperation("获取标的的信息")
    @GetMapping("/show/{id}")
    public R show(@ApiParam(value = "标的id",required = true) @PathVariable Long id) {
        Map<String,Object> result = lendService.getLendDetail(id);
        return R.ok().data("lendDetail", result);
    }
}
