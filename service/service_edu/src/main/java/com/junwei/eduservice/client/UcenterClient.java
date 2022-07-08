package com.junwei.eduservice.client;

import com.junwei.commonutils.R;
import com.junwei.eduservice.entity.UcenterMember;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 调用ucenter模块的获取用户信息的方法
 */

@Component
@FeignClient(name = "service-ucenter",fallback = UcenterDegradeFeignClient.class)
public interface UcenterClient {

    @GetMapping("/educenter/ucenter-member/getInfoUser/{uid}")
    UcenterMember getInfoUser(@PathVariable("uid")String uid);

}
