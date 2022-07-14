package com.junwei.staservice.client;

import com.junwei.commonutils.R;
import com.junwei.staservice.client.impl.StaClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "service-ucenter",fallback = StaClientImpl.class)
public interface StaClient {

    // 远程调用service_ucenter模块中的接口
    @GetMapping("/educenter/ucenter-member/getRegisterCount/{day}")
    R getRegisterCount(@PathVariable("day") String day);
}
