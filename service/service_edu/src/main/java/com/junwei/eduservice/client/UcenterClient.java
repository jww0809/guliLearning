package com.junwei.eduservice.client;

import com.junwei.entity.vo.CenterMemberNew;
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
    CenterMemberNew getInfoUser(@PathVariable("uid")String uid);

}
