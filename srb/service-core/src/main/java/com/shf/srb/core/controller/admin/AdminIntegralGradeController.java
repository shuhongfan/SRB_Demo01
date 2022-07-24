package com.shf.srb.core.controller.admin;


import com.shf.common.exception.BusinessException;
import com.shf.common.result.R;
import com.shf.common.result.ResponseEnum;
import com.shf.srb.core.pojo.entity.IntegralGrade;
import com.shf.srb.core.service.IntegralGradeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 积分等级表 前端控制器
 * </p>
 *
 * @author shuhongfan
 * @since 2022-07-23
 */
@Api(tags = "积分等级管理")
@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/admin/core/integralGrade")
public class AdminIntegralGradeController {
    @Resource
    private IntegralGradeService integralGradeService;

    @ApiOperation(value = "积分等级表")
    @GetMapping("/list")
    public R listAll() {

        log.info("hi this is log info");
        log.warn("hi this is log warn");
        log.error("hi this is log error");

        List<IntegralGrade> list = integralGradeService.list();
        return R.ok().data("list", list).message("获取列表成功");
    }

    @ApiOperation(value = "根据id删除记录", notes = "逻辑删除数据记录")
    @DeleteMapping("/remove/{id}")
    public R removeById(@ApiParam(value = "数据id", example = "100", required = true) @PathVariable Long id) {
        boolean res = integralGradeService.removeById(id);
        if (res) {
            return R.ok().message("删除成功");
        } else {
            return R.error().message("删除失败");
        }
    }

    @ApiOperation("新增积分等级")
    @PostMapping("/save")
    public R save(@ApiParam(value = "积分等级对象", required = true) @RequestBody IntegralGrade integralGrade) {

        /**
         * 如果借款额度为空就手动抛出一个自定义的异常！
         */
        if (integralGrade.getBorrowAmount() == null) {
            throw new BusinessException(ResponseEnum.BORROW_AMOUNT_NULL_ERROR);
        }

        boolean res = integralGradeService.save(integralGrade);
        if (res) {
            return R.ok().message("保存成功");
        } else {
            return R.error().message("保存失败");
        }
    }

    @ApiOperation("根据id获取积分等级")
    @GetMapping("/get/{id}")
    public R getById(@ApiParam(value = "数据id",required = true) @PathVariable Long id) {
        IntegralGrade integralGrade = integralGradeService.getById(id);
        if (integralGrade != null) {
            return R.ok().data("record", integralGrade);
        } else {
            return R.error().message("获取数据失败");
        }
    }

    @ApiOperation("更新积分等级")
    @PutMapping("/update")
    public R updateById(@ApiParam(value = "积分等级对象", required = true)
                            @RequestBody IntegralGrade integralGrade) {
        boolean res = integralGradeService.updateById(integralGrade);
        if (res) {
            return R.ok().message("更新成功");
        } else {
            return R.error().message("更新失败");
        }
    }
}

