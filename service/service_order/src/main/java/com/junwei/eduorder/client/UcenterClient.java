package com.junwei.eduorder.client;

import com.junwei.eduorder.client.impl.UcenterClientImpl;
import com.junwei.entity.vo.CenterMemberNew;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 远程调用Ucenter模块的接口
 */

@Component
@FeignClient(name = "service-ucenter",fallback = UcenterClientImpl.class)
public interface UcenterClient {

    //Ucenter模块 根据用户id查询用户信息的方法
    @GetMapping("/educenter/ucenter-member/getInfoUser/{uid}")
    CenterMemberNew getInfoUser(@PathVariable("uid") String uid);
}
