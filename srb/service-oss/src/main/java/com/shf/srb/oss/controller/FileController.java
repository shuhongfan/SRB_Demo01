package com.shf.srb.oss.controller;

import com.shf.common.exception.BusinessException;
import com.shf.common.result.R;
import com.shf.common.result.ResponseEnum;
import com.shf.srb.oss.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Api(tags = "阿里云文件管理")
@CrossOrigin
@RestController
@RequestMapping("/api/oss/file")
public class FileController {
    @Resource
    private FileService fileService;

    @ApiOperation("文件上传")
    @PostMapping("/upload")
    public R upload(
            @ApiParam(value = "文件", required = true)
            @RequestParam("file") MultipartFile file,

            @ApiParam(value = "模块", required = true)
            @RequestParam("module") String module
    ) {
        try {
            InputStream inputStream = file.getInputStream();
            String originalFilename = file.getOriginalFilename();
            String uploadURL = fileService.upload(inputStream, module, originalFilename);

            return R.ok().message("文件上传成功").data("url", uploadURL);
        } catch (IOException e) {
            throw new BusinessException(ResponseEnum.UPLOAD_ERROR, e);
        }
    }

    @ApiOperation("删除OSS文件")
    @DeleteMapping("/remove")
    public R remove(
            @ApiParam(value = "要删除的文件路径", required = true)
            @RequestParam("url") String url
    ) {
        fileService.removeFile(url);
        return R.ok().message("删除成功");
    }
}
