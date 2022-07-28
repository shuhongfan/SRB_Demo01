package com.shf.srb.core.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shf.common.result.R;
import com.shf.srb.core.pojo.entity.BorrowInfo;
import com.shf.srb.core.pojo.vo.BorrowInfoApprovalVO;
import com.shf.srb.core.service.BorrowInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@Api(tags = "借款管理")
@RestController
@RequestMapping("/admin/core/borrowInfo")
@Slf4j
public class AdminBorrowInfoController {
    @Resource
    private BorrowInfoService borrowInfoService;

    @ApiOperation("借款信息列表")
    @GetMapping("/list/{page}/{limit}")
    public R list(@ApiParam(value = "当前页码", required = true)
                  @PathVariable Long page,

                  @ApiParam(value = "每页记录数", required = true)
                  @PathVariable Long limit,

                  @ApiParam(value = "查询关键字", required = false)
                  String keyword) {

        Page<BorrowInfo> pageParam = new Page<>(page, limit);
        IPage<BorrowInfo> pageModel = borrowInfoService.listPage(pageParam, keyword);
        return R.ok().data("pageModel", pageModel);
    }

    @ApiOperation("获取借款信息")
    @GetMapping("/show/{id}")
    public R show(@ApiParam(value = "借款id", required = true)  @PathVariable Long id) {
        Map<String,Object> borrowInfoDetail = borrowInfoService.getBorrowInfoDetail(id);
        return R.ok().data("borrowInfoDetail", borrowInfoDetail);
    }

    @ApiOperation("审批借款人信息")
    @PostMapping("/approval")
    public R approval(@RequestBody BorrowInfoApprovalVO borrowInfoApprovalVO) {
        borrowInfoService.approval(borrowInfoApprovalVO);
        return R.ok().message("审批完成");
    }
}
