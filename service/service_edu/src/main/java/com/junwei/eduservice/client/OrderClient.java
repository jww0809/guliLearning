package com.junwei.eduservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
//执行出错，熔断机制
@FeignClient(name = "service-order",fallback = OrderClientImpl.class)
public interface OrderClient {

    /**
     * 根据课程id和用户id查询订单表 当前订单是否已经购买（status为1表示已经购买），用于前端的显示
     */
    @GetMapping("/eduorder/order/isBuyCourse/{courseId}/{memberId}")
    boolean isBuyCourse(@PathVariable("courseId") String courseId, @PathVariable("memberId") String memberId);
}
