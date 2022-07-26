package com.shf.srb.sms.client;

import com.shf.srb.sms.fallbask.CoreUserInfoClientFallback;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "service-core",fallback = CoreUserInfoClientFallback.class)
public interface CoreUserInfoClient {
    @ApiOperation("校验手机号是否注册")
    @GetMapping("/api/core/userInfo/checkMobile/{mobile}")
    public boolean checkMobile(@ApiParam(value = "手机号",required = true) @PathVariable String mobile);
}
