package com.shf.srb.core.controller.admin;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shf.common.exception.Assert;
import com.shf.common.result.R;
import com.shf.common.result.ResponseEnum;
import com.shf.common.util.RegexValidateUtils;
import com.shf.srb.base.util.JwtUtils;
import com.shf.srb.core.pojo.entity.UserInfo;
import com.shf.srb.core.pojo.query.UserInfoQuery;
import com.shf.srb.core.pojo.vo.LoginVO;
import com.shf.srb.core.pojo.vo.RegisterVO;
import com.shf.srb.core.pojo.vo.UserInfoVO;
import com.shf.srb.core.service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 用户基本信息 前端控制器
 * </p>
 *
 * @author shuhongfan
 * @since 2022-07-23
 */
@Api(tags = "会员管理")
@RestController
@RequestMapping("/admin/core/userInfo")
@Slf4j
@CrossOrigin
public class AdminUserInfoController {

    @Resource
    private UserInfoService userInfoService;


    @ApiOperation("获取会员分页列表")
    @GetMapping("/list/{page}/{limit}")
    public R listPage(
            @ApiParam(value = "当前页码", required = true) @PathVariable Long page,
            @ApiParam(value = "每页记录数", required = true) @PathVariable Long limit,
            @ApiParam(value = "查询对象", required = false) UserInfoQuery userInfoQuery
    ) {
        Page<UserInfo> pageParam = new Page<>(page, limit);
        IPage<UserInfo> pageModel = userInfoService.listPage(pageParam, userInfoQuery);
        return R.ok().data("pageModel", pageModel);
    }

    @ApiOperation("锁定和解锁")
    @PutMapping("/lock/{id}/{status}")
    public R lock(
            @ApiParam(value = "用户id", required = true)
            @PathVariable("id") Long id,

            @ApiParam(value = "锁定状态（0：锁定 1：解锁）", required = true)
            @PathVariable("status") Integer status
    ) {
        userInfoService.lock(id, status);
        return R.ok().message(status == 1 ? "解锁成功" : "锁定成功");
    }
}

