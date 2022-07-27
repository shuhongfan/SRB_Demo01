package com.shf.srb.core.controller.api;


import com.shf.common.result.R;
import com.shf.srb.base.util.JwtUtils;
import com.shf.srb.core.pojo.vo.BorrowerVO;
import com.shf.srb.core.service.BorrowerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 借款人 前端控制器
 * </p>
 *
 * @author shuhongfan
 * @since 2022-07-23
 */
@Api(tags = "借款人")
@RestController
@RequestMapping("/api/core/borrower")
@Slf4j
public class BorrowerController {
    @Resource
    private BorrowerService borrowerService;

    @ApiOperation("保存借款人信息")
    @PostMapping("/auth/save")
    public R save(@ApiParam(value = "借款人认证信息Vo", required = true)
                  @RequestBody BorrowerVO borrowerVO,
                  HttpServletRequest request) {
        String token = request.getHeader("token");
        Long userId = JwtUtils.getUserId(token);
        borrowerService.saveBorrowerVOByUserId(borrowerVO, userId);
        return R.ok().message("信息提交成功");
    }

    @ApiOperation("获取借款人认证状态")
    @GetMapping("/auth/getBorrowerStatus")
    public R getBorrowerStatus(HttpServletRequest request) {
        String token = request.getHeader("token");
        Long userId = JwtUtils.getUserId(token);
        Integer status = borrowerService.getStatusByUserId(userId);
        return R.ok().data("borrowerStatus", status);
    }
}

